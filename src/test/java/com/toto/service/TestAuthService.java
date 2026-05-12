package com.toto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.toto.dtos.AuthResponse;
import com.toto.repository.UserRepository;

public class TestAuthService {
    @Test
    void shouldRegister(){
        UserRepository repo = new UserRepository();
        JwtService jwtService = new JwtService();
        AuthService authService = new AuthService(repo, jwtService);

        AuthResponse response = authService.register("Chiara", "chiara.messina@gmail.com", "chiaradu38!");
        assertEquals(response.email(), "chiara.messina@gmail.com");
    }

    @Test
    void shouldNotRegisterWithoutEmail(){
        UserRepository repo = new UserRepository();
        JwtService jwtService = new JwtService();
        AuthService authService = new AuthService(repo, jwtService);

        RuntimeException exception = assertThrows(
        RuntimeException.class,
            () -> authService.register("Chiara", null, "chiaradu38!")
        );

        assertEquals(
            "Email obligatoire",
            exception.getMessage()
        );
    }

    @Test
    void shouldNotRegisterWithShortPassword(){
        UserRepository repo = new UserRepository();
        JwtService jwtService = new JwtService();
        AuthService authService = new AuthService(repo, jwtService);

        RuntimeException exception = assertThrows(
        RuntimeException.class,
            () -> authService.register("Chiara", "chiara.messina@gmail.com", "chia!")
        );

        assertEquals(
            "Mot de passe trop court",
            exception.getMessage()
        );
    }

    @Test
    void shouldNotRegisterUserWithSameEmail(){
        UserRepository repo = new UserRepository();
        JwtService jwtService = new JwtService();
        AuthService authService = new AuthService(repo, jwtService);

        RuntimeException exception = assertThrows(
        RuntimeException.class,
            () -> authService.register("Thomas", "thomas.messina@gmail.com", "thomasdu38!")
        );

        assertEquals(
            "Email déjà utilisé",
            exception.getMessage()
        );
    }

    @Test
    void shouldLogin(){
        UserRepository repo = new UserRepository();
        JwtService jwtService = new JwtService();
        AuthService authService = new AuthService(repo, jwtService);

        AuthResponse response = authService.login("thomas.messina@gmail.com", "totodu38");
        assertEquals("thomas.messina@gmail.com", response.email());
    }

    @Test
    void shouldNotLogin(){
        UserRepository repo = new UserRepository();
        JwtService jwtService = new JwtService();
        AuthService authService = new AuthService(repo, jwtService);

        RuntimeException exception = assertThrows(
        RuntimeException.class,
            () -> authService.login("thomas.messina@gmail.com", "totodu3")
        );

        assertEquals(
            "Identifiants invalides",
            exception.getMessage()
        );
    }
}
