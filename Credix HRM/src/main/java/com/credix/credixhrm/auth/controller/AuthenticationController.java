package com.credix.credixhrm.auth.controller;

import com.credix.credixhrm.auth.model.AuthenticationRequest;
import com.credix.credixhrm.auth.model.AuthenticationResponse;
import com.credix.credixhrm.auth.service.AuthenticationService;
import com.credix.credixhrm.auth.model.RegisterRequest;
import com.credix.credixhrm.utils.EmployeeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.credix.credixhrm.constents.EmployeeConstants.SOMETHING_WENT_WRONG;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        try {
            return service.register(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        try {
            return service.authenticate(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
