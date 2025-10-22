package com.MyJournal.MyJournalApi.services;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.MyJournal.MyJournalApi.dtos.JournalEntryRequest;
import com.MyJournal.MyJournalApi.models.Feeling;
import com.MyJournal.MyJournalApi.models.User;
import com.MyJournal.MyJournalApi.repositories.JournalEntryRepository;

@SpringBootTest
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
        MockitoAnnotations.openMocks(this);
        // Initialize test data
        user = new User();
        user.setId("testUserId");

        journalEntryRequest = new JournalEntryRequest();
        journalEntryRequest.setNote("En fin dag idag!");
        journalEntryRequest.setFeeling(Feeling.GLAD);
    }

}
