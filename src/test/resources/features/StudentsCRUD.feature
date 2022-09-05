Feature: Testing a REST API
  Users should be able to execute all CRUD operations with students.

  Scenario: Adding of a new student
    When new student is added
    Then the student is present in the database

  Scenario Outline: Retrieving an existing student
    Given there is a student in the database
      | firstName | lastName | email              |
      | Natan     | Chachko  | email1@example.com |
      | Test      | Student  | email2@example.com |
      | Another   | Test     | email3@example.com |
    And the user is authenticated with email "<email>" and password "<password>"
    When trying to retrieve an existing student
    Then getting an existing student
      | firstName | lastName | email              |
      | Natan     | Chachko  | email1@example.com |
      | Test      | Student  | email2@example.com |
      | Another   | Test     | email3@example.com |

    Examples:
      | email                         | password |
      | admin@admin.com               | admin    |
      | natan.chachko@stud.onu.edu.ua | 12345    |


  Scenario: Updating an existing student
    Given there is a student in the database
      | firstName | lastName | email              |
      | Natan     | Chachko  | email1@example.com |
    When existing student is updated
    Then updated student is saved in the database


  Scenario: Updating not existing student
    Given there is no student in the database with given id
    When not existing student is updated
    Then UserNotFoundException is thrown