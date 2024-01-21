CREATE TABLE test
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    teacher_id INTEGER      NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES person (id)
);