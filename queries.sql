--1. Geben Sie alle Mannschaften aus, die bei der Bodenseeregatta im Jahr 2014 teilgenommen haben. Wenn eine Mannschaft mit einem Boot mit der Segelfläche kleiner als 20 m2 teilgenommen hat, soll auch die ID des Bootes ausgegeben werden.
SELECT mname AS mannschaft, (SELECT id FROM sportboot WHERE segelflaeche<20 AND id=sportboot) AS boot_id FROM nimmt_teil WHERE rname = 'Bodenseeregatta' AND rjahr = 2014;

--2. Geben Sie den Namen und das Geburtsdatum der jüngsten Trainer aus (können auch mehrere sein).
SELECT name,geburtsdatum FROM person ORDER BY geburtsdatum DESC LIMIT 25;

--3. Geben Sie alle Personen geordnet nach Geburtsdatum aus, die sowohl Segler als auch Trainer sind, allerdings in keiner Mannschaft dabei sind.
SELECT name,key,geburtsdatum FROM person natural join trainer natural join segler WHERE key NOT IN (SELECT key FROM mannschaft) AND key NOT IN (SELECT key FROM bildet) ORDER BY geburtsdatum ASC;

--4. Geben Sie alle Personen geordnet nach Geburtsdatum aus, die entweder Segler oder Trainer sind, jedoch nicht beides und vermerken Sie in einer Spalte, ob es sich um einen Trainer oder einen Segler handelt.
SELECT *,'segler' AS funktion FROM person NATURAL JOIN segler WHERE key NOT IN (SELECT key FROM trainer) UNION SELECT *,'trainer' AS funktion FROM person NATURAL JOIN trainer WHERE key NOT IN (SELECT key FROM segler) ORDER BY geburtsdatum ASC;

--5. Geben Sie die Regatten (Name und Jahr) mit den wenigsten Wettfahrten an und geben Sie auch die Anzahl aus.
SELECT name,jahr,COUNT(datum) AS anzahl FROM wettfahrt GROUP BY name,jahr;

--6. Geben Sie die Namen jener Trainer aus, die zwei oder mehr Mannschaften betreuen.
SELECT name,mannschaften FROM (SELECT name, (SELECT COUNT(name) FROM mannschaft WHERE key = person.key) AS mannschaften FROM person NATURAL JOIN trainer) AS trainer where mannschaften>1;

--7. Welche Altersklasse ist am aktivsten (hat an den meisten Wettfahrten Punkte erzielt)?
SELECT altersklasse FROM (SELECT aklasse AS altersklasse,sum(punkte) AS punkte FROM erzielt LEFT JOIN mannschaft ON erzielt.mname=mannschaft.name GROUP BY aklasse ORDER BY punkte DESC) AS altersklassen LIMIT 1;

--8. Um wieviel gehen Tourenboote durchschnittlich tiefer als Sportboote?
SELECT AVG(tiefgang)-(SELECT AVG(tiefgang) FROM sportboot NATURAL JOIN boot) as differenz FROM tourenboot NATURAL JOIN boot;

--9. Geben Sie für alle Mannschaften aus, an wievielen Regatten sie bereits teilgenommen haben und wieviele Punkte sie dort erzielt haben.
SELECT mname AS mannschaft,COUNT(mname) AS anzahl, sum(punkte) AS punkte FROM (SELECT mname,wname,sum(punkte) as punkte FROM erzielt GROUP BY mname,wname) AS teilnehmen GROUP BY mname;

--10. Welches Land bietet die längste Wettfahrtsstrecke und hat zusätzlich nicht die kürzeste?

--11. Wie heißt der Trainer, der die Manschaft mit den meisten Punkten trainiert hat?

--12. Geben Sie für JEDE Mannschaft aus, wieviele Punkte Sie bei der 'Bodenseeregatta' in 'Oesterreich' erzielt haben.

--14. Geben Sie die Regatten (Name, Jahr und Land) aus, die über die kürzeste Distanz gehen.
SELECT name,jahr,land,sum(laenge) AS laenge FROM wettfahrt NATURAL JOIN regatta GROUP BY name,jahr,land ORDER BY laenge ASC LIMIT 35;
