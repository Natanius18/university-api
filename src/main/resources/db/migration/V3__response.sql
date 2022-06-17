CREATE TABLE response
(
    id            SERIAL PRIMARY KEY,
    student_id    INTEGER NOT NULL,
    test_id       INTEGER NOT NULL,
    number_of_try INTEGER NOT NULL,
    result        NUMERIC(5,2) NOT NULL,
    FOREIGN KEY (student_id) REFERENCES person (id),
    FOREIGN KEY (test_id) REFERENCES test (id)
);

CREATE TABLE response_to_answer
(
    response_id INTEGER NOT NULL,
    answer_id   INTEGER NOT NULL,
    FOREIGN KEY (response_id) REFERENCES response (id),
    FOREIGN KEY (answer_id) REFERENCES answer (id)
);