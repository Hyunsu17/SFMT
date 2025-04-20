package com.temp.inflow.repository;

import com.temp.inflow.model.InstagramToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface InstagramTokenRepository extends JpaRepository<InstagramToken, Long> {
    Optional<InstagramToken> findByUserIdAndTokenType(Long userId, String tokenType);

    Optional<InstagramToken> findByUserIdAndExpireTimeAfter(Long userId, LocalDateTime dateTime);
}
