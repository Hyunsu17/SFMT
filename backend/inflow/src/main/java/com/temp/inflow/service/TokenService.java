package com.temp.inflow.service;

import com.temp.inflow.model.InstagramToken;
import com.temp.inflow.repository.InstagramTokenRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final InstagramTokenRepository tokenRepository;

    public InstagramToken saveShortLivedToken(Long userId, String accessToken, String permissions) {
        tokenRepository.findByUserIdAndTokenType(userId, "short")
                .ifPresent(tokenRepository::delete);

        InstagramToken token = InstagramToken.builder()
                .userId(userId)
                .accessToken(accessToken)
                .permissions(permissions)
                .tokenType("short")
                .createTime(LocalDateTime.now())
                .expireTime(LocalDateTime.now().plusHours(1))
                .build();

        return tokenRepository.save(token);
    }

    public InstagramToken saveLongLivedToken(Long userId, String accessToken, String permissions) {
        tokenRepository.findByUserIdAndTokenType(userId, "long")
                .ifPresent(tokenRepository::delete);

        InstagramToken token = InstagramToken.builder()
                .userId(userId)
                .accessToken(accessToken)
                .permissions(permissions)
                .tokenType("short")
                .createTime(LocalDateTime.now())
                .expireTime(LocalDateTime.now().plusHours(1))
                .build();

        return tokenRepository.save(token);
    }
}
