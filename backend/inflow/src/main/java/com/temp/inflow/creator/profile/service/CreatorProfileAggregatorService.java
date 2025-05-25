package com.temp.inflow.creator.profile.service;

import com.temp.inflow.common.enums.PlatformType;
import com.temp.inflow.creator.profile.dto.CreatorProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreatorProfileAggregatorService {

    private final List<CreatorProfileService> creatorProfileServiceList;

    public Map<PlatformType, CreatorProfileDto> getAllProfiles(String userId) {
        return creatorProfileServiceList.stream()
                .collect(Collectors.toMap(CreatorProfileService::getPlatform, service -> service.getProfileDto(userId)));
    }
}
