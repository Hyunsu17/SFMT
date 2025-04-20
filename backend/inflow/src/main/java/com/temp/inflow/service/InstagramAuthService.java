package com.temp.inflow.service;

import com.temp.inflow.dto.InstagramTokenResponse;
import com.temp.inflow.model.InstagramToken;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Log4j2
public class InstagramAuthService {

    private final TokenService tokenService;

    private final InstagramAuthClient instagramAuthClient;

    public String processInstagramAuth(String code) {
        // short 토큰 가져오기
        InstagramTokenResponse tokenResponse = instagramAuthClient.fetchInstagramShortToken(code);

        InstagramToken shortToken = InstagramToken.builder()
                .userId(tokenResponse.getUserId())
                .accessToken(tokenResponse.getAccessToken())
                .permissions(tokenResponse.getPermissions())
                .build();
        log.error("Instagram Short Token Object :");
        log.error(shortToken.getAccessToken());
        log.error(shortToken.getUserId());
        log.error(shortToken.getPermissions());
        // short 토큰 저장하기
//        InstagramToken shortToken  = tokenService.saveShortLivedToken(
//                tokenResponse.getUserId(),
//                tokenResponse.getAccessToken(),
//                tokenResponse.getPermissions()
//        );

        // long 토큰 가져오기
        InstagramTokenResponse longTokenResponse = instagramAuthClient.fetchInstagramLongToken(shortToken.getAccessToken());
        InstagramToken longToken = InstagramToken.builder()
                .userId(longTokenResponse.getUserId())
                .accessToken(longTokenResponse.getAccessToken())
                .permissions("test")
                .build();

        log.error("Instagram Long Token Object :");
        log.error(longTokenResponse.getAccessToken());
        log.error(longTokenResponse.getUserId());
        log.error(longTokenResponse.getPermissions());


        // long 토큰 저장하기
//        InstagramToken longToken  = tokenService.saveLongLivedToken(
//                longTokenResponse.getUserId(),
//                longTokenResponse.getAccessToken(),
//                longTokenResponse.getPermissions()
//        );

        // 필요한 값 반환
        return longToken.getAccessToken();
    }

}
