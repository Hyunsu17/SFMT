package com.temp.inflow.creator.profile.service;

import com.temp.inflow.common.enums.PlatformType;
import com.temp.inflow.creator.profile.client.InstagramProfileClient;
import com.temp.inflow.creator.profile.dto.CreatorProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstagramProfileService implements CreatorProfileService{

    private final InstagramProfileClient instagramProfileClient;


    @Override
    public PlatformType getPlatform() {
        return PlatformType.INSTAGRAM;
    }

    @Override
    public CreatorProfileDto getProfileDto(String userId) {

        LocalDate today = LocalDate.now();
        //TODO : Instagram의 계정정보를 가져오는 기능 추가(DB 만든 후에) ex) account => { userId, accessToken}
        Object account = null;

        List<Integer> values = instagramProfileClient.fetchFollowerCounts(
                "",
                "",
                today.minusDays(29),
                today
        );

        int current = values.get(values.size() - 1);
        double avg7 = average(values.subList(23, 30));   // 최근 7일
        double avg15 = average(values.subList(15, 30));  // 최근 15일
        double avg30 = average(values);                  // 전체 30일

        return new CreatorProfileDto(current, avg7, avg15, avg30);
    }

    private double average(List<Integer> list) {
        return list.stream().mapToInt(i -> i).average().orElse(0);
    }
}
