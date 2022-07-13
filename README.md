# API for universities
## Table of Contents
* [Project purpose](#purpose)
* [Project structure](#structure)
* [For developer](#developer-start)
* [Author](#author)

## <a name="purpose"></a>Project purpose
Creation of the platform where teachers can create tests and students can pass them.
<hr>
Without being authenticated you can register and login.
When a new user registers, his account is activated by default, and he will have the USER role.
Thus, any registered person gets access to the system, but not to all its functions.
To get teacher's or student's privileges, you need to send a request to the administrator, who can confirm your role.
<hr>

## <a name="structure"></a>Project Structure
* Java 11
* hibernate-core 5.6.9.Final
* org.flywaydb.flyway 8.5.11
* com.h2database:h2 2.1.214
* hibernate-validator 6.2.3.Final
* io.jsonwebtoken:jjwt-api 0.11.5
* org.springframework.boot 2.7.0
* org.postgresql:postgresql 42.3.5
* org.projectlombok:lombok 1.18.24
* io.springfox:springfox-swagger-ui 3.0.0
* spring-security-core, spring-security-config, spring-security-web 5.7.1
<hr>

## <a name="developer-start"></a>For developer

1. Open the project in your IDE.

2. Add Java SDK 11 or above in Project Structure.

3. Install PostgreSQL if you don't have it or use [Docker Desktop](https://docs.docker.com/).

4. Configure your datasource properties to create a connection:
 + Host: localhost
 + Port: 5432
 + Set your username and password (don't forget to change also properties in [application.yaml](https://github.com/Natanius18/internship/blob/main/src/main/resources/application.yaml)).
 + Database: university

5. Run the project.

6. For testing this API you can use Postman or visit [Swagger UI page](http://localhost:8080/university/swagger-ui/index.html).

7. For authorization, you must add a new header, where Authorization is a key, 
and you should pass a JVT token, which you will receive after registration (using method [POST](http://localhost:8080/university/swagger-ui/index.html#/student-controller/saveUsingPOST_1)) and [logging in](http://localhost:8080/university/swagger-ui/index.html#/authentication-controller/authenticateUsingPOST).

## <a name="author"></a>Author

Natan Chachko: https://github.com/Natanius18

