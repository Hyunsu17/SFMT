package com.temp.inflow.model;

import jakarta.persistence.*;
import lombok.Builder;

import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@Entity
public class InstagramToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String accessToken;
    private String permissions;
    private String tokenType;
    private LocalDateTime createTime;
    private LocalDateTime expireTime;

}
