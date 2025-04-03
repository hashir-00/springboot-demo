# Project Setup

**Prerequisites**

- Java 23
- Maven
- PostgreSQL
- Google Drive API credentials

Before setup make sure to get a credential file for a service account with editor
access and Oauth initiated credential file from google cloud console.
Save it inside resources folder

**Steps**

1. Clone repository
2.  Add .env file in the project directory(not src) with:
- MY_DB_URL=<your_database_url> (if you are planning to use docker instead use "jdbc:postgresql://db:5432/springboot")
- MY_DB_USERNAME=<your_database_username>
- MY_DB_PASSWORD=<your_database_password>
- MY_GITHUB_OAUTH_CLIENT_ID=<your_github_oauth_client_id>
- MY_GITHUB_OAUTH_CLIENT_SECRET=<your_github_oauth_client_secret>
- MY_SERVER_PORT=8005
- MY_JWT_SECURITY_KEY=<your_jwt_security_key>
- MY_JWT_SECURITY_KEY_EXP_TIME=<your_jwt_expiration_time>
- MY_REFRESH_TOKEN_TTL=<your_refresh_token_ttl>
- MY_CLIENT_URL=<your_client_application_url>
- MY_GOOGLE_DRIVE_ID=<your_google_drive_folder_id>
- MY_APPLICATION_NAME=<your_application_name>
- MY_SPRING_PROFILE=<your_application_profile_name>
- MY_SERVER_ADDRESS=0.0.0.0
- 
3. Build:
- `mvn wrapper:wrapper` to install mvn wrapper
- `./mvnw clean install` (optional for mvn wrapper)
- `mvn clean install`
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
- Sanitized inputs
- PDF upload system
- Google Drive integration
- Migration for Specific ID format
- Dockerize
- CICD in cloud

---

**Testing**

- Run tests: `mvn test`

---
