--1. Geben Sie alle Mannschaften aus, die bei der Bodenseeregatta im Jahr 2014 teilgenommen haben. Wenn eine Mannschaft mit einem Boot mit der Segelfläche kleiner als 20 m2 teilgenommen hat, soll auch die ID des Bootes ausgegeben werden.

--2. Geben Sie den Namen und das Geburtsdatum der jüngsten Trainer aus (können auch mehrere sein).
SELECT name,geburtsdatum FROM person ORDER BY geburtsdatum DESC LIMIT 25;

--3. Geben Sie alle Personen geordnet nach Geburtsdatum aus, die sowohl Segler als auch Trainer sind, allerdings in keiner Mannschaft dabei sind.

--4. Geben Sie alle Personen geordnet nach Geburtsdatum aus, die entweder Segler oder Trainer sind, jedoch nicht beides und vermerken Sie in einer Spalte, ob es sich um einen Trainer oder einen Segler handelt.

--5. Geben Sie die Regatten (Name und Jahr) mit den wenigsten Wettfahrten an und geben Sie auch die Anzahl aus.

--6. Geben Sie die Namen jener Trainer aus, die zwei oder mehr Mannschaften betreuen.

--7. Welche Altersklasse ist am aktivsten (hat an den meisten Wettfahrten Punkte erzielt)?

--8. Um wieviel gehen Tourenboote durchschnittlich tiefer als Sportboote?

--9. Geben Sie für alle Mannschaften aus, an wievielen Regatten sie bereits teilgenommen haben und wieviele Punkte sie dort erzielt haben.

--10. Welches Land bietet die längste Wettfahrtsstrecke und hat zusätzlich nicht die kürzeste?

--11. Wie heißt der Trainer, der die Manschaft mit den meisten Punkten trainiert hat?

--12. Geben Sie für JEDE Mannschaft aus, wieviele Punkte Sie bei der 'Bodenseeregatta' in 'Oesterreich' erzielt haben.

--13. Geben Sie die ID und den Namen jener Sportboote aus, die mindestens an zwei Regatten Teil genommen haben, aber keiner Mannschaft zugewiesen sind.

--14. Geben Sie die Regatten (Name, Jahr und Land) aus, die über die kürzeste Distanz gehen. 
