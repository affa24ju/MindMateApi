package com.MyJournal.MyJournalApi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;

import com.MyJournal.MyJournalApi.dtos.JournalEntryRequest;
import com.MyJournal.MyJournalApi.models.Feeling;
import com.MyJournal.MyJournalApi.models.JournalEntry;
import com.MyJournal.MyJournalApi.models.User;
import com.MyJournal.MyJournalApi.repositories.JournalEntryRepository;

// @ExtendWith - Använder MockitoExtension för att möjliggöra Mockito-funktioner i JUnit 5 tester
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
        // Initierar test data
        user = new User();
        user.setId("testUserId");

        journalEntryRequest = new JournalEntryRequest();
        journalEntryRequest.setNote("En fin dag idag!");
        journalEntryRequest.setFeeling(Feeling.GLAD);
    }

    // Test för createJournalEntry-metoden
    @Test
    void testCreateJournalEntry_ShouldSaveAndReturnJournalEntry() {
        // Arrange
        JournalEntry savedEntry = JournalEntry.builder()
                .userId(user.getId())
                .note(journalEntryRequest.getNote())
                .feeling(journalEntryRequest.getFeeling())
                .createdAt(LocalDateTime.now())
                .build();

        // Mockar beteendet för journalEntryRepository.save()
        when(journalEntryRepository.save(any(JournalEntry.class)))
                .thenReturn(savedEntry);

        System.out.println("Mock save() returns: " + journalEntryRepository.save(any()));
        // Act
        JournalEntry result = journalEntryService.createJournalEntry(user, journalEntryRequest);

        // Assert
        // Verifierar att resultatet inte är null och har rätt värden
        assertNotNull(result);
        assertEquals(user.getId(), result.getUserId());
        assertEquals(journalEntryRequest.getNote(), result.getNote());
        assertEquals(journalEntryRequest.getFeeling(), result.getFeeling());

        verify(journalEntryRepository, times(1)).save(any(JournalEntry.class));

    }

    // Test för getAllEntries-metoden
    @Test
    void testGetAllEntries_ShouldReturnListOfJournalEntriesForUser() {
        // Arrange
        // Skapar några dummy journalposter för användaren
        // Ska returnera dessa när repository anropas
        JournalEntry entry1 = JournalEntry.builder()
                .userId(user.getId())
                .note("Dagbokspost 1")
                .feeling(Feeling.NEUTRAL)
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();

        JournalEntry entry2 = JournalEntry.builder()
                .userId(user.getId())
                .note("Dagbokspost 2")
                .feeling(Feeling.GLAD)
                .createdAt(LocalDateTime.now())
                .build();

        when(journalEntryRepository.findByUserId(user.getId()))
                .thenReturn(List.of(entry1, entry2));

        // Act
        List<JournalEntry> result = journalEntryService.getAllEntries(user);

        // Assert
        // Verifierar att resultatet inte är null, har rätt storlek (2) och innehåller
        // de förväntade journalposterna.
        // 
        // Använder också verify för att säkerställa att repository-metoden anropades exakt en gång med rätt användar-ID.
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Dagbokspost 1", result.get(0).getNote());
        assertEquals("Dagbokspost 2", result.get(1).getNote());

        verify(journalEntryRepository, times(1)).findByUserId(user.getId());
    }

}
