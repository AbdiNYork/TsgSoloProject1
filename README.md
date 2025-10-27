# TsgSoloProject1
# 🏥 Member Benefits Dashboard

A **4-screen member portal** for viewing healthcare claims, plan details, and accumulators.  
Built as a 2-week scoped full-stack application featuring **federated authentication (OIDC)**, **React frontend**, **Spring Boot backend**, and **PostgreSQL** persistence.

---

## 🚀 Overview

The **Member Benefits Dashboard** provides members with a simple, secure, and modern way to:

- Sign in with an external identity provider (e.g., Google via OIDC)
- View their plan and accumulator progress
- Browse, filter, and search their claims
- Drill into individual claim details for financial breakdowns
---

## 🧩 Tech Stack

| Layer            | Technology                 |
|------------------|----------------------------|
| Frontend         | React (Vite), React Router |
| Backend          | Java 17+, Spring Boot 3.x  |
| Database         | PostgreSQL 14+             |
| Auth             | Federated OIDC (Google)    |
| ORM              | JPA / Hibernate            |
| API              | REST (JSON over HTTP)      |
| Containerization | Docker / Docker compose    |

---

## 📱 Application Structure
<prev>
```
project-root/
├── docker-compose.yml
├── README.md
├── client/                 # Frontend (React + Vite)
│   ├── package.json
│   ├── src/
│   │   ├── Views/
│   │   ├── api/
│   │   ├── components/
│   │   └── utils/
│   └── public/
├── server/                 # Backend (Spring Boot)
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/net/tsg_projects/server/
│       │   │   ├── Auth/
│       │   │   ├── Config/
│       │   │   ├── Controller/
│       │   │   ├── Dto/
│       │   │   ├── Entity/
│       │   │   ├── Repository/
│       │   │   └── Service/
│       │   └── resources/
│       │       ├── application.properties
│       │       └── templates/
│       └── test/
└── ...
```
</prev>


### 4 Core Screens

1. **Login (OIDC Federated)**
    - “Continue with Google” or similar IDP sign-in
    - Redirects to Dashboard after successful authentication

2. **Dashboard**
    - Displays active plan details, accumulator progress, and recent claims
    - Links to Claims List and Claim Detail

3. **Claims List**
    - Filterable, paginated claims table
    - Columns: Claim #, Service Dates, Provider, Status, Member Responsibility
    - Server-side filtering & pagination

4. **Claim Detail**
    - Claim header (provider, service dates, status)
    - Financial summary (billed, allowed, plan paid, member responsibility)
    - Line-item breakdown
    - Back navigation to Claims List

---

## ⚙️ Setup Instructions

### 1. Clone the Repository

```
git clone https://github.com/York-Solutions-B2E/AbdiNBenefits.git
```


### 2. Start Database in docker
```
docker compose up
```
### 3. Run the server
```
cd server
```
run application through editor
##### Server runs on localhost:8080

### 4. Run the client
```
cd frontend
npm install
npm run dev
```
##### Client runs on localhost:5173

---
### 5. After login -

On first signin the servers 'MockDataFactory' creates a user + member along with mock data for Plan, Provider, Enrollment, Claim...:

- After signin, user is redirected to the member dashboard to view Plan and previous claims
- Clicking a claim will navigate member to claims detail page
- On Claimslist page browse and filter claims
---

