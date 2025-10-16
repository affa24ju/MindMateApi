package com.MyJournal.MyJournalApi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyJournal.MyJournalApi.services.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular dev server
@RestController
@RequestMapping("/api/myJournal")
@RequiredArgsConstructor
public class AiRecipeController {

    private final ChatClient chatClient;
    private final UserService userService;
    // Listan används för att AI ska komma ihåg tidigare samtal
    // Inne i listan använder Message från ai.chat
    private final List<Message> conversation = new ArrayList<>();

}
