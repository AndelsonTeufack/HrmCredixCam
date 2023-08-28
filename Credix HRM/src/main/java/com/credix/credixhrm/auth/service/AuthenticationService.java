package com.credix.credixhrm.auth.service;

import com.credix.credixhrm.auth.model.AuthenticationRequest;
import com.credix.credixhrm.auth.model.AuthenticationResponse;
import com.credix.credixhrm.auth.model.RegisterRequest;

public interface AuthenticationService {
    public AuthenticationResponse register(RegisterRequest request);

    public AuthenticationResponse authenticate(AuthenticationRequest request);
}
