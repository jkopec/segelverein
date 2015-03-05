-- DROP DATABASE IF EXISTS segelverein;
-- CREATE DATABASE segelverein WITH OWNER = segel;
\c segelverein;

-- DROP TABLE IF EXISTS kategorie;
CREATE TABLE person(
      key           SERIAL,
      name          varchar(50),
      geburtsdatum  date,
      PRIMARY KEY (key)
);

/*
ALTER TABLE kategorie OWNER TO insy4;

--INSERTS
INSERT INTO kategorie VALUES ('eBook', 10);
INSERT INTO kategorie VALUES ('MP3', 20); -- Anführungszeichen!
INSERT INTO kategorie VALUES ('Blu-ray', 20);
INSERT INTO kategorie VALUES ('Elektronik', 20);

-- DROP TABLE IF EXISTS artikel;
CREATE TABLE artikel (
       aid   SERIAL,
       kbez  VARCHAR(255) REFERENCES kategorie (kbez),
       abez  VARCHAR(255),
       preis DECIMAL(5,2),
       PRIMARY KEY (aid)
);
ALTER TABLE artikel OWNER TO insy4;

--INSERTS
INSERT INTO artikel (kbez, abez, preis) VALUES ('eBook', 'Der Anschlag', '0.00');
INSERT INTO artikel (kbez, abez, preis) VALUES ('MP3', '21', '0.00');
INSERT INTO artikel (kbez, abez, preis) VALUES ('Blu-ray', 'Pulp Fiction', '0.00');
INSERT INTO artikel (kbez, abez, preis) VALUES ('Blu-ray', 'Hangover 2', '0.00');
INSERT INTO artikel (kbez, abez, preis) VALUES ('Elektronik', 'Kindle WI-FI', '0.00');

-- reassign owned by postgres to insy4

*/
