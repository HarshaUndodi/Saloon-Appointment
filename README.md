<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-4.0.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white" />
  <img src="https://img.shields.io/badge/Chart.js-FF6384?style=for-the-badge&logo=chartdotjs&logoColor=white" />
  <img src="https://img.shields.io/badge/Vanilla%20JS-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black" />
</p>

# ✂️ Classic Hair Studio — Salon Booking System

A full-stack salon appointment booking platform with a **customer web app**, a **PIN-protected admin dashboard**, and a **Spring Boot REST API** backed by MongoDB. Everything is self-contained — the entire frontend lives in a single HTML file, and the backend follows a clean MVC architecture.

---

## 📑 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Getting Started](#-getting-started)
- [API Reference](#-api-reference)
- [Data Models](#-data-models)
- [Key Flows](#-key-flows)
- [Configuration](#-configuration)
- [Roadmap](#-roadmap)
- [License](#-license)

---

## ✨ Features

### 👤 Customer Side

| Feature | Description |
|---|---|
| **Landing Page** | Hero section, feature cards, and sticky navigation |
| **Phone Login** | Simple login by name + phone number |
| **Gold/Dark Calendar** | Custom-themed date picker with visual flair |
| **Thursday Auto-Block** | Shop closed on Thursdays — auto-blocked in calendar |
| **Closure Announcements** | Admin-set closures show as yellow-striped blocked dates |
| **Smart Slot Filtering** | Past time slots hidden automatically for today's date |
| **Multi-Service Booking** | Select services → barber → date → time slot |
| **Booking Confirmation** | Summary card with all appointment details |
| **Shop Location** | Location card with *Get Directions* button |
| **Browser Reminders** | 30-minute reminder via browser notifications |
| **Review & Rating** | Post-service modal (1–5 stars + optional comment) |

### 🔧 Admin Dashboard

| Feature | Description |
|---|---|
| **PIN-Protected Login** | Secure admin access |
| **Appointments Table** | View all bookings — Cancel / Delete / ✅ Mark Done |
| **Today's Queue** | Upcoming appointments with live time badges |
| **Service Management** | Add / Delete salon services |
| **Barber Management** | Add / Delete barbers (with UPI ID), toggle availability |
| **Closure Announcements** | Set date-range closures with reason |
| **Earnings Tracker** | Today / Week / Month revenue (counts only `DONE` appointments) |
| **Per-Barber Analytics** | Earnings cards with 7-day sparkline charts |
| **Revenue Comparison** | Bar chart comparing barber earnings |
| **Daily Trend Chart** | Per-barber line chart — last 14 days |
| **Reviews Tab** | View all reviews, per-barber averages, admin delete option |

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| **Frontend** | Single HTML file — Vanilla JS, CSS, Chart.js |
| **Backend** | Spring Boot 4.0.4 (Java 17) |
| **Database** | MongoDB |
| **Build Tool** | Maven (with Maven Wrapper) |
| **Annotations** | Lombok (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`) |

---

## 🏗 Architecture

```
┌──────────────────────────┐        REST API         ┌──────────────────────┐
│                          │  ◄──── fetch() ────►    │                      │
│    index.html            │                         │   Spring Boot :8081  │
│  (Customer + Admin UI)   │   JSON over HTTP        │                      │
│                          │                         │   Controllers        │
│  • Vanilla JS            │   GET / POST / PATCH    │   Services           │
│  • Chart.js              │   DELETE                │   Repositories       │
│  • Custom CSS            │                         │                      │
└──────────────────────────┘                         └──────────┬───────────┘
                                                                │
                                                                │ Spring Data
                                                                │ MongoDB
                                                                ▼
                                                     ┌──────────────────────┐
                                                     │      MongoDB         │
                                                     │  (localhost:27017)   │
                                                     │   database: test     │
                                                     └──────────────────────┘
```

---

## 📁 Project Structure

```
salon-booking-system/
├── pom.xml
├── mvnw / mvnw.cmd
├── src/
│   ├── main/
│   │   ├── java/com/salon/booking/
│   │   │   ├── SalonBookingSystemApplication.java      ← Entry point
│   │   │   ├── model/
│   │   │   │   ├── Appointment.java                    ← Booking data
│   │   │   │   ├── Barber.java                         ← Barber profile + UPI
│   │   │   │   ├── SalonService.java                   ← Service (name, price, duration)
│   │   │   │   ├── User.java                           ← Customer (name, phone, email)
│   │   │   │   └── Review.java                         ← Rating + comment
│   │   │   ├── repository/
│   │   │   │   ├── AppointmentRepository.java
│   │   │   │   ├── BarberRepository.java
│   │   │   │   ├── ServiceRepository.java
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── ReviewRepository.java
│   │   │   ├── controller/
│   │   │   │   ├── AppointmentController.java          ← Book, slots, cancel, complete
│   │   │   │   ├── BarberController.java               ← CRUD + toggle availability
│   │   │   │   ├── ServiceController.java              ← CRUD services
│   │   │   │   ├── UserController.java                 ← Register + phone login
│   │   │   │   └── ReviewController.java               ← Submit, list, delete reviews
│   │   │   ├── service/
│   │   │   │   └── AppointmentService.java             ← Booking logic + slot engine
│   │   │   └── dto/                                    ← (reserved for future DTOs)
│   │   └── resources/
│   │       ├── application.properties                  ← Port + MongoDB URI
│   │       ├── static/
│   │       │   └── index.html                          ← Full frontend (≈96 KB)
│   │       └── templates/
│   └── test/
│       └── java/com/salon/booking/
│           └── SalonBookingSystemApplicationTests.java
└── target/                                             ← Build output
```

---

## 📋 Prerequisites

| Requirement | Version |
|---|---|
| **Java JDK** | 17+ |
| **MongoDB** | 4.4+ (running on `localhost:27017`) |
| **Maven** | 3.8+ (or use the included `mvnw` wrapper) |

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/<your-username>/salon-booking-system.git
cd salon-booking-system
```

### 2. Start MongoDB

Make sure MongoDB is running locally:

```bash
# If installed as a service, it's likely already running.
# Otherwise:
mongod --dbpath /data/db
```

### 3. Build & Run

**Using the Maven Wrapper (no Maven install needed):**

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

**Or with Maven directly:**

```bash
mvn spring-boot:run
```

### 4. Open the App

Navigate to:

```
http://localhost:8081/index.html
```

- **Customer view** loads by default
- **Admin dashboard** is accessible via the admin login (PIN-protected)

---

## 📡 API Reference

All endpoints are prefixed with `/api`. The backend runs on **port 8081**.

### Appointments

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/appointments` | Book an appointment |
| `GET` | `/api/appointments/slots` | Get available time slots |
| `GET` | `/api/appointments/all` | Get all appointments (admin) |
| `GET` | `/api/appointments/{id}` | Get single appointment (polling) |
| `PATCH` | `/api/appointments/{id}/cancel` | Cancel an appointment |
| `PATCH` | `/api/appointments/{id}/complete` | Mark as done (admin) |
| `DELETE` | `/api/appointments/{id}` | Hard delete an appointment |

<details>
<summary><strong>Book Appointment — request params</strong></summary>

| Param | Type | Description |
|---|---|---|
| `userId` | `String` | Customer's user ID |
| `serviceIds` | `List<String>` | Selected service IDs |
| `barberId` | `String` | Preferred barber ID |
| `date` | `String` | Date in `YYYY-MM-DD` format |
| `time` | `String` | Time in `HH:MM` format |

**Response:**
```json
{ "status": "BOOKED", "id": "abc123", "barberId": "...", "startTime": "10:00", "endTime": "10:30" }
```
```json
{ "status": "BUSY", "message": "Preferred barber not available", "alternativeBarber": "xyz789" }
```
```json
{ "status": "FULL", "message": "No barbers available at this time" }
```

</details>

---

### Barbers

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/barbers` | Available barbers only |
| `GET` | `/api/barbers/all` | All barbers (admin) |
| `POST` | `/api/barbers` | Add a new barber |
| `PATCH` | `/api/barbers/{id}/toggle` | Toggle barber availability |
| `DELETE` | `/api/barbers/{id}` | Delete a barber |

<details>
<summary><strong>Add Barber — request body</strong></summary>

```json
{
  "name": "Ravi",
  "specialty": "All",
  "available": true,
  "upiId": "ravi@okaxis"
}
```

</details>

---

### Services

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/services` | List all services |
| `POST` | `/api/services` | Add a new service |
| `DELETE` | `/api/services/{id}` | Delete a service |

<details>
<summary><strong>Add Service — request body</strong></summary>

```json
{
  "name": "Haircut",
  "duration": 30,
  "price": 200.0
}
```

</details>

---

### Users

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/users` | Register a new user |
| `GET` | `/api/users/login?phone=...` | Login by phone number |
| `GET` | `/api/users/all` | Get all users (admin) |

---

### Reviews

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/reviews` | Submit a review |
| `GET` | `/api/reviews/all` | Get all reviews |
| `GET` | `/api/reviews/barber/{barberId}` | Reviews for a specific barber |
| `DELETE` | `/api/reviews/{id}` | Delete a review (admin) |

<details>
<summary><strong>Submit Review — request body</strong></summary>

```json
{
  "appointmentId": "abc123",
  "userId": "user456",
  "userName": "Harsh",
  "barberId": "barber789",
  "barberName": "Ravi",
  "rating": 5,
  "comment": "Great haircut!"
}
```

> Duplicate reviews for the same appointment are automatically rejected (`ALREADY_REVIEWED`).

</details>

---

## 🗃 Data Models

### Appointment

| Field | Type | Notes |
|---|---|---|
| `id` | `String` | Auto-generated MongoDB ID |
| `userId` | `String` | Reference to User |
| `serviceIds` | `List<String>` | Selected services |
| `barberId` | `String` | Assigned barber |
| `date` | `LocalDate` | Appointment date |
| `startTime` | `LocalTime` | Slot start |
| `endTime` | `LocalTime` | Slot end (auto-calculated) |
| `status` | `String` | `BOOKED` · `CANCELLED` · `DONE` |
| `paymentMode` | `String` | `cash` or `upi` |

### Barber

| Field | Type | Notes |
|---|---|---|
| `id` | `String` | Auto-generated |
| `name` | `String` | Unique (indexed) |
| `specialty` | `String` | `Hair` · `Beard` · `All` |
| `available` | `boolean` | Toggle via admin |
| `upiId` | `String` | UPI ID for payments |

### SalonService

| Field | Type | Notes |
|---|---|---|
| `id` | `String` | Auto-generated |
| `name` | `String` | Service name |
| `duration` | `int` | Duration in minutes |
| `price` | `double` | Price in ₹ |

### User

| Field | Type | Notes |
|---|---|---|
| `id` | `String` | Auto-generated |
| `name` | `String` | Customer name |
| `phone` | `String` | Unique (indexed) — used for login |
| `email` | `String` | Optional |

### Review

| Field | Type | Notes |
|---|---|---|
| `id` | `String` | Auto-generated |
| `appointmentId` | `String` | One review per appointment |
| `userId` | `String` | Reviewer |
| `userName` | `String` | Display name |
| `barberId` | `String` | Reviewed barber |
| `barberName` | `String` | Display name |
| `rating` | `int` | 1–5 stars |
| `comment` | `String` | Optional text |
| `date` | `LocalDate` | Auto-set on submission |

---

## 🔄 Key Flows

### Booking Flow

```
Customer selects services + barber + date + slot
        │
        ▼
  POST /api/appointments
        │
        ├── Preferred barber free? ──► BOOKED (returns appointment ID)
        │
        ├── Preferred busy, alternate free? ──► BUSY + suggests alternative
        │
        └── All barbers busy ──► FULL
```

### Mark Done → Review Flow

```
Admin clicks "Mark Done"
        │
        ▼
  PATCH /api/appointments/{id}/complete
        │  status → DONE
        ▼
  Customer polls GET /api/appointments/{id} every 5s
        │  detects status = DONE
        ▼
  Review modal appears (1–5 stars + comment)
        │
        ▼
  POST /api/reviews → saved to MongoDB
        │
        ▼
  Earnings tracker updates (counts DONE only)
```

### Closure Flow

```
Admin creates closure (date range + reason)
        │  saved to localStorage
        ▼
  Calendar renders yellow-striped blocked dates
        │
        ▼
  Customer cannot select those dates
  Banner shown if shop closed today
```

### Slot Generation Logic

- **Operating hours:** 09:00 – 21:00
- **Slot interval:** 30 minutes
- Slots are dynamically filtered — only slots where the barber is free for the total service duration are returned
- Past slots are hidden for today's date (frontend logic)
- Thursdays and admin-set closure dates are fully blocked

---

## ⚙️ Configuration

All configuration lives in `src/main/resources/application.properties`:

```properties
spring.application.name=salon-booking-system
server.port=8081
spring.data.mongodb.uri=mongodb://localhost:27017/test
```

| Property | Default | Description |
|---|---|---|
| `server.port` | `8081` | Backend HTTP port |
| `spring.data.mongodb.uri` | `mongodb://localhost:27017/test` | MongoDB connection string |

> **Tip:** Change the database name from `test` to `salon` for production use.

---

## 🗺 Roadmap

- [x] Customer booking web app
- [x] Admin dashboard with earnings & analytics
- [x] Review & rating system
- [x] Closure announcements
- [x] Per-barber earnings + sparkline charts
- [ ] **WhatsApp Booking Integration** — Customers book via WhatsApp chatbot (same MongoDB backend)
- [ ] SMS/Email appointment reminders
- [ ] Payment gateway integration (Razorpay / Stripe)
- [ ] Multi-branch support

---

## 📄 License

This project is for personal/educational use. No license specified.

---

<p align="center">
  <strong>Built with ☕ Java + 🍃 MongoDB + ✂️ Passion</strong>
</p>
