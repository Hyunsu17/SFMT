package com.temp.inflow.dto;

import lombok.Getter;

@Getter
public class InstagramTokenResponse {
    private final String accessToken;
    private final Long userId;
    private final String permissions;

    public InstagramTokenResponse(String accessToken, Long userId, String permissions) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.permissions = permissions;
    }

}
