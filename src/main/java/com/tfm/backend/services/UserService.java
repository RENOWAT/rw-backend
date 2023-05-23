package com.tfm.backend.services;

import com.tfm.backend.data.daos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public Optional< String > login(String email) {
        return this.userRepository.findByEmail(email)
                .map(user -> jwtService.createToken(user.getEmail(), user.getFirstName(), user.getRole().name()));
    }


}
