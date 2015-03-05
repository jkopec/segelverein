-- DROP DATABASE IF EXISTS segelverein;
-- CREATE DATABASE segelverein WITH OWNER = segel;
-- \c segelverein;

-- DROP TABLE IF EXISTS kategorie;
CREATE TABLE person(
  key           SERIAL PRIMARY KEY,
  name          VARCHAR(50),
  geburtsdatum  DATE
);

CREATE TABLE segler(
  key INTEGER PRIMARY KEY REFERENCES person ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE trainer(
  key INTEGER PRIMARY KEY REFERENCES person ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE boot(
  id        SERIAL PRIMARY KEY,
  name      VARCHAR(50),
  personen    INTEGER,
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
  key     INTEGER REFERENCES trainer ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE regatta(
  name  VARCHAR(50),
  jahr  SMALLINT,
  land  VARCHAR(40),
  PRIMARY KEY (name, jahr)
);

CREATE TABLE wettfahrt(
  name    VARCHAR(50),
  jahr    SMALLINT,
  datum   DATE,
  laenge  INTEGER,
  PRIMARY KEY (name, jahr, datum),
  FOREIGN KEY (name,jahr) REFERENCES regatta (name,jahr) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE bildet(
  key   INTEGER,
  name  VARCHAR(50),
  PRIMARY KEY (key,name),
  FOREIGN KEY (key) REFERENCES segler (key) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (name) REFERENCES mannschaft (name) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE zugewiesen(
  id    INTEGER,
  name  VARCHAR(50),
  PRIMARY KEY (id,name),
  FOREIGN KEY (id) REFERENCES boot (id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (name) REFERENCES mannschaft (name) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE nimmt_teil(
  mname     VARCHAR(50),
  rname     VARCHAR(50),
  rjahr     SMALLINT,
  sportboot INTEGER,
  startnr   SMALLINT,
  PRIMARY KEY (mname,rname,rjahr,sportboot),
  FOREIGN KEY (mname) REFERENCES mannschaft (name) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (rname,rjahr) REFERENCES regatta (name,jahr) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (sportboot) REFERENCES sportboot (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE erzielt(
  mname   VARCHAR(50),
  wname   VARCHAR(50),
  wjahr   SMALLINT,
  wdatum  DATE,
  punkte  INTEGER,
  PRIMARY KEY(mname,wname,wjahr,wdatum),
  FOREIGN KEY (mname) REFERENCES mannschaft (name) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (wname,wjahr,wdatum) REFERENCES wettfahrt (name,jahr,datum) ON UPDATE CASCADE ON DELETE CASCADE
);
