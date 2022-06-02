CREATE TABLE answer
(
    id          SERIAL PRIMARY KEY,
    question_id INTEGER      NOT NULL,
    is_correct     BOOLEAN      NOT NULL,
    option      VARCHAR(255) NOT NULL,
    FOREIGN KEY (question_id) REFERENCES question (id)
);