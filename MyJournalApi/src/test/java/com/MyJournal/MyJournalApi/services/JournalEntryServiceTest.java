package com.MyJournal.MyJournalApi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;

import com.MyJournal.MyJournalApi.dtos.JournalEntryRequest;
import com.MyJournal.MyJournalApi.models.Feeling;
import com.MyJournal.MyJournalApi.models.JournalEntry;
import com.MyJournal.MyJournalApi.models.User;
import com.MyJournal.MyJournalApi.repositories.JournalEntryRepository;

// @SpringBootTest
@ExtendWith(MockitoExtension.class)
public class JournalEntryServiceTest {

    // @Mock: skapar en mock-instans av JournalEntryRepository för teständamål
    @Mock
    private JournalEntryRepository journalEntryRepository;

    // @InjectMocks: skapar en instans av JournalEntryService och injicerar de
    // mockade beroenden
    @InjectMocks
    private JournalEntryService journalEntryService;

    private User user;
    private JournalEntryRequest journalEntryRequest;

    @BeforeEach
    void setUp() {
        // MockitoAnnotations.openMocks(this);
        // Initialize test data
        user = new User();
        user.setId("testUserId");

        journalEntryRequest = new JournalEntryRequest();
        journalEntryRequest.setNote("En fin dag idag!");
        journalEntryRequest.setFeeling(Feeling.GLAD);
    }

    @Test
    void testCreateJournalEntry_ShouldSaveAndReturnJournalEntry() {
        // Arrange
        JournalEntry savedEntry = JournalEntry.builder()
                .userId(user.getId())
                .note(journalEntryRequest.getNote())
                .feeling(journalEntryRequest.getFeeling())
                .createdAt(LocalDateTime.now())
                .build();

        when(journalEntryRepository.save(any(JournalEntry.class)))
                .thenReturn(savedEntry);

        System.out.println("Mock save() returns: " + journalEntryRepository.save(any()));
        // Act
        JournalEntry result = journalEntryService.createJournalEntry(user, journalEntryRequest);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getUserId());
        assertEquals(journalEntryRequest.getNote(), result.getNote());
        assertEquals(journalEntryRequest.getFeeling(), result.getFeeling());

        verify(journalEntryRepository, times(1)).save(any(JournalEntry.class));

    }

}
