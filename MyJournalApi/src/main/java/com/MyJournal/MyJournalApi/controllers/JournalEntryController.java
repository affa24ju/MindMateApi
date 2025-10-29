package com.MyJournal.MyJournalApi.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.MyJournal.MyJournalApi.dtos.JournalEntryRequest;
import com.MyJournal.MyJournalApi.dtos.JournalEntryUpdateRequest;
import com.MyJournal.MyJournalApi.dtos.JournalStatsResponse;
import com.MyJournal.MyJournalApi.models.JournalEntry;
import com.MyJournal.MyJournalApi.models.User;
import com.MyJournal.MyJournalApi.services.JournalEntryService;
import com.MyJournal.MyJournalApi.services.JournalStatsService;
import com.MyJournal.MyJournalApi.services.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "https://mindmatefrontend.onrender.com", "http://localhost:4200" })
@RestController
@RequestMapping("/api/myJournal")
@RequiredArgsConstructor
public class JournalEntryController {

    private final JournalEntryService journalEntryService;
    private final JournalStatsService journalStatsService;
    private final UserService userService;

    @PostMapping("/createJournalEntry")
    public JournalEntry createJournalEntry(@RequestBody JournalEntryRequest request) {
        // User dummyUser = new User();
        // dummyUser.setId("dummyUser123"); // Replace with actual user ID retrieval
        // logic
        User currentUser = userService.getCurrentUser();
        return journalEntryService.createJournalEntry(currentUser, request);
    }

    @GetMapping("/getAllEntries")
    public List<JournalEntry> getAllEntries() {
        // User dummyUser = new User();
        // dummyUser.setId("dummyUser123"); // Replace with actual user ID retrieval
        // logic
        User currentUser = userService.getCurrentUser();
        return journalEntryService.getAllEntries(currentUser);
    }

    @GetMapping("/getStats")
    public JournalStatsResponse getStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        // User dummyUser = new User();
        // dummyUser.setId("dummyUser123"); // Replace with actual user ID retrieval
        // logic
        User currentUser = userService.getCurrentUser();

        // Konvertera LocalDate till LocalDateTime för att inkludera hela dagen
        LocalDateTime startDateTime = startDate.atStartOfDay(); // 00:00:00
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59); // 23:59:59 för att inkludera hela dagen

        List<JournalEntry> entries = journalEntryService.getJournalEntriesByDateRange(currentUser, startDateTime,
                endDateTime);

        return journalStatsService.getStats(entries);

    }

    @GetMapping("/today")
    public List<JournalEntry> getTodayEntries() {
        // User dummyUser = new User();
        // dummyUser.setId("dummyUser123"); // Replace with actual user ID retrieval
        // logic
        User currentUser = userService.getCurrentUser();

        // Tar reda på dagens datum och skapar start & end tid för dagen
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        // return journalEntryService.getJournalEntriesByDateRange(dummyUser,
        // startOfDay, endOfDay);
        return journalEntryService.getJournalEntriesByDateRange(currentUser, startOfDay, endOfDay);
    }

    // Endpoint för att redera ett journal inlägg
    @DeleteMapping("/deleteJournalEntry/{entryId}")
    public ResponseEntity<String> deleteJournalEntry(@PathVariable String entryId) {
        User currentUser = userService.getCurrentUser();
        journalEntryService.deleteJournalEntry(entryId, currentUser);
        return ResponseEntity.ok().body("Inlägget har raderats!");
    }

    // Endpoint för att uppdatera ett inlägg
    @PutMapping("/updateJournalEntry/{entryId}")
    public JournalEntry updateJournalEntry(@PathVariable String entryId,
            @RequestBody JournalEntryUpdateRequest request) {
        User currentUser = userService.getCurrentUser();
        return journalEntryService.updateJournalEntry(entryId, request, currentUser);
    }

}
