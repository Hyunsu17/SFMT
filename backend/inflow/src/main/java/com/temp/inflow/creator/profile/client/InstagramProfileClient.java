package com.temp.inflow.creator.profile.client;

import com.temp.inflow.creator.profile.dto.InstagramProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstagramProfileClient {

    private final RestTemplate restTemplate;

    public List<Integer> fetchFollowerCounts(String igUserId, String accessToken, LocalDate since, LocalDate until){
        String url = String.format(
                "https://graph.facebook.com/v19.0/%s/insights" +
                        "?metric=follower_count&period=day&since=%s&until=%s&access_token=%s",
                igUserId,
                since,
                until,
                accessToken
        );

        ResponseEntity<InstagramProfileResponseDto> response = restTemplate.getForEntity(url, InstagramProfileResponseDto.class);
        return response.getBody().getData().get(0).getValues().stream()
                .map(InstagramProfileResponseDto.DataItem.ValueItem::getValue)
                .toList();
    }
}
