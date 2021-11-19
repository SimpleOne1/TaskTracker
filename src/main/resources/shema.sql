DROP TABLE IF EXISTS tasks;
DROP SEQUENCE IF EXISTS global_seq;
CREATE SEQUENCE global_seq START WITH 1;

CREATE TABLE tasks
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    text VARCHAR NOT NULL
);
