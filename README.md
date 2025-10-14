## MindMateApi
This repository is clonned from MyJournalAPI to further development. That's why application name is MyJournalApi. This is the backend API for MindMateFrontend application. 

## Features
**Authentication**
- User registration and login system
- Secure authentication flow with JWT token
- Encrypted password

**Daily Journaling**
- Record daily mental condition with text descriptions
- Save and manage daily entries
- Delete en entry

**History**
- Endpoint to retrieve all current date entries
- Endpoint to retrieve all entries (including today's)

**Statistics**
- Endpoint to retrieve emotional pattern between two selected dates
- Percentage breackdown of emotions (e.g. Glad: 40%, Sad:15%, Worried:20%...)

## Architecture
This is an API built with:
- Java 21
- Spring Boot
- Maven
- Spring Security (JWT authentication) 
- MongoDB database

## Frontend Integration
The application has a seperate frontend service (https://github.com/affa24ju/MyJournalFrontend.git) built with:
- Angular
- TypeScript

## Getting started
**Prerequisites**
- Postman or any othe software, if you'd like to test endpoints without frontend
- Clone and run frontend application to run the whole application. 

## Installation
**Clone the repository**
```bash
git clone https://github.com/affa24ju/MyJournalApi.git
```
Open the application with preffered code editor

**Run the application**

To run the application select MyJournalApiApplication.java file and click Run icon.

## Testing with Postman
Open Postman (or any other similar software) use endpoints. Please keep in mind that all endpoints needs to have authorization token (Exception: /register and /login).
Please make sure that the application is running before you test with Postman.

**Add authorization token**
- You will get an authorization token while you login with username and password
- Copy this token
- Go to that endpoint you'd like to access
- Select 'Bearer Token' under 'Authorization' and paste it in the given field

## Endpoints
The application has the following endpoints:

**Create user & login**

**POST:** localhost:8080/api/auth/register

**GET:** localhost:8080/api/auth/getUsers

**POST:** localhost:8080/api/auth/login

**Journal entry & Statistics**

**POST:** localhost:8080/api/myJournal/createJournalEntry

**GET:** localhost:8080/api/myJournal/getAllEntries

**GET:** localhost:8080/api/myJournal/today

**GET:** localhost:8080/api/myJournal/getStats?startDate&endDate

**DELETE:** localhost:8080/api/myJournal/deleteJournalEntry/{entryId}

## Ex. JSON
**Create user/ Login**

`json { "username": "olle", "password": "olle12345" } `

**Create journal entry**

`json { 
  "note" : "Felling proud",
   "feeling": "PROUD"
} `

**Note:** For Better experience use with frontend application! There you could find emojies and grahical representation of statistics.  

## Future Development:
- Endpoint to update (pathc) journal entry
- Endpoint to update username/ password
- Add Admin role
- Admin can delete an user account

