# Budgette Backend 🇧🇯

Backend du SaaS **Budgette** — suivi de finances personnelles centré sur le **Mobile Money africain** (Bénin).

## Stack technique

| Composant | Technologie |
|---|---|
| Langage | Java 17 |
| Framework | Spring Boot 3.2.x |
| Base de données | PostgreSQL |
| ORM | Spring Data JPA + Hibernate |
| Auth | Spring Security + JWT (jjwt) |
| API HTTP externe | Spring WebFlux WebClient |
| Documentation API | Swagger / OpenAPI 3 (springdoc) |
| Port | 8080 |
| Architecture | **Hexagonale pure (Ports & Adapters)** |

---

## Architecture Hexagonale

Ce projet suit strictement l'**Architecture Hexagonale** (Ports & Adapters), aussi appelée *Clean Architecture* :

```
┌─────────────────────────────────────────────────────┐
│                    INFRASTRUCTURE                    │
│  ┌──────────────────────────────────────────────┐   │
│  │                   DOMAINE                    │   │
│  │                                              │   │
│  │   ┌──────────┐   ┌─────────┐   ┌────────┐  │   │
│  │   │  Models  │   │  Ports  │   │Services│  │   │
│  │   │ (Java pur│   │(in/out) │   │(logique│  │   │
│  │   │  aucune  │   │interfcs │   │métier) │  │   │
│  │   │ annotation│   │Java pur │   │        │  │   │
│  │   └──────────┘   └─────────┘   └────────┘  │   │
│  └──────────────────────────────────────────────┘   │
│                                                     │
│  Adapters (implémentent les ports) :                │
│  • persistence/ (JPA, PostgreSQL)                   │
│  • mobilemoney/ (WebClient → simulateur/vraie API)  │
│  • web/         (REST controllers)                  │
│  • security/    (JWT, Spring Security)              │
└─────────────────────────────────────────────────────┘
```

### Règles fondamentales

- ✅ Le package `domain/` ne contient **aucune** annotation Spring, JPA, Lombok, ou autre framework — uniquement du **Java pur**.
- ✅ Les **ports** (`port/in/` et `port/out/`) sont des interfaces Java pures définies dans le domaine.
- ✅ Les **adapters** dans `infrastructure/` implémentent les ports.
- ✅ Toute la **logique métier** réside dans le domaine.
- ✅ Le domaine ne dépend de rien d'externe.

---

## Structure du projet

```
src/main/java/com/budgette/backend/
│
├── domain/                          # ❤️ Cœur — aucune dépendance externe
│   ├── model/
│   │   ├── User.java
│   │   ├── Account.java
│   │   ├── Transaction.java
│   │   ├── Operator.java            # Enum : MTN, MOOV
│   │   ├── Country.java             # Enum : BJ (Bénin)
│   │   └── TransactionType.java     # Enum : SEND, RECEIVE, WITHDRAW, DEPOSIT, PAYMENT, AIRTIME
│   ├── port/
│   │   ├── in/                      # Use cases (ports entrants)
│   │   │   ├── RegisterUserUseCase.java
│   │   │   ├── LoginUseCase.java
│   │   │   ├── AddAccountUseCase.java
│   │   │   ├── SyncTransactionsUseCase.java
│   │   │   └── GetDashboardUseCase.java
│   │   └── out/                     # Ports sortants (repositories, providers)
│   │       ├── UserRepositoryPort.java
│   │       ├── AccountRepositoryPort.java
│   │       ├── TransactionRepositoryPort.java
│   │       └── MobileMoneyProviderPort.java
│   └── service/                     # Implémentations des use cases (logique métier)
│       ├── RegisterUserService.java
│       ├── LoginService.java
│       ├── AddAccountService.java
│       ├── SyncTransactionsService.java
│       └── GetDashboardService.java
│
└── infrastructure/
    ├── persistence/                 # Adapter JPA/PostgreSQL
    │   ├── entity/
    │   ├── repository/
    │   ├── mapper/
    │   └── adapter/
    ├── mobilemoney/                 # Adapter WebClient → simulateur/vraie API
    │   ├── MobileMoneyProviderAdapter.java
    │   ├── dto/
    │   └── mapper/
    ├── web/                         # Adapter REST (controllers, DTOs)
    │   ├── controller/
    │   ├── dto/
    │   └── mapper/
    ├── security/                    # JWT + Spring Security
    │   ├── JwtService.java
    │   ├── JwtAuthFilter.java
    │   └── SecurityConfig.java
    └── config/                      # Beans Spring (wiring domaine ↔ adapters)
        ├── WebClientConfig.java
        ├── BeanConfig.java
        └── SwaggerConfig.java
```

---

## Endpoints API

| Méthode | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/api/v1/auth/register` | ❌ | Inscription |
| `POST` | `/api/v1/auth/login` | ❌ | Connexion → retourne JWT |
| `POST` | `/api/v1/accounts` | ✅ JWT | Ajouter un compte Mobile Money |
| `POST` | `/api/v1/transactions/sync/{accountId}` | ✅ JWT | Synchroniser les transactions |
| `GET` | `/api/v1/dashboard` | ✅ JWT | Tableau de bord financier |

Documentation interactive : **http://localhost:8080/swagger-ui.html**

---

## Lancer le projet

### Prérequis

- Java 17
- Maven 3.9+
- PostgreSQL 15+
- (Optionnel) Le simulateur [budgette-simulator](https://github.com/charbelogb/budgette-simulator) sur le port 8081

### 1. Configurer la base de données PostgreSQL

```sql
CREATE DATABASE budgette;
CREATE USER budgette WITH PASSWORD 'budgette';
GRANT ALL PRIVILEGES ON DATABASE budgette TO budgette;
```

### 2. Variables d'environnement (optionnel)

Par défaut, l'application utilise :
- `DATABASE_URL=jdbc:postgresql://localhost:5432/budgette`
- `DATABASE_USER=budgette`
- `DATABASE_PASSWORD=budgette`
- `JWT_SECRET=budgette-secret-key-change-in-production-min32chars`
- `MTN_API_URL=http://localhost:8081/api/mtn`
- `MOOV_API_URL=http://localhost:8081/api/moov`

Pour surcharger, créez un fichier `.env` ou passez les variables en argument :

```bash
export DATABASE_URL=jdbc:postgresql://myhost:5432/budgette
export JWT_SECRET=my-super-secret-key-at-least-32-chars
```

### 3. Compiler et lancer

```bash
# Compiler
mvn clean package -DskipTests

# Lancer
mvn spring-boot:run

# Ou avec java -jar
java -jar target/budgette-backend-0.0.1-SNAPSHOT.jar
```

### 4. Accéder à l'API

- **API** : http://localhost:8080
- **Swagger UI** : http://localhost:8080/swagger-ui.html
- **OpenAPI JSON** : http://localhost:8080/v3/api-docs

---

## Exécuter les tests

```bash
mvn test
```

---

## Opérateurs Mobile Money supportés

| Opérateur | Nom complet |
|---|---|
| `MTN` | MTN Mobile Money Bénin |
| `MOOV` | Moov Money Bénin |

**Monnaie** : FCFA (Franc CFA)  
**Pays** : Bénin 🇧🇯 (`BJ`)

---

## Flux d'utilisation typique

```
1. POST /auth/register   → Créer un compte
2. POST /auth/login      → Obtenir un token JWT
3. POST /accounts        → Lier un compte MTN ou MOOV Money
4. POST /transactions/sync/{accountId}  → Récupérer les transactions
5. GET  /dashboard       → Voir le solde total et les dernières transactions
```
