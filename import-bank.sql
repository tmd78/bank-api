-- import the bank database

CREATE DATABASE IF NOT EXISTS bank DEFAULT CHARSET = utf8;
USE bank;

DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS account;

CREATE TABLE account (
    id INT AUTO_INCREMENT,
    balance INT UNSIGNED NOT NULL,
    passcode CHAR(4) NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;
CREATE TABLE transaction (
    id INT AUTO_INCREMENT,
    account_id INT,
    action ENUM('DEPOSIT', 'WITHDRAW') NOT NULL,
    amount INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id)
        REFERENCES account (id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

INSERT INTO account (id, balance, passcode) VALUES (1, 100, '1234');
INSERT INTO transaction (account_id, action, amount) VALUES (1, 'DEPOSIT', 100);
