CREATE TABLE IF NOT EXISTS ROLE
(
    id BIGINT IDENTITY,
    name VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS USER
(
    id BIGINT IDENTITY,
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    birthday DATE NOT NULL,
    role_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
    );

INSERT INTO ROLE(NAME)VALUES('admin'),('user');
INSERT INTO USER(LOGIN, PASSWORD, EMAIL, FIRSTNAME, LASTNAME, BIRTHDAY, ROLE_ID)
VALUES ('admin', '$2a$10$ktWjIN34tx.qW60s3w2R/OMcMoI1xcR4gSV/IYc4CAS0Sgo1oZSoW', 'admin@gmailcom', 'Admin', 'Johnson', DATE '1973-12-22', 1),
       ('user', '$2a$10$rvdp0hw9OXTPr3c5OjTOrerR53z5vgWxAY/puqAlra/tLhUnzjxQi', 'user@gmailcom', 'pikachu', 'Ishimoda', DATE '1991-05-03', 2);
