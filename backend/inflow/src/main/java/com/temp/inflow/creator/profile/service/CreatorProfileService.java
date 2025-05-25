package com.temp.inflow.creator.profile.service;

import com.temp.inflow.common.enums.PlatformType;
import com.temp.inflow.creator.profile.dto.CreatorProfileDto;
import org.springframework.stereotype.Service;

@Service
public interface CreatorProfileService {

    PlatformType getPlatform();

    CreatorProfileDto getProfileDto(String userId);
}
