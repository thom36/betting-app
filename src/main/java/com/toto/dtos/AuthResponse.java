package com.toto.dtos;

public record AuthResponse(
        int userId,
        String email,
        String token
) {}
