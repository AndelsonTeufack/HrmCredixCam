package com.hrmcredixcam.service;

import com.hrmcredixcam.authdtos.LoginRequestDTO;
import com.hrmcredixcam.authdtos.RefreshTokenDTO;
import com.hrmcredixcam.authdtos.SignupRequestDTO;
import com.hrmcredixcam.authdtos.UserInfoResponseDTO;

public interface AuthService {


    UserInfoResponseDTO login(LoginRequestDTO loginDto);

    String registerByAdmin(SignupRequestDTO registerDto);

    //String registerByUser(SignupRequestDTO registerDto);

    UserInfoResponseDTO refreshToken(RefreshTokenDTO refreshTokendto);
}
