CREATE TABLE course
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE teacher_reads_course
(
    id         SERIAL PRIMARY KEY,
    teacher_id INTEGER NOT NUll REFERENCES teacher,
    course_id  INTEGER NOT NULL REFERENCES course
);