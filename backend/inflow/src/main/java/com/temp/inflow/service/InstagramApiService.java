package com.temp.inflow.service;

import com.temp.inflow.dto.InstagramProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@RequiredArgsConstructor
public class InstagramApiService {

    private final InstagramApiClient instagramApiClient;

    @Value("${test.longToken}")
    private String testToken;
    @Value("${test.id}")
    private String testId;

    public InstagramProfileResponse getInstgramProfile(){
        return instagramApiClient.fetchInstagramProfile(testId, testToken);
    }
}
