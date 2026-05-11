package com.toto.middleware;

import com.toto.service.JwtService;

public class AuthMiddleware {
    private final JwtService jwtService = new JwtService();

    public boolean isAuthenticated(String authorizationHeader){
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            return false;
        }
        String token = authorizationHeader.substring(7);
        return jwtService.verifyToken(token);
    }
}
