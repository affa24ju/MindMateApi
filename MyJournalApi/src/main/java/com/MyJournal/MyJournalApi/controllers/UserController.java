package com.MyJournal.MyJournalApi.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyJournal.MyJournalApi.services.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular dev server
@RestController
@RequestMapping("/api/myJournal")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

}
