package com.MyJournal.MyJournalApi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyJournal.MyJournalApi.services.UserService;

// import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular dev server
@RestController
@RequestMapping("/api/myJournal")
// @RequiredArgsConstructor
public class AiRecipeController {

    private final ChatClient chatClient;
    private final UserService userService;
    // Listan används för att AI ska komma ihåg tidigare samtal
    // Inne i listan använder Message från ai.chat
    private final List<Message> conversation = new ArrayList<>();

    // Constructor
    public AiRecipeController(ChatClient.Builder chatClienBuilder, UserService userService) {
        this.chatClient = chatClienBuilder.build();
        this.userService = userService;

        // Skapar grundläggande AI personlighet, ska svara på svenska
        // Ge recept på nyttig mat för att känna sig lugn & få mer energi
        final String systemMessageString = """
                Du är MindMate - en vänlig och omtänksam hälsocoach.
                Ditt mål är att hjälpa användaren att må bättre genom hälsosamma matvanor.
                Ge förslag på enkla, nyttiga recept som kan bidra till bättre egergi, lugn eller balans.
                Förklar kort varför receptet är bra för hälsan eller sinnesstämningen.
                Skriv alltid på ett varmt och positivt sätt, som en empatisk vän.
                Undvik att ge medicinska råd, men uppmuntra till egenvård och balans. Håll svaren korta, tydliga och inspirerande!
                """;

        conversation.add(new SystemMessage(systemMessageString));
    }

}
