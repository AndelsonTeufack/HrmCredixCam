package com.credix.credixhrm.auth.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AuthenticationResponse {

    public LocalDateTime timeStamp;
    public int statusCode;
    public HttpStatus status;
    public String message;
    public String token;

}