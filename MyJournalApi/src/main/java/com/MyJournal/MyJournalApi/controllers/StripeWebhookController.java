package com.MyJournal.MyJournalApi.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyJournal.MyJournalApi.services.UserService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/myJournal/payments")
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular dev server
@RequiredArgsConstructor
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    private final UserService userService;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
            @RequestHeader(value = "Stripe-Signature", required = false) String sigHeader) {
        // Event från Stripe
        Event event;
        try {
            event = Webhook.constructEvent(
                    payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            System.out.println("⚠️  Ogiltigt Stripe signature: " + e.getMessage());
            // Signaturen är ogiltig
            return ResponseEntity.badRequest().body("Invalid signature");
        } catch (Exception e) {
            System.out.println("⚠️  Fel vid webhook event parsing: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error parsing webhook event");
        }
        System.out.println("✅  Mottog enent från Stripe: " + event.getType());

        // Hanterar checkout.session.completed event
        if ("checkout.session.completed".equals(event.getType())) {
            // Session från Stripe.checkout; Hämtar session objektet från eventet
            Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);

            if (session != null) {
                // Kontrollerar faktiska betalningstatus, inte bara skapandet av sessionen
                // t.ex. session.getPaymentStatus() är "paid" om betalningen lyckades
                // String paymentStatus = session.getPaymentStatus();
                // Hämtar användar-ID från metadata, satte det när checkout session skapades i
                // PaymentController
                String userId = session.getMetadata() != null ? session.getMetadata().get("userId") : null;

                if (userId != null) {
                    // Uppdaterar användarens premium-status
                    userService.setPremium(userId, true);
                    System.out.println("User med " + userId + " upgraded to premium.");
                } else {
                    System.out.println("Ingen userId i metadata.");
                }

            } else {
                System.out.println("Kunde inte läsa session objektet från webhook eventet.");
            }

        }
        // Svarar med 200 OK för att stripe vet att jag mottagit eventet
        return ResponseEntity.ok("Webhook received");
    }

}
