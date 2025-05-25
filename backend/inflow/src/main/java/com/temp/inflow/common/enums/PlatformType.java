package com.temp.inflow.common.enums;

public enum PlatformType {
    INSTAGRAM,
    YOUTUBE,
    TIKTOK,
    BLOG;
    public String displayName() {
        return switch (this) {
            case INSTAGRAM -> "인스타그램";
            case YOUTUBE -> "유튜브";
            case TIKTOK -> "틱톡";
            case BLOG -> "블로그";
        };
    }
}
