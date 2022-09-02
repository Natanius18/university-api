Feature: Testing a REST API
  Users should be able to execute all CRUD operations with students.

  Scenario: Adding of a new student
    When new student is added
    Then the student is present in the database

  Scenario: Retrieving an existing student
    Given there is a student in the database
    When trying to retrieve an existing student
    Then getting an existing student


  Scenario: Updating an existing student
    Given there is a student in the database
    When existing student is updated
    Then updated student is saved in the database

  Scenario: Updating not existing student
    Given there is no student in the database with given id
    When not existing student is updated
    Then UserNotFoundException is thrown