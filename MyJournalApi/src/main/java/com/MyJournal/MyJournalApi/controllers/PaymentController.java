package com.MyJournal.MyJournalApi.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@RestController
@RequestMapping("/api/myJournal/payments")
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular dev server
public class PaymentController {

    // Hämtar STRIPE_SECRET_KEY från miljövariabler
    @Value("${STRIPE_SECRET_KEY}")
    private String stripeSecretKey;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession() throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        // Kod från Stripe-dokumentation för att skapa en checkout session
        // https://docs.stripe.com/payments/accept-a-payment
        // Har justerat för att passa en prenumeration på premium
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                // Vid lyckad betalning, skicka användaren till premium-sidan
                // på frontend, i premium-success-page visas ett tack-meddelande
                .setSuccessUrl("http://localhost:4200/premium-success")
                // Om användaren avbryter betalningen, kommer till payment-cancel sidan
                .setCancelUrl("http://localhost:4200/payment-cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("sek")
                                                .setUnitAmount(2000L) // 20.00 SEK, i ören
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData
                                                                .builder()
                                                                .setName("Premium Subscription")
                                                                .build())
                                                .build())
                                .build())
                .build();

        Session session = Session.create(params);
        // Returnerar URL för Stripe-hosted checkout page, som frontend ska
        // redirecta användaren till
        return ResponseEntity.ok(Map.of("url", session.getUrl()));

    }

}
