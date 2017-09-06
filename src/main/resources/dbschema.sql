--CREATE SCHEMA phone_book;
DROP TABLE IF EXISTS PERSON;
DROP TABLE IF EXISTS PHONE;

--PERSON TABLE

CREATE TABLE IF NOT EXISTS PERSON(
	id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) NOT NULL,
	last_name VARCHAR(255),
	first_name VARCHAR(255),
	middle_name VARCHAR(255),
	PRIMARY KEY(id)
);


--PHONE TABLE

CREATE TABLE IF NOT EXISTS PHONE(
	id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) NOT NULL,
	phone_number VARCHAR(255),
	phone_type VARCHAR(255),
	person_id BIGINT NOT NULL,
	PRIMARY KEY(id)
);