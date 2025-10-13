package com.MyJournal.MyJournalApi.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
//import org.springframework.format.annotation.DateTimeFormat;

import com.MyJournal.MyJournalApi.dtos.JournalEntryRequest;
import com.MyJournal.MyJournalApi.models.JournalEntry;
import com.MyJournal.MyJournalApi.models.User;
import com.MyJournal.MyJournalApi.repositories.JournalEntryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;

    // Metod för att skapa en journalpost
    public JournalEntry createJournalEntry(User user, JournalEntryRequest request) {
        JournalEntry newEntry = JournalEntry.builder()
                .userId(user.getId())
                .note(request.getNote())
                .feeling(request.getFeeling())
                .createdAt(LocalDateTime.now())
                .build();

        return journalEntryRepository.save(newEntry);

    }

    // Metod för att hämta alla journalposter för en användare
    public List<JournalEntry> getAllEntries(User user) {
        return journalEntryRepository.findByUserId(user.getId());
    }

    // Metod för att visa statistik för en tidperiod
    public List<JournalEntry> getJournalEntriesByDateRange(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return journalEntryRepository.findByUserIdAndCreatedAtBetween(user.getId(), startDate, endDate);
    }

    // Metod för att ta bort ett inlägg

    // Metod för att uppdatera ett inlägg

}
