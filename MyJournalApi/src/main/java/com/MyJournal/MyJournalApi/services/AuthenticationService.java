package com.MyJournal.MyJournalApi.services;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.MyJournal.MyJournalApi.dtos.AuthRequest;
import com.MyJournal.MyJournalApi.dtos.AuthResponse;
import com.MyJournal.MyJournalApi.dtos.UserResponse;
import com.MyJournal.MyJournalApi.models.User;
import com.MyJournal.MyJournalApi.repositories.UserRepository;
import com.MyJournal.MyJournalApi.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // Registrerar en ny användare och returnerar en JWT-token
    public AuthResponse register(AuthRequest request) {
        // Kontrollera om användarnamnet redan finns
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Skapa och spara ny användare
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(request.getUsername());

        // Generera JWT-token för den nya användaren
        return new AuthResponse("User registered successfully", token);
    }

    // Autentiserar en användare och returnerar en JWT-token
    public AuthResponse authenticate(AuthRequest request) {
        // Autentisera användaren (validera användarnamn och lösenord)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        // Kontrollera om användaren finns i databasen
        userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        // Generera och returnera JWT-token om autentiseringen lyckas
        String token = jwtService.generateToken(request.getUsername());
        return new AuthResponse("User authenticated successfully", token);
    }

    // Metod för att hämta alla användare från databasen (bara för att se vilka
    // användare som finns)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername()))
                .toList();
    }

}
