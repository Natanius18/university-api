# API for universities
## Table of Contents
* [Project purpose and description](#purpose)
* [Project structure](#structure)
* [Project Status](#status)
* [For developer](#developer-start)
* [Examples](#examples)
* [Related projects](#related-projects)
* [Author](#author)

## <a name="purpose"></a>Project purpose and description
Creation of the platform where teachers can create tests and students can pass them.

![DB diagram](src/main/resources/img/DB_diagram.png?raw=true)
###### <div align="center">Diagram of the database</div>
* Each teacher has several tests.
* One test consists of many questions.
* One question has multiple options of answer.
* Each answer is labeled as correct or not (bool correct).
* Depending on the type of question (1 or 2), options will be displayed as ⬜ or 🔵
  and different strategies will be used for counting students' result.
* The responses of students after passing the test will be recorded to the Response table.

### Calculation of students' results
On the back-end the all chosen answers are being found by their ids and are being compared with the set
of correct answers for the test (for better understanding of the response format see the [examples below](#examples)).
Depending on the type of each question, two strategies are used for counting the result.
- 1st type questions: If a student's response is correct, he gets one point. If not, no points are given.
- 2nd type questions:
    - There is a scale: maximum points == amount of options of answer.
    - The student gets one point if he chooses the correct answer and doesn't choose the wrong one.
          For example, we have 4 possible options (ABCD) and 2 of them are correct (C and D).
          The student will get one point for choosing C, one for choosing D,
          one for not choosing A and one for not choosing B. We will just compare the student's answers 
          on the 2nd-type-question with the correct combination of answers to this question from table Answer.

And after all the calculations and mapping, the new entity of response is saved to the database.
And, of course, the result is being returned to the student.

### Access to the system
Without being authenticated you can register and login.
When a new user registers, his account is activated by default, and he will have the USER role.
Thus, any registered person gets access to the system, but not to all its functions.
To get teacher's or student's privileges, you need to send a request to the administrator, who can confirm your role.

### Statistics
When a student takes a test, MongoDB saves statistics: test name, attempt number, result, and date of passing.
From this data, you can see the statistics: the amount of passing each test;
the average number of attempts it takes to pass the test; average result for the test.
You can also apply different filters to see only the required information.

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
* org.junit.jupiter:junit-jupiter-api 5.9.0
* org.springframework.kafka:spring-kafka 2.9.0


## <a name="status"></a>Project Status
#### In progress.
- [X] Create DB structure
- [X] Create controllers
- [X] Write tests
- [X] Write documentation in Swagger UI
- [X] Add security configuration
- [X] Add history and statistics for responses
- [X] Implement sending emails ([notification microservice](https://github.com/Natanius18/notification))
- [X] Implement saving logs ([logs-for-university-api microservice](https://github.com/Natanius18/logs-for-university-api))
- [ ] Create a REST service to access the logs


## <a name="developer-start"></a>For developer

1. Open the project in your IDE.
2. Add Java SDK 11 or above in Project Structure.
3. Install PostgreSQL if you don't have it or use [Docker Desktop](https://docs.docker.com/).
4. Configure ZooKeeper and Kafka for exchanging the data between [different microservices](#related-projects).
5. Configure your datasource properties to create a connection:
   + Host: localhost
   + Port: 5432
   + Set your username and password (don't forget to change also properties in 
   [application.yaml](https://github.com/Natanius18/internship/blob/main/src/main/resources/application.yaml)).
   + Database: university

6. Run the project.
7. For testing this API you can use Postman or visit [Swagger UI page](http://localhost:8080/university/swagger-ui/index.html).
8. For authorization, you must add a new header, where Authorization is a key,
   and you should pass a JWT, which you will receive after registration 
   (using method [POST](http://localhost:8080/university/swagger-ui/index.html#/student-controller/saveUsingPOST_1))
   and [logging in](http://localhost:8080/university/swagger-ui/index.html#/authentication-controller/authenticateUsingPOST).

## <a name="examples"></a>Examples
Examples of JSON for creating a new test, teacher, student, response can be found [here](src/main/resources/example).

## <a name="related-projects"></a>Related projects
* [Notification](https://github.com/Natanius18/notification) — microservice for sending notifications via email. 
Currently, there are two types of notification available:
    * A confirmation letter for each new user with a secret password to activate the account;
    * Daily email for teachers with their test statistics for the previous day.
* [Logs for university api](https://github.com/Natanius18/logs-for-university-api) — microservice for saving 
and accessing users' activity logs. 


## <a name="author"></a>Author
Natan Chachko: https://github.com/Natanius18
