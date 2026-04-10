# Budgette 💰

**SaaS de suivi de finances personnelles Mobile Money — Bénin 🇧🇯**

Gérez vos comptes **MTN Mobile Money** et **Moov Money** en un seul endroit. Consolidez vos soldes, suivez vos transactions et visualisez vos finances en **FCFA**.

---

## 🏗️ Structure du repo

```
budgette/
├── budgette-frontend/      # React + Vite + Tailwind CSS  (port 5173)
├── budgette-backend/       # Spring Boot 3 / Java 21 — Architecture Hexagonale Pure  (port 8080)
├── budgette-simulator/     # Spring Boot 3 / Java 21 — Faux endpoints MTN & Moov Money  (port 8081)
└── docker-compose.yml
```

### Communication entre les projets

```
budgette-frontend  ──(REST)──►  budgette-backend  (8080)
                                       │
                                       └──(REST)──►  budgette-simulator  (8081)
                                                      /api/mtn/...
                                                      /api/moov/...
```

> En production, il suffit de changer `MTN_API_URL` et `MOOV_API_URL` pour pointer vers les vraies APIs des opérateurs. Zéro modification de code.

---

## 🚀 Démarrage rapide avec Docker Compose

```bash
git clone https://github.com/charbelogb/budgette
cd budgette
docker compose up -d
```

| Service    | URL                                    |
|------------|----------------------------------------|
| Frontend   | http://localhost:5173                  |
| Backend    | http://localhost:8080                  |
| Simulator  | http://localhost:8081                  |
| Swagger (backend)   | http://localhost:8080/swagger-ui.html  |
| Swagger (simulator) | http://localhost:8081/swagger-ui.html  |

---

## 🖥️ Développement local

### Prérequis

- Java 21
- Maven 3.9+
- Node.js 20+
- PostgreSQL 15+ (ou Docker)

### 1. Base de données

```bash
docker run -d \
  --name budgette-postgres \
  -e POSTGRES_DB=budgette \
  -e POSTGRES_USER=budgette \
  -e POSTGRES_PASSWORD=budgette \
  -p 5432:5432 \
  postgres:15
```

### 2. Simulateur (port 8081)

```bash
cd budgette-simulator
mvn spring-boot:run
```

### 3. Backend (port 8080)

```bash
cd budgette-backend
mvn spring-boot:run
```

### 4. Frontend (port 5173)

```bash
cd budgette-frontend
npm install
npm run dev
```

---

## ⚙️ Variables d'environnement

### Backend (`budgette-backend`)

| Variable           | Par défaut                              | Description                       |
|--------------------|-----------------------------------------|-----------------------------------|
| `DATABASE_URL`     | `jdbc:postgresql://localhost:5432/budgette` | URL PostgreSQL                |
| `DATABASE_USER`    | `budgette`                              | Utilisateur PostgreSQL            |
| `DATABASE_PASSWORD`| `budgette`                              | Mot de passe PostgreSQL           |
| `MTN_API_URL`      | `http://localhost:8081/api/mtn`         | URL base API MTN                  |
| `MOOV_API_URL`     | `http://localhost:8081/api/moov`        | URL base API Moov Money           |
| `JWT_SECRET`       | `your-very-long-secret-key-...`         | Clé secrète JWT (à changer!)     |

---

## 🎭 Simulateur Mobile Money

Le simulateur expose des endpoints imitant les APIs MTN Mobile Money et Moov Money.

### Endpoints MTN Mobile Money (`/api/mtn/accounts`)

| Méthode | Endpoint                           | Description              |
|---------|------------------------------------|--------------------------|
| GET     | `/{accountId}/balance`             | Solde du compte          |
| GET     | `/{accountId}/transactions`        | Liste des transactions   |
| GET     | `/{accountId}/info`                | Infos du compte          |

### Endpoints Moov Money (`/api/moov/accounts`)

| Méthode | Endpoint                           | Description              |
|---------|------------------------------------|--------------------------|
| GET     | `/{accountId}/balance`             | Solde du compte          |
| GET     | `/{accountId}/transactions`        | Liste des transactions   |
| GET     | `/{accountId}/info`                | Infos du compte          |

**Comportements simulés :**
- 🎲 Données aléatoires réalistes (noms béninois, montants FCFA)
- ⏱️ Latence aléatoire (100-500ms)
- 💥 5% de chance d'erreur 503 (timeout simulé)

---

## 🏛️ Architecture Hexagonale (Backend)

Le backend implémente une **architecture hexagonale pure** (Ports & Adapters).

```
domain/                     ← Cœur — AUCUNE dépendance externe
├── model/                  ← Entités Java pures (User, Account, Transaction)
├── port/
│   ├── in/                 ← Use cases (interfaces)
│   └── out/                ← Ports sortants (interfaces)
└── service/                ← Logique métier pure

infrastructure/             ← Adapters
├── persistence/            ← JPA (annotations uniquement ici)
├── mobilemoney/            ← HTTP adapter (WebClient → simulator)
├── web/                    ← REST controllers
├── security/               ← JWT + Spring Security
└── config/                 ← Bean wiring, Swagger
```

**Règles strictes :**
- Le package `domain/` ne contient **aucune** annotation Spring, JPA ou autre framework
- Les ports sont des **interfaces Java pures**
- La logique métier est dans `domain/service/`
- Les adapters implémentent les ports dans `infrastructure/`

---

## 📱 Frontend — Pages disponibles

| Route            | Page                          |
|------------------|-------------------------------|
| `/`              | Landing page                  |
| `/login`         | Connexion                     |
| `/register`      | Inscription                   |
| `/dashboard`     | Tableau de bord               |
| `/accounts`      | Liste des comptes             |
| `/accounts/add`  | Ajouter un compte             |
| `/transactions`  | Historique des transactions   |

---

## 🌍 Contexte

| | |
|---|---|
| **Pays** | 🇧🇯 Bénin |
| **Opérateurs** | MTN Mobile Money, Moov Money |
| **Devise** | FCFA (XOF) |
| **Types de transactions** | SEND, RECEIVE, WITHDRAW, DEPOSIT, PAYMENT, AIRTIME |
