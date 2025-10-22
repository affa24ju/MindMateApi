# MindMateApi
Det här är en vidareutveckling av **MyJournalApi**, därför behåller applikationen namnet MyJournalApi. Detta är backend-API:t till frontendapplikationen **MindMateFrontend**.

API:t hanterar användarauthentisering, daglig journalföring samt en AI-funktion som kan föreslå goda råd relaterad till välmående och recept på hälsosamma kost. Med premium funktion får man tillgång till statistiken. 

## ✨ Funktioner
**🔐Authentisering**
- Registrering och inloggning av användare
- Säker authenticering med **JWT-token**
- Krypterad lösenord

**📔Daglig journalföring**
- Registrera dagligt mentalt tillstån med textbeskrivning
- Spara och hantera dagliga inlägg
- Uppdatera och ta bort inlägg


**📅Historik**
- Hämta alla inlägg för dagens datum
- Hämta alla tidigare inlägg

**📊Statistik *(premium funktion)***
- Hämta känslomönster mellan två valda datum
- Prucentuell fördelning av känslor (e.g. Glad: 40%, Ledsen:15%, Orolig:20%...)

**🤖Fråga AI**
- Chatta med AI
- Be AI om förslag för hälsosamma livsstil eller receptförslag
- Om man inte frågar något ger AI ett receptförslag för hälsosamma kost med kort förklaring varför recepten är bra för hälsan
- AI kan referera till tidigare konversationer

**💳Betalningstjänst**
- Btalningstjäns från Stripe för att få tillgång till premium funktion
- 
## 🧩Arkitektur
API:t är byggt med:
- **Java 21**
- **Spring Boot**
- **Maven**
- **Spring Security (JWT authentication)**
- **Spring AI-OpenAI API**
- **MongoDB-databas**
- **Stripe**

## 🖥️Frontend-integration
Frontend applikation finns seperat här:

  🔗(https://github.com/affa24ju/MyJournalFrontend.git) 

Byggd med:

- Angular
- TypeScript

## 🚀Kom igång
**✅Förutsättningar**
För att test API:t behöver du:
- **Postman** (eller liknande verktyg) för att testa endpoints utan frontend
- **Frontend-applikation** (om du vill köra hela systemet tillsammans)

## 📥Installation
**1. Klona projektet**
```bash
git clone https://github.com/affa24ju/MyJournalApi.git
```
**2. Öppna projektet**

Öppna repository i din favorit IDE(t.ex. VS Code, Intellij eller Eclipse)

**3. Skapa .env fil**

Skapa en .env fil med:

```bash
OPEN_AI_API_KEY="yourOpenAiApiKey"

STRIPE_SECRET_KEY="yourStripeSecretKey"
```

Spara filen.

**4. Kör applikationen**

Starta applikationen genom att köra MyJournalApiApplication.java.

Obs! Om det visar fel meddelande i pom.xml fil, är det bara köra 'relod maven project'!

## 🧪Testa med Postman
Öppna **Postman** (liknande ett verktyg) för att testa API:t.

**Observera** att de flesta endpoint kräver authentisering med JWT-token (undantag: `/register` och `/login`).

**🔑Lägg till authentiseringstoken**
1. Skapa en användare med användernamn och lösenord 
2. Logga in med samma användarnamn och lösenord 
3. Kopiera den token som returneras
4. Gå till önskad endpoint
5. Välj **Bearer Token** under *Authorization* och klirstra in din token

**💬Fråga AI**

För att fråga AI:n:
- Använd din JWT-token under *Authorization → Bearer Token*
- Skicka en förfrågan till /suggest-recipe
- Lägg till en parameter med nyckeln *message* och skriv din fråga som värde
- Om du inte lägger till *message* parameter och skickar får du ett receptförslag som är bra för hälsa

## 🔗API-endpoints

### 👤Användare

**POST** `localhost:8080/api/auth/register` -- Registrera användare

**GET** `localhost:8080/api/auth/getUsers` -- Hämta alla användare

**POST** `localhost:8080/api/auth/login` -- Logga in

### 📔Journalinlägg & statistik

**POST** `localhost:8080/api/myJournal/createJournalEntry` -- Skapa inlägg

**GET** `localhost:8080/api/myJournal/getAllEntries` -- Hämta alla inlägg

**GET** `localhost:8080/api/myJournal/today` -- Hämta dagens inlägg

**GET** `localhost:8080/api/myJournal/getStats?startDate&endDate` -- Hämta statistik

**DELETE** `localhost:8080/api/myJournal/deleteJournalEntry/{entryId}` -- Ta bort inlägg

**PUT** `localhost:8080/api/myJournal/updateJournalEntry/{entryId}` -- Uppdatera inlägg

### 🤖Chatta med AI

**GET:** `localhost:8080/api/myJournal/suggest-recipe` -- få receptförslag/ råd att må bättre

### 💳Betalningstjänst

**POST** `localhost:8080/api/myJournal/payments/create-checkout-session` -- anropar Stripe API

## Ex. JSON
**Registrera / Logga in**

`json { "username": "olle", "password": "olle12345" } `

**Skapa journal inlägg**

`json { 
  "note" : "Felling proud",
   "feeling": "PROUD"
} `

**💡** För bästa upplevelse - använd API:t tillsammans med  **frontendapplicationen**,  där du får emojis och grafisk statistik.  

## 🔮Framtida utvecklingsmöjligheter:
- Endpoint för att uppdatera användarnamn/ lösenord
- Administratörroll
- Möjligheter för admin att radera användarkonton
- Flera olika abonemang
- Uppdatera AI delen med olika specefika tjänster t.ex. förslag på fritids aktivititer och mm
