CREATE TABLE response_to_answer
(
    response_id INTEGER NOT NULL,
    answer_id   INTEGER NOT NULL,
    FOREIGN KEY (response_id) REFERENCES response (id),
    FOREIGN KEY (answer_id) REFERENCES answer (id)
);