# 🪙 Coin Collection API

A Spring Boot application to manage a collection of currencies and their coin/note denominations. It uses **Spring Boot**, **Spring Data JPA**, and **PostgreSQL** (or H2 for testing), with a clean architecture and high test coverage.

---

## 🚀 Features

- CRUD operations for **currencies** and **denominations**
- Search:
    - Currencies by `code`
    - Denominations by `value`, `series`, or both
- RESTful API with Spring Web
- H2 in-memory DB for development/testing
- PostgreSQL support via Docker
- Test coverage with JUnit 5 and Mockito
- Uses Java `record` classes for data modeling where applicable

---

## 🧱 Database Structure

### Currencies

| Field     | Type   |
|-----------|--------|
| id        | Long   |
| code      | String |
| info      | String |

### Denominations

| Field        | Type   |
|--------------|--------|
| id           | Long   |
| value        | Double |
| series       | String |
| imageUrl     | String |
| currency_id  | Long (FK) |

---

## ⚙️ Tech Stack

- Java 17+
- Spring Boot 3
- Spring Data JPA
- H2 / PostgreSQL
- Docker (for PostgreSQL)
- JUnit 5, Mockito
- Maven

---

## 🐳 Running with Docker (PostgreSQL)

Ensure Docker is installed, then:

```bash
docker-compose up -d
