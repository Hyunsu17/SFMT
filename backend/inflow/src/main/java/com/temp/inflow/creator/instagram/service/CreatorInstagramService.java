package com.temp.inflow.creator.instagram.service;

import com.temp.inflow.creator.instagram.client.CreatorInstagramApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CreatorInstagramService {
    private final CreatorInstagramApiClient client;
    public InstagramMetricsDto.PlatformMetrics getInstagramMetrics(Long userId) {
        UserInstagram account = repo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Instagram 연동 정보 없음"));

        String igId = account.getIgUserId();
        String token = account.getAccessToken();

        LocalDate today = LocalDate.now();
        LocalDate weekAgo = today.minusDays(7);
        LocalDate monthAgo = today.minusDays(30);

        // 팔로워 수 리스트 (30일)
        List<Integer> followers = client.fetchFollowerCounts(igId, token, monthAgo, today);
        int current = followers.get(followers.size() - 1);
        double avg7 = followers.subList(followers.size() - 7, followers.size()).stream().mapToInt(i->i).average().orElse(0);

        // 콘텐츠 업로드 수 (주간)
        int weeklyPostCount = client.fetchRecentMediaIds(igId, token,7,100).size();

        // 최근 7개, 3개 미디어 ID
        List<String> last7 = client.fetchRecentMediaIds(igId, token,30,7);
        List<String> last3 = client.fetchRecentMediaIds(igId, token,30,3);

        // 좋아요, 댓글, 저장, 공유 수 집계
        List<Integer> likes7 = last7.stream().map(mid-> client.fetchMediaEngagement(Map.of(mid, "like_count"), token).getOrDefault(mid,0)).toList();
        List<Integer> comments7 = last7.stream().map(mid-> client.fetchMediaEngagement(Map.of(mid, "comments_count"), token).getOrDefault(mid,0)).toList();
        List<Integer> saves7 = last7.stream().map(mid-> client.fetchMediaEngagement(Map.of(mid, "saved"), token).getOrDefault(mid,0)).toList();
        List<Integer> shares7 = last7.stream().map(mid-> client.fetchMediaEngagement(Map.of(mid, "share_count"), token).getOrDefault(mid,0)).toList();

        List<Integer> likes3 = last3.stream().map(mid-> client.fetchMediaEngagement(Map.of(mid, "like_count"), token).getOrDefault(mid,0)).toList();
        List<Integer> comments3 = last3.stream().map(mid-> client.fetchMediaEngagement(Map.of(mid, "comments_count"), token).getOrDefault(mid,0)).toList();
        List<Integer> saves3 = last3.stream().map(mid-> client.fetchMediaEngagement(Map.of(mid, "saved"), token).getOrDefault(mid,0)).toList();
        List<Integer> shares3 = last3.stream().map(mid-> client.fetchMediaEngagement(Map.of(mid, "share_count"), token).getOrDefault(mid,0)).toList();

        // 참여율 = (좋아요+댓글+저장+공유)/현재팔로워*100 (최근 7개 평균)
        double engagementRate = IntStream.range(0,7).mapToDouble(i->
                (likes7.get(i)+comments7.get(i)+saves7.get(i)+shares7.get(i))
        ).average().orElse(0) / current * 100;

        // 도달률 = 평균 reach/current*100 (최근 3개) - assume reach fetched similarly
        double reachRate = 0; // fetchMediaEngagement with metric "reach"

        // 댓글/좋아요 비율 = 평균(comments/likes)*100 (최근 3개)
        double commentLikeRatio = IntStream.range(0,3).mapToDouble(i->
                (likes3.get(i)>0? comments3.get(i)/(double)likes3.get(i) : 0)
        ).average().orElse(0) * 100;

        // 성장률
        double growthRate = (current - avg7) / avg7 * 100;

        return new PlatformMetrics(
                growthRate, weeklyPostCount,
                engagementRate, reachRate, commentLikeRatio,
                likes3, comments3, saves3, shares3,
                likes7, comments7, saves7, shares7,
                current
        );
    }
}
