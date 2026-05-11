package com.toto.service;

import org.mindrot.jbcrypt.BCrypt;

import com.toto.domain.User;
import com.toto.dtos.AuthResponse;
import com.toto.repository.IUserRepository;

public class AuthService {

    private final IUserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(IUserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public AuthResponse register(String name, String email, String password) {
        if (email == null || email.isBlank()) {
            throw new RuntimeException("Email obligatoire");
        }

        if (password == null || password.length() < 8) {
            throw new RuntimeException("Mot de passe trop court");
        }

        User existingUser = userRepository.findByEmail(email);

        if (existingUser != null) {
            throw new RuntimeException("Email déjà utilisé");
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(userRepository.getNextUserId(),
                            name,
                            email,
                            passwordHash
                        );

        User savedUser = userRepository.create(user);

        String token = jwtService.generateToken(savedUser);

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                token
        );
    }

    public AuthResponse login(String email, String password) {
        User user = userRepository.findByEmail(email);
        System.out.println(email);
        System.out.println(password);

        if (user == null) {
            System.out.println("Salutttttttt");
            throw new RuntimeException("Identifiants invalides");
        }

        boolean passwordOk = BCrypt.checkpw(password, user.getPassHash());

        if (!passwordOk) {
            throw new RuntimeException("Identifiants invalides");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                token
        );
    }
}
