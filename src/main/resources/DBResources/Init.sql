CREATE DATABASE atm_db;
USE atm_db;
DROP TABLE IF EXISTS accounts;
CREATE TABLE accounts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_number INT NOT NULL UNIQUE,
    pin VARCHAR(10) NOT NULL,
    balance DOUBLE DEFAULT 0
);

INSERT INTO accounts (account_number, pin, balance)
VALUES (123456, '1234', 1500.0),
       (987654, '9999', 500.0);

