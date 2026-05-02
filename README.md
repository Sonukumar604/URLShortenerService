# URL Shortener Service

A modern, fast, and user-friendly **URL Shortening Service** built with **Spring Boot**, **PostgreSQL**, and **Thymeleaf**. Convert long URLs into shareable short codes with a single click.

## 🌟 Features

- **Instant URL Shortening** — Generate 6-character Base62 short codes in milliseconds
- **Duplicate Detection** — Returns existing short URL if the original URL was already shortened
- **Collision Handling** — Automatically retries on collision to ensure uniqueness
- **Secure Randomness** — Uses `SecureRandom` for cryptographically strong short codes
- **Redirect Support** — Visit short URL to instantly redirect to the original link
- **URL Validation** — Validates input URLs with Hibernate validators
- **Expiration Tracking** — Optional expiration dates for URLs (stored, ready to implement)
- **Responsive UI** — Beautiful gradient-styled frontend with CSS styling
- **Database Persistence** — PostgreSQL integration with Hibernate ORM

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| **Backend Framework** | Spring Boot 4.1.0-RC1 |
| **Language** | Java 17 |
| **Database** | PostgreSQL 18.1 |
| **ORM** | Hibernate JPA |
| **Template Engine** | Thymeleaf |
| **Validation** | Jakarta Validation API |
| **Build Tool** | Gradle 9.4.1 |
| **Server** | Apache Tomcat 11.0.21 |

## 📋 Prerequisites

- **Java 17+** (Eclipse Temurin JDK 17.0.16+)
- **PostgreSQL 12+** (tested with PostgreSQL 18.1)
- **Git** for version control

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/Sonukumar604/URLShortenerService.git
cd URLShortenerService
```

### 2. Configure PostgreSQL

Create the `urlshortener` database:

```bash
psql -U postgres -d postgres -c "CREATE DATABASE urlshortener;"
```

Update `src/main/resources/application.properties` with your PostgreSQL credentials:

```properties
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 3. Build the Project

```bash
./gradlew clean build
```

### 4. Run the Application

```bash
./gradlew bootRun
```

The application will start on **http://localhost:8081**

## 🌐 Usage

### Web Interface

1. Open **http://localhost:8081** in your browser
2. Enter a long URL (e.g., `https://github.com`)
3. Click **"Shorten URL"**
4. Copy the generated short URL and share it
5. Visit the short URL to redirect to the original link

### Example

**Original URL:**
```
https://github.com/Sonukumar604/URLShortenerService
```

**Generated Short URL:**
```
http://localhost:8081/KVvYr8
```

## 🔌 API Endpoints

### GET `/`
- **Description:** Display the URL shortener form
- **Response:** HTML form page

### POST `/shorten`
- **Description:** Create a shortened URL
- **Request Body:**
  ```json
  {
    "url": "https://example.com/very/long/url"
  }
  ```
- **Response:** HTML result page with short URL details

### GET `/{shortCode}`
- **Description:** Redirect to the original URL
- **Parameters:**
  - `shortCode`: The 6-character short code (e.g., `KVvYr8`)
- **Behavior:** Redirects to original URL or home page if not found

## 📊 Database Schema

### `short_urls` Table

| Column | Type | Constraints |
|--------|------|-----------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| `short_code` | VARCHAR(10) | UNIQUE, NOT NULL |
| `original_url` | TEXT | NOT NULL |
| `created_at` | TIMESTAMP | NOT NULL, DEFAULT CURRENT_TIMESTAMP |
| `expires_at` | TIMESTAMP | NULL (optional) |

## 📁 Project Structure

```
URLShortenerService/
├── src/main/
│   ├── java/com/example/URLShortenerService/
│   │   ├── controller/
│   │   │   └── HomeController.java       # Web request handlers
│   │   ├── entity/
│   │   │   └── ShortUrl.java             # JPA entity
│   │   ├── model/
│   │   │   ├── ShortenUrlRequest.java    # DTO for requests
│   │   │   └── ShortenUrlResponse.java   # DTO for responses
│   │   ├── repository/
│   │   │   └── ShortUrlRepository.java   # Data access layer
│   │   ├── service/
│   │   │   └── UrlShortenerService.java  # Business logic
│   │   ├── util/
│   │   │   └── ShortCodeGenerator.java   # Base62 code generation
│   │   └── UrlShortenerServiceApplication.java
│   ├── resources/
│   │   ├── application.properties         # Spring Boot config
│   │   └── templates/
│   │       ├── home.html                 # Form page
│   │       └── result.html               # Result page
│   └── test/
└── build.gradle                          # Gradle configuration
```

## 🔐 Security Features

- **URL Validation** — Uses `@URL` annotation to validate proper URL format
- **Secure Randomness** — `SecureRandom` ensures unpredictable short codes
- **SQL Injection Prevention** — JPA parameterized queries
- **Input Sanitization** — Validates all user inputs before processing

## 🔧 Configuration

Edit `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8081

# PostgreSQL Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/urlshortener
spring.datasource.username=postgres
spring.datasource.password=khushi
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

## 🧪 Testing

Run the test suite:

```bash
./gradlew test
```

Build without tests (faster):

```bash
./gradlew build -x test
```

## 📦 Dependencies

Key dependencies defined in `build.gradle`:

- `spring-boot-starter-web` — Web and REST support
- `spring-boot-starter-data-jpa` — Database access
- `spring-boot-starter-thymeleaf` — Template engine
- `spring-boot-starter-validation` — Input validation
- `postgresql:postgresql:42.7.1` — PostgreSQL driver
- `org.projectlombok:lombok` — Boilerplate reduction

## 🚀 Deployment

### Docker (Optional)

Create a `Dockerfile`:

```dockerfile
FROM eclipse-temurin:17-jdk-focal
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:

```bash
docker build -t url-shortener .
docker run -p 8081:8081 url-shortener
```

### Cloud Platforms

- **Heroku** — `git push heroku main`
- **Railway** — Connect GitHub repo directly
- **Azure App Service** — Deploy via Spring Boot plugin
- **AWS Elastic Beanstalk** — JAR deployment

## 🤝 Contributing

Contributions are welcome! Please:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add your feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

## 📝 Future Enhancements

- [ ] URL expiration enforcement
- [ ] Analytics dashboard (view count, last accessed)
- [ ] Custom short codes
- [ ] QR code generation
- [ ] API key authentication
- [ ] Rate limiting
- [ ] Admin panel
- [ ] Dark mode toggle
- [ ] URL statistics and tracking

## 📄 License

This project is licensed under the **MIT License** — see the LICENSE file for details.

## 👤 Author

**Sonu Kumar**
- GitHub: [@Sonukumar604](https://github.com/Sonukumar604)
- Email: [your-email@example.com](mailto:your-email@example.com)

## 📞 Support

For issues, questions, or suggestions, please open an [issue](https://github.com/Sonukumar604/URLShortenerService/issues) on GitHub.

---

**Give this project a ⭐ if you find it useful!**
