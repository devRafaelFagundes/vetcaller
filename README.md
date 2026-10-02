# VetVoice AI 🐾📞

**VetVoice AI** is an innovative management system for veterinary clinics focused on automating appointment scheduling through phone calls using voice Artificial Intelligence. 

The main objective is to allow pet owners to call the clinic at any time and converse with a natural and humanized virtual assistant, capable of checking the clinic's schedule and booking appointments in real-time, without the need for human intervention.

## 💡 Project Core

The system architecture is divided into three main pillars:

1. **Service (Voice):** An AI telephony agent answers the call, listens to the client's request, and converses naturally.
2. **Brain (Backend):** A robust API that receives real-time requests from the AI to check available time slots, handle concurrency (prevent double bookings), and save the appointment.
3. **Management (Frontend):** An administrative dashboard where the veterinarian or secretary can view the schedule, configure operating hours, and manage clients and patients (pets).

## 🛠️ Technology Stack

The tools chosen to bring this project to life are:

### Backend (API and Business Rules)
* **Java + Spring Boot:** The main engine of the application, responsible for all business rules and orchestration.
* **Spring Web:** For creating the Webhooks (endpoints) that the voice AI will consume.
* **Spring Data JPA / Hibernate:** For object-relational mapping and database communication.
* **Spring Security:** To protect the API and create the administrative login system.

### Database
* **PostgreSQL:** A robust relational database, ideal for handling the complexity of calendar queries, time zones, and ensuring appointment integrity (preventing *double-booking*).

### Artificial Intelligence and Telephony
* **Vapi.ai:** *Voice Agent* platform that orchestrates the call.

### Frontend (Administrative Dashboard)
* **Angular:** Framework for building the SaaS-format web dashboard.
* *(To be defined - Tailwind CSS / Angular Material / FullCalendar)*

🚧 **In development** 🚧