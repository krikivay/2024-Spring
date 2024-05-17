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
)