package com.MyJournal.MyJournalApi.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyJournal.MyJournalApi.dtos.AuthRequest;
import com.MyJournal.MyJournalApi.dtos.AuthResponse;
import com.MyJournal.MyJournalApi.dtos.UserResponse;
import com.MyJournal.MyJournalApi.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "https://mindmatefrontend.onrender.com", "http://localhost:4200" }) // Allow requests from
                                                                                             // Angular dev server
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authenticationService.authenticate(request);
    }

    @GetMapping("/getUsers")
    public List<UserResponse> getAllUsers() {
        return authenticationService.getAllUsers();
    }
}
