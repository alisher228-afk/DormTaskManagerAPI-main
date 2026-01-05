# DormTaskManagerAPI

DormTaskManagerAPI is a RESTful backend service designed to manage tasks and responsibilities in a dormitory environment.  
The system allows organizing tasks, assigning responsibilities, and managing core entities through a clean and structured API.

This project is intended as a main backend project and is actively evolving.

---

## Features

- Task management (create, update, delete, retrieve)
- RESTful API design
- DTO-based data transfer
- Entity–DTO mapping with MapStruct
- Layered architecture (Controller / Service / Repository)
- Data validation
- Clean separation of concerns

---

## Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- MapStruct
- Maven
- PostgreSQL (or H2 for development/testing)

---

## Architecture

The project follows a layered architecture approach:

- **Controller layer** — handles HTTP requests and REST endpoints
- **Service layer** — contains business logic
- **Repository layer** — database access using Spring Data JPA
- **DTO layer** — separates API models from persistence entities
- **Mapper layer** — entity-to-DTO mapping using MapStruct

This structure improves maintainability, testability, and scalability.

---

## How to Run the Project

### Prerequisites
- Java 17 or higher
- Maven
- PostgreSQL (optional, H2 can be used)

### Steps
1. Clone the repository
2. Configure database settings in `application.yml`
3. Run the application:
```bash
mvn spring-boot:run
The application will start on the default Spring Boot port.

 API
The application exposes REST endpoints for managing dormitory tasks and related entities.

Swagger/OpenAPI documentation will be added in future updates.

 Future Improvements
JWT-based authentication and authorization

Role-based access control (ADMIN / USER)

Integration and unit tests

Swagger / OpenAPI documentation

Docker support

Centralized exception handling and logging

 Author
Alisher Baymuratov
Backend Developer (Java / Spring Boot)

 License
This project is developed for educational and portfolio purposes.