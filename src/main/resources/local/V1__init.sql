CREATE TABLE person
(
    id         SERIAL PRIMARY KEY,
    dtype      VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    course     SMALLINT,
    position   VARCHAR(255)
);