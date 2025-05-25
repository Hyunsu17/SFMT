package com.temp.inflow.creator.profile.controller;

import com.temp.inflow.common.enums.PlatformType;
import com.temp.inflow.creator.profile.dto.CreatorProfileDto;
import com.temp.inflow.creator.profile.service.CreatorProfileAggregatorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "Creator Profile View", description = "id: 1-1-1-3")
@RequestMapping("/api/creator/profile")
public class CreatorProfileController {

    private final CreatorProfileAggregatorService aggregatorService;

    @GetMapping
    public Map<PlatformType, CreatorProfileDto> getFollowerStates(Authentication auth){
        return aggregatorService.getAllProfiles(auth.getName());
    }
}
