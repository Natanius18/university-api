ALTER TABLE person
    ADD COLUMN email    VARCHAR(255) UNIQUE NOT NULL ;
ALTER TABLE person
    ADD COLUMN password VARCHAR(255) NOT NULL;
ALTER TABLE person
    ADD COLUMN status   VARCHAR(20) NOT NULL;

UPDATE person
SET email    = 'email',
    password = 'password',
    status   = 'ACTIVE';

ALTER TABLE person
    ALTER COLUMN email SET NOT NULL;
ALTER TABLE person
    ALTER COLUMN password SET NOT NULL;
ALTER TABLE person
    ALTER COLUMN status SET NOT NULL;