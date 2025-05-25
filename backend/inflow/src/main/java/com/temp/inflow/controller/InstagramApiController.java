package com.temp.inflow.controller;

import com.temp.inflow.dto.InstagramProfileResponse;
import com.temp.inflow.service.InstagramApiService;
import com.temp.inflow.service.InstagramAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RequestMapping("/api/instagram")
@RequiredArgsConstructor
@Tag(name = "Instagram API", description = "인스타그램 graph API - Node")
@RestController
public class InstagramApiController {

    private final InstagramApiService instagramApiService;
    @Operation(summary = "프로필 정보 가져오기", description = "엑세스 토큰을 통해 사용자의 프로필 정보를 가져옵니다")
    @GetMapping("/me/profile")
    public ResponseEntity<?> getUserProfile() {
        InstagramProfileResponse profile = instagramApiService.getInstgramProfile();

        return ResponseEntity.ok(profile);
    }

    @Operation(summary = "특정기간동안의 팔로워수 가져오기", description = "엑세스 토큰을 통해 사용자의 프로필 정보를 가져옵니다")
    @GetMapping("/follower/")
    public ResponseEntity<?> getUserFollowerCount(
            @RequestParam ("period") int period

    ) {

        InstagramProfileResponse profile = instagramApiService.getInstgramProfile();
        return ResponseEntity.ok(profile);
    }
}
