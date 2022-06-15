CREATE TABLE response
(
    id            SERIAL PRIMARY KEY,
    student_id    INTEGER NOT NULL,
    test_id       INTEGER NOT NULL,
    number_of_try INTEGER NOT NULL,
    FOREIGN KEY (student_id) REFERENCES person (id),
    FOREIGN KEY (test_id) REFERENCES test (id)
);