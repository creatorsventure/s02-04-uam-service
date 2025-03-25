package com.cv.s0204uamservice.service.intrface;

public interface TokenService {

    String generateToken(String userId);

    String validateToken(String token);

    void cleanExpiredTokens();

}
