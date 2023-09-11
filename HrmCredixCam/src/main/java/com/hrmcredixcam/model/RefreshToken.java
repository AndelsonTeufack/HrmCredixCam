package com.hrmcredixcam.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "RefreshToken")
public class RefreshToken {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;
        private UUID refreshTokenId;
        private String refreshToken;
        private String token;
        private String user;
        private Date expiryDate;
        private LocalDateTime dateTime;



}
