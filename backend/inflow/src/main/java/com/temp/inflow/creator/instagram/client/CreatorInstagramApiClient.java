package com.temp.inflow.creator.instagram.client;

import com.temp.inflow.creator.instagram.dto.CreatorInstagramResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CreatorInstagramApiClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Integer> fetchFollowerCounts(String igUserId, String accessToken, LocalDate since, LocalDate until) {
        String url = String.format(
                "https://graph.facebook.com/v19.0/%s/insights?metric=follower_count&period=day&since=%s&until=%s&access_token=%s",
                igUserId, since, until, accessToken
        );
        ResponseEntity<CreatorInstagramResponseDto> res = restTemplate.getForEntity(url, CreatorInstagramResponseDto.class);
        return res.getBody().getData().get(0).getValues().stream()
                .map(v -> v.getValue())
                .collect(Collectors.toList());
    }

    public List<String> fetchRecentMediaIds(String igUserId, String accessToken, int limitDays, int maxCount) {
        // Fetch media published since limitDays ago and limit to maxCount
        LocalDate since = LocalDate.now().minusDays(limitDays);
        String url = String.format(
                "https://graph.facebook.com/v19.0/%s/media?fields=id,timestamp&since=%s&limit=%d&access_token=%s",
                igUserId, since, maxCount, accessToken
        );
        ResponseEntity<Map> res = restTemplate.getForEntity(url, Map.class);
        List<Map<String, String>> data = (List<Map<String, String>>) res.getBody().get("data");
        return data.stream().map(m -> m.get("id")).collect(Collectors.toList());
    }

    public Map<String, Integer> fetchMediaEngagement(Map<String,String> mediaIds, String accessToken) {
        // For each media id, fetch insights
        return mediaIds.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> {
                    String mediaId = e.getKey();
                    String metricList = "like_count,comments_count,saved,share_count,reach,impressions";
                    String url = String.format(
                            "https://graph.facebook.com/v19.0/%s?fields=%s&access_token=%s",
                            mediaId, metricList, accessToken
                    );
                    ResponseEntity<Map> resp = restTemplate.getForEntity(url, Map.class);
                    Map<String, Integer> ms = (Map<String, Integer>) resp.getBody();
                    // Flatten sum of required metrics as needed by service
                    return ms.getOrDefault(e.getValue(), 0);
                }
        ));
    }

}
