CREATE SEQUENCE user_id_seq START 1;

CREATE TABLE users (
                       id BIGINT PRIMARY KEY DEFAULT nextval('user_id_seq'),
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       role VARCHAR(50) NOT NULL CHECK (role IN ('ROLE_USER', 'ROLE_ADMIN'))
);