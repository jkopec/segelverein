-- DROP DATABASE IF EXISTS segelverein;
-- CREATE DATABASE segelverein WITH OWNER = segel;
\c segelverein;

-- DROP TABLE IF EXISTS kategorie;
CREATE TABLE person(
  key           INTEGER PRIMARY KEY,
  name          VARCHAR(50),
  geburtsdatum  DATE
);

CREATE TABLE segler(
  key INTEGER PRIMARY KEY REFERENCES person ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE trainer(
  key INTEGER PRIMARY KEY REFERENCES person
);

CREATE TABLE boot(
  id        INTEGER PRIMARY KEY,
  name      VARCHAR(50),
  person    INTEGER,
  tiefgang  INTEGER
);

CREATE TABLE tourenboot(
  id          INTEGER PRIMARY KEY,
  bootsklasse VARCHAR(25)
);

CREATE TABLE sportboot(
  id            INTEGER PRIMARY KEY,
  segelflaeche  INTEGER
);

CREATE TABLE mannschaft(
  name    VARCHAR(50) PRIMARY KEY,
  aklasse VARCHAR(15),
  key     INTEGER REFERENCES trainer
);

CREATE TABLE regatta(
  name  VARCHAR(50),
  jahr  SMALLINT,
  land  VARCHAR(40),
  PRIMARY KEY (name, jahr)
--  CONSTRAINT helga UNIQUE (land)
);

CREATE TABLE wettfahrt(
  name    VARCHAR(50),
  jahr    SMALLINT,
  datum   DATE,
  laenge  INTEGER,
  PRIMARY KEY (name, jahr, datum),
  FOREIGN KEY (name,jahr) REFERENCES regatta (name,jahr)
);

CREATE TABLE bildet(
  key   INTEGER,
  name  VARCHAR(50),
  PRIMARY KEY (key,name),
  FOREIGN KEY (key) REFERENCES segler (key),
  FOREIGN KEY (name) REFERENCES mannschaft (name)
);
