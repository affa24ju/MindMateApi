package com.MyJournal.MyJournalApi.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.MyJournal.MyJournalApi.models.User;

@Service
public class UserService {

    // Metod för att hämta den aktuella inloggade användaren
    public User getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return (User) auth.getPrincipal();
    }

    public void setPremium(String userId, boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPremium'");
    }

}
