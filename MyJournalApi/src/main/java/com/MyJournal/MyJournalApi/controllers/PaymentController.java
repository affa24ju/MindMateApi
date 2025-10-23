package com.MyJournal.MyJournalApi.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyJournal.MyJournalApi.models.User;
import com.MyJournal.MyJournalApi.services.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/myJournal/payments")
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular dev server
@RequiredArgsConstructor
public class PaymentController {

        private final UserService userService;

        // Hämtar STRIPE_SECRET_KEY från application.properties
        @Value("${stripe.secret.key}")
        private String stripeSecretKey;

        @PostMapping("/create-checkout-session")
        public ResponseEntity<Map<String, String>> createCheckoutSession() throws StripeException {
                // Sätt Stripe API-nyckeln
                Stripe.apiKey = stripeSecretKey;

                User currentUser = userService.getCurrentUser();

                // Skapa en ny checkout session
                SessionCreateParams params = SessionCreateParams.builder()
                                .setMode(SessionCreateParams.Mode.PAYMENT)
                                .setSuccessUrl("http://localhost:4200/premium-success?session_id={CHECKOUT_SESSION_ID}")
                                .setCancelUrl("http://localhost:4200/payment-cancel")
                                .addLineItem(
                                                SessionCreateParams.LineItem.builder()
                                                                .setQuantity(1L)
                                                                .setPriceData(
                                                                                SessionCreateParams.LineItem.PriceData
                                                                                                .builder()
                                                                                                .setCurrency("sek")
                                                                                                .setUnitAmount(2000L) // Pris
                                                                                                                      // 20.00
                                                                                                                      // SEK
                                                                                                .setProductData(
                                                                                                                SessionCreateParams.LineItem.PriceData.ProductData
                                                                                                                                .builder()
                                                                                                                                .setName("Premium Subscription")
                                                                                                                                .build())
                                                                                                .build())
                                                                .build())
                                .putMetadata("userId", currentUser.getId()) // Lägger till användar-ID i metadata
                                .build();

                Session session = Session.create(params);

                return ResponseEntity.ok(Map.of("url", session.getId()));
        }

}
