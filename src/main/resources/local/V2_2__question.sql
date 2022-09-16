CREATE TABLE question
(
    id       SERIAL PRIMARY KEY,
    test_id  INTEGER      NOT NULL,
    type     INT          NOT NULL,
    text VARCHAR(255) NOT NULL,
    FOREIGN KEY (test_id) REFERENCES test (id)
);