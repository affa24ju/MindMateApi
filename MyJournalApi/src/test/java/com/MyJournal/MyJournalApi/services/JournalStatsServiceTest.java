package com.MyJournal.MyJournalApi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.MyJournal.MyJournalApi.dtos.JournalStatsResponse;
import com.MyJournal.MyJournalApi.models.Feeling;
import com.MyJournal.MyJournalApi.models.JournalEntry;

public class JournalStatsServiceTest {

    private JournalStatsService journalStatsService;

    @BeforeEach
    void setUp() {
        journalStatsService = new JournalStatsService();
    }

    // Test för getStats-metoden
    @Test
    void testGetStats_ShouldReturnCorrectCountAndPercentages() {
        // Arrange
        List<JournalEntry> entries = List.of(
                JournalEntry.builder().feeling(Feeling.GLAD).build(),
                JournalEntry.builder().feeling(Feeling.TIRED).build(),
                JournalEntry.builder().feeling(Feeling.GLAD).build());
        // Act
        JournalStatsResponse result = journalStatsService.getStats(entries);

        // Assert
        // Kollar att resultatet inte är null, returnerar korrekt antal och procent
        assertNotNull(result);
        assertEquals(3, result.getTotalEntries());

        // Kollar att räkningen och procenten för varje känsla är korrekt
        Map<Feeling, Long> counts = result.getFeelingCounts();
        assertEquals(2L, counts.get(Feeling.GLAD));
        assertEquals(1L, counts.get(Feeling.TIRED));

        // Kollar procenten
        Map<Feeling, Double> percentages = result.getFeelingPercentages();
        assertEquals(66.666, percentages.get(Feeling.GLAD), 0.01);
        assertEquals(33.333, percentages.get(Feeling.TIRED), 0.01);

    }

}
