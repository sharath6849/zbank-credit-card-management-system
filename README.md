# Z Bank Credit Card Management System

## Overview

A microservices-based Credit Card Management System built using Spring Boot, PostgreSQL, REST APIs, Maven Multi-Module Architecture, Security, Validation, Audit Logging and Unit Testing.

## Architecture

Customer Service (8081)
↓
Credit Score Service (8082)
↓
Card Service (8083)

## Microservices

### Customer Service

Responsibilities:
- Customer onboarding
- Customer information management
- Credit score storage

### Credit Score Service

Responsibilities:
- Credit score calculation
- Score persistence

Rules:
- Cards >= 2 → 300
- Salary > 200000 → 500
- Salary > 50000 → 150
- Salary <= 50000 → 50

### Card Service

Responsibilities:
- Credit card application
- Card allocation
- PIN generation
- PIN change
- Audit logging

## Card Allocation Rules

| Score | Card Type | Limit |
|---------|---------|---------|
| 500 | PLATINUM | 40000 |
| 300 | GOLD | 20000 |
| 150 | VISA | 10000 |
| 50 | DOCUMENT_REQUIRED | 0 |

## APIs

### Customer Service

- POST /api/v1/customers
- GET /api/v1/customers/{id}
- GET /api/v1/customers/document/{documentNumber}
- PUT /api/v1/customers/{id}/credit-score

### Credit Score Service

- POST /api/v1/credit-score/calculate

### Card Service

- POST /api/v1/cards/apply
- POST /api/v1/cards/change-pin

## Security

- BCrypt PIN encryption
- Validation
- Global Exception Handling

## Audit Logging

- PIN_GENERATED
- PIN_CHANGED

## Technologies

- Java 25
- Spring Boot 4.1.2
- Spring Data JPA
- PostgreSQL
- Spring Security
- BCrypt
- Swagger/OpenAPI
- JUnit 5
- Mockito
- Maven Multi Module

## Swagger URLs

Customer Service:
http://localhost:8081/swagger-ui/index.html

Credit Score Service:
http://localhost:8082/swagger-ui/index.html

Card Service:
http://localhost:8083/swagger-ui/index.html
