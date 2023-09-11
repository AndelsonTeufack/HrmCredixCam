package com.hrmcredixcam.repository;

import com.hrmcredixcam.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByRefreshTokenId(UUID refreshTokenId);

}
