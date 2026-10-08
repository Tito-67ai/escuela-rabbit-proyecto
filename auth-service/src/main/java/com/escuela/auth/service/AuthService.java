package com.escuela.auth.service;

import com.escuela.auth.dto.*;

public interface AuthService {
    String registrar(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    LoginResponse refreshToken(RefreshTokenRequest request);
}