# Project Setup

**Prerequisites**
- Java 23
- Maven
- PostgreSQL
- Google Drive API credentials

**Steps**
1. Clone repository
2. Update `application.properties` with:
   - Database URL, username, password
   - JWT secret key
   - Google Drive client ID/secret
3. Build: `mvn clean install`
4. Run: `mvn spring-boot:run`

---

**API Endpoints**
- `GET /swagger-ui.html` - API documentation

---

**Features**
- Database integration with PostgreSQL
- JWT-based authentication
- Swagger API documentation
- Logging for all controllers
- sanitized inputs
- PDF upload system
- Google Drive integration
- migration for Specific ID format
- Logging for all controllers
- CICD in cloud

---

**Testing**
- Run tests: `mvn test`

---
