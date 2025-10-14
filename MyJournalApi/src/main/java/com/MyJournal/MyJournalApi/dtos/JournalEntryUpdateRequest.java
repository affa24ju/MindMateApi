package com.MyJournal.MyJournalApi.dtos;

// import java.time.LocalDateTime;

import com.MyJournal.MyJournalApi.models.Feeling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalEntryUpdateRequest {
    // Dto för att uppdatera inlägg
    private String note;
    private Feeling feeling;
    // private LocalDateTime updatedAt;

}
