package com.MyJournal.MyJournalApi.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyJournal.MyJournalApi.models.User;
import com.MyJournal.MyJournalApi.services.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular dev server
@RestController
@RequestMapping("/api/myJournal/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me/premium")
    public ResponseEntity<Map<String, Boolean>> getPremiumStatus() {
        User currentUser = userService.getCurrentUser();
        boolean isPremium = currentUser.isPremium();
        return ResponseEntity.ok(Map.of("premium", isPremium));
    }

}
