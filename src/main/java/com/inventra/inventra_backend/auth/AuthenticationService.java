package com.inventra.inventra_backend.auth;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

    LoginResponse register(RegisterRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    void logout(String username);

}


