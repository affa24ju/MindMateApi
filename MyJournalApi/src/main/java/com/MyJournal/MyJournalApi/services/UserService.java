package com.MyJournal.MyJournalApi.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.MyJournal.MyJournalApi.models.User;
import com.MyJournal.MyJournalApi.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Metod för att hämta den aktuella inloggade användaren
    public User getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        return (User) auth.getPrincipal();
    }

    public void setPremium(String userId, boolean b) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setPremium(b);
            userRepository.save(user);
        });

    }

}
