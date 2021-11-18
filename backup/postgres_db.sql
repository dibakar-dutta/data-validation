

DROP DATABASE IF EXISTS validation_target;
CREATE DATABASE validation_target;
\c validation_target;

\echo 'LOADING database'


CREATE TABLE employees_target (
    id         INT,
    name       VARCHAR(255),
    email      VARCHAR(255),
    role       VARCHAR(255)
);

--CREATE TABLE employees_target (
--    id         INT             NOT NULL,
--    name       VARCHAR(255),
--    email      VARCHAR(255),
--    role       VARCHAR(255),
--    PRIMARY KEY (id)
--);


INSERT INTO employees_target (id, name, email, role)
VALUES (1, 'Dibakar Dutta', 'dibakard@emids.com', 'engineer'),
       (2, 'Manikandan Elumalai', 'manikandane@emids.com', 'architect');