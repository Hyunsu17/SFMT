package com.temp.inflow.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InstagramProfileResponse {

    private String username;
    private String name;
    private String id;
    private int mediaCount;
    private int followersCount;


}
