package com.MyJournal.MyJournalApi.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/myJournal/payments")
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular dev server
public class StripeWebhookController {

}
