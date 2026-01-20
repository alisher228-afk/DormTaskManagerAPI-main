# DormTaskManagerAPI

Backend (Spring Boot) для управления задачами в общежитии: **комнаты**, **жители** и **задачи**.
Проект построен по layered-архитектуре (Controller → Service → Repository) и использует **DTO + MapStruct**.

> ⚠️ **Про безопасность (учебный режим):** сейчас JWT-секрет и время жизни токена заданы в коде (`JwtService`).
> Для продакшена их нужно вынести в переменные окружения / конфиги (и хранить секрет безопасно).

---

## Что уже реализовано

- CRUD для основных сущностей: **Room**, **User**, **Task**
- Аутентификация и авторизация: **JWT Bearer**
    - `POST /auth/register` — регистрация
    - `POST /auth/login` — логин (возвращает JWT)
- Роли: **ROLE_ADMIN** / **ROLE_USER**
- Ограничения доступа на уровне методов через `@PreAuthorize`
- Пагинация через `Page` / `PageRequest`
- DTO-ответы для списков/деталей (например, Rooms / Users)
- Глобальная обработка ошибок (`@RestControllerAdvice`)
- Swagger UI / OpenAPI (springdoc)

---

## Технологии

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA (Hibernate)
- Spring Security + Method Security
- JWT (jjwt)
- MapStruct
- Maven
- PostgreSQL (по умолчанию)
- H2 (dependency есть, можно включить для локальной разработки)

---

## Основные сущности (кратко)

- **Room** — комната
- **User** — житель (может быть привязан к комнате)
- **Task** — задача (имеет тип/статус)
- **AuthUser** — аккаунт для входа (username/password/role)

---

## Безопасность и роли

- Все эндпоинты (кроме `/auth/**`, Swagger и H2-console) требуют **Authorization: Bearer <token>**.
- Доступ по ролям (согласно `@PreAuthorize` в контроллерах):

### Auth
- `GET /auth/authUsers` — **только ADMIN**

### Rooms
- `POST /room/create` — **ADMIN**
- `GET /room` — **ADMIN или USER**
- `GET /room/{id}` — **ADMIN**
- `DELETE /room/delete/{id}` — **ADMIN**

### Tasks
- `GET /tasks` — **ADMIN или USER**
- `GET /tasks/{id}` — **ADMIN или USER**
- `POST /tasks/create` — **ADMIN**
- `DELETE /tasks/delete/{id}` — **ADMIN**
- `PUT /tasks/complete/{id}` — **USER**

### Users
- `POST /users/create` — требует аутентификацию (глобально), но без `@PreAuthorize` по роли
- `GET /users` — требует аутентификацию (глобально), но без `@PreAuthorize` по роли
- `GET /users/{id}` — **ADMIN или USER**
- `DELETE /users/delete/{id}` — **ADMIN или USER**
- `PUT /users/{id}/assign-room/{roomId}` — **ADMIN**

---

## Swagger / OpenAPI

После запуска:

- Swagger UI: `http://localhost:8086/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8086/v3/api-docs`

Swagger настроен под Bearer JWT (scheme: `bearerAuth`).

---

## Как запустить

### Требования

- Java 17+
- Maven
- PostgreSQL (или переключиться на H2)

### 1) Настройка базы

По умолчанию в `application.properties`:

- `server.port=8086`
- `spring.datasource.url=jdbc:postgresql://localhost:5438/postgres`
- `spring.datasource.username=postgres`
- `spring.datasource.password=12345`

Поменяй под свои значения (или подними Postgres на указанном порту).

### 2) Запуск

```bash
mvn spring-boot:run
```

Либо:

```bash
mvn clean package
java -jar target/*.jar
```

---

## Быстрый старт (пример через curl)

### 1) Регистрация

```bash
curl -X POST http://localhost:8086/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"user1","password":"12345","role":"ROLE_USER"}'
```

### 2) Логин (получить токен)

```bash
curl -X POST http://localhost:8086/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user1","password":"12345"}'
```

В ответе будет JWT (поле зависит от `LoginResponse`).

### 3) Вызов защищенного эндпоинта

```bash
curl http://localhost:8086/tasks?page=0\&size=5 \
  -H "Authorization: Bearer <PASTE_TOKEN_HERE>"
```

---

## Структура проекта

- `presentation/controller` — REST-контроллеры
- `application/service` — бизнес-логика
- `entity` — JPA-сущности
- `entity/repository` — Spring Data репозитории
- `application/Dto` — DTO-модели
- `application/mapper` — MapStruct-мапперы
- `security` — UserDetails, роли, SecurityService
- `security/jwt` — JWT-фильтр и сервис
- `exception` — глобальная обработка ошибок

---

## Идеи для следующих улучшений

- Вынести JWT secret/expiration в конфиг и сделать refresh-token
- Нормальные DTO для всех эндпоинтов (не возвращать entity напрямую)
- Покрыть сервисы unit-тестами + интеграционные тесты (Testcontainers)
- Docker / docker-compose (Postgres + приложение)
- Аудит (createdAt/updatedAt), логирование

---

## Автор

**Алишер Баймуратов** — Backend Developer (Java / Spring Boot)

## Лицензия

Проект сделан для обучения и портфолио.