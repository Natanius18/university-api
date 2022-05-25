CREATE TABLE student
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    course     SMALLINT     NOT NULL
);

INSERT INTO student (first_name, last_name, course)
values ('Natan', 'Chachko', 2),
       ('Vasya', 'Pupkin', 1);