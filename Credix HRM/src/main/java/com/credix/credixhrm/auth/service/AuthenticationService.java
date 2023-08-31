package com.credix.credixhrm.auth.service;

import com.credix.credixhrm.auth.model.AuthenticationRequest;
import com.credix.credixhrm.auth.model.AuthenticationResponse;
import com.credix.credixhrm.auth.model.RegisterRequest;
import org.springframework.http.ResponseEntity;


public interface AuthenticationService {
    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request);

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request);

}
