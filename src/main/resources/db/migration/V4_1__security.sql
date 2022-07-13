ALTER TABLE person
    ADD COLUMN role VARCHAR(20);

UPDATE person
SET role = 'STUDENT'
WHERE dtype = 'Student';

UPDATE person
SET role = 'TEACHER'
WHERE dtype = 'Teacher';

ALTER TABLE person
    ALTER COLUMN role SET NOT NULL;