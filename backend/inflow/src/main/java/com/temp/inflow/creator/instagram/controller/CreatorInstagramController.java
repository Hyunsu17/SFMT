package com.temp.inflow.creator.instagram.controller;

import com.temp.inflow.creator.instagram.service.CreatorInstagramService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/creator/instagram")
@Tag(name = "Creator instagram View", description = "id: 1-1-1-4")
public class CreatorInstagramController {
    private final CreatorInstagramService service;
    public ResponseEntity<InstagramMetricsDto.PlatformMetrics> getInstagramMetrics(Authentication auth) {
        Long userId = Long.parseLong(auth.getName());
        return ResponseEntity.ok(service.getInstagramMetrics(userId));
    }
}
