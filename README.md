# DigiWallet

Event-driven **microservices** system for a digital wallet platform, built with Spring Boot and RabbitMQ.

## Architecture

```
                ┌──────────────────┐
                │     RabbitMQ     │  (event bus)
                └──────────────────┘
                  ▲      ▲      ▲
        events    │      │      │   events
        ┌─────────┘      │      └──────────┐
        │                │                 │
┌───────────────┐ ┌─────────────┐ ┌──────────────────────┐
│    project    │ │  statement  │ │  notificationservice │
│ (core wallet) │ │ (statements)│ │   (notifications)    │
└───────────────┘ └─────────────┘ └──────────────────────┘
        │                │                 │
        └──── PostgreSQL ─┴─────────────────┘
```

## Services

| Service | Açıklama | Stack |
|---|---|---|
| [project](project) | Çekirdek cüzdan / hesap servisi | Spring Boot, JPA, AMQP, PostgreSQL |
| [statement](statement) | Hesap ekstresi servisi | Spring Boot, JPA, AMQP, PostgreSQL |
| [notificationservice](notificationservice) | Bildirim servisi | Spring Boot, AMQP |

Servisler birbirleriyle **RabbitMQ üzerinden olay (event) tabanlı** haberleşir; her servisin kendi veritabanı vardır.

## Geliştirme

Bu depo bir **monorepo**'dur — her servis kendi Gradle projesidir.

VSCode'da multi-root workspace olarak aç:

```
File > Open Workspace from File... > digiwallet.code-workspace
```

Tek bir servisi çalıştırmak:

```bash
cd statement
./gradlew bootRun
```

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Data JPA
- Spring AMQP (RabbitMQ)
- PostgreSQL
- Gradle
