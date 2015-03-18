<?php

  //Die Datei, in die die SQL-Befehle geschrieben werden sollen
  $insertFile = fopen("insert.sql", "w");

  //Variablen für die Anzahlen
  $anzahl = 10000;
  $trainerminmax = array(35,45);  //minimim muss sich überschneiden
  $seglerminmax = array(70,90);   //minimum muss sich überschneiden
  $bootminmax = array(25,40);
  $sbootminmax = array(40,50);
  $anzmannschaft = round($anzahl/100);
  $anzregatta = round($anzahl/300);
  $bildetminmax = array(70,90);
  $zugewiesenminmax = array(70,90);
  $anznimmtteil = round($anzmannschaft*$anzregatta);

  //Array für die Namen von Personen
  $pname = array('Ernhofer','Adler','Karic','Kopec', 'Stedronsky', 'Kreutzer','Lehner','Zainzinger','Schwarz','Lupinek','Anil','Perny','Mustermann','Fischer','Meier','Svatunek','Haiderer','Pichler', 'Captain Hook', 'Captain Jack', 'Flotte Lotte');

  //Die Anzahl von Einträgen in den jeweiligen Tabellen
  $anztrainer = 0;
  $anzsegler = 0;
  $anzboot = 0;
  $anzsboot = 0;

  //Namensliste fuer die Boote
  $bname = array('Abracadabra', 'Affähre', 'Ali Baba', 'Alligator', 'Anaconda', 'Anna Nass', 'AquaDuck', 'Aquaholic', 'Ausreißer', 'Airwave', 'Chili Lilly', 'Die faule Paula', 'Butterfly', 'Chouchou', 'Exotica');

  //Liste fuer die Bootsklassen
  $bklasse = array('Finn-Dinghy', 'Laser', 'Laser Radial', '49er', '49erFX', '470er', 'Nacra 17', 'RS:X', 'Sonar', ' SKUD18', '2.4mR');

  //Namensliste fuer die Mannschaften
  $mname = array('Wilde Affen', 'Here for Beer', 'Die Hopfentropfen', 'Hello Titty', 'Die Durstigen', 'The Chicks', 'Die Gipfelstürmer', 'Die taffen Giraffen', 'Der Name zum Sieg', 'Namenlose Qualität');

  //Liste fuer die Altersklassen
  $aklasse = array('Kinder', 'Junioren A', 'Junioren B', 'Senioren A', 'Senioren B', 'Masters');

  //Namesliste fuer die Regatten
  $rname = array('Bodenseeregatta', 'Nordseewoche', 'Kieler Woche', 'Bundesliga', 'Pantaenius Rund Skagen Race', 'Rund um den Bodensee', 'Rheinwoche', 'Swan Baltic Challenge', 'Berliner Yardstick CUP', '20 Stunden Wettfahrt', 'Ostsee Cup', 'Baltic Match Race', 'Rolex International Regatta', 'Rolex Swan American Regatta', 'Onion Patch', 'Antigua Sailing Week', '24 Uurs Zeilrace');

  //Liste der Laender fuer die Regatten
  $rland = array('Belgien', 'Bulgarien', 'Deutschland', 'Estland', 'Finnland', 'Frankreich', 'Griechenland', 'Irland', 'Italien', 'Kroatien', 'Lettland', 'Litauen', 'Luxemburg', 'Malta', 'Niederlande', 'Polen', 'Portugal', 'Rumänien', 'Schweden', 'Slowakei', 'Slowenien', 'Spanien', 'Ungarn');

  //Variablen fuer die verwendeten Werte
  $usedmname = array();
  $usedmtrainer = array();
  $usedrname = array();
  $usedrjahr = array();
  $usedwdatum = array();
  $mnimmtteil = array();
  $aufteilung = array();

  //Ausfuehren der "Hauptfunktion"
  generate($pname,$bname,$rname,$bklasse,$mname,$aklasse,$rland);

  //Eine Funktion die ein zufälliges Datum zwischen einem bestimmten Bereich zurückgibt
  function datum($startdatum, $enddatum){
    return date("Y-m-d", mt_rand(strtotime($startdatum), strtotime($enddatum)));
  }

  //Alt
  function aufteilung(){
    //Zuffalszahlen
    do{
    $z4;
    $z3;
    for($z4 = rand(9,50);$z4%10 != 0;){
      $z4 = rand(9,50);
    }
    for($z3 = rand(9,90-$z4);$z3%10 != 0;){
      $z3 = rand(9,90-$z4);
    }
    $z2 = 100 - ($z4 + $z3);

    //Prozent
    $z4 /= 100;
    $z3 /= 100;
    $z2 /= 100;

    //------------Fixes setzen der Zuffalszahlen---------------
    $z4 = 0.4;
    $z3 = 0.2;
    $z2 = 0.4;


    //echo ($z4." ".$z3." ".$z2);

    $x = $GLOBALS['anzahl']/($z4*4+$z3*3+$z2*2+1);

    //echo($x."\n");

    }while($x%1!=0);

    $segler = $GLOBALS['anzahl']-$x;

    $anzPersMit4er = $z4*4*$x;
    $anzPersMit3er = $z3*3*$x;
    $anzPersMit2er = $z2*2*$x;

    //$aufteilungtmp = array($x,$segler,$anzPersMit4er,$anzPersMit3er,$anzPersMit2er);
    array_push($GLOBALS['aufteilung'],$x);
    array_push($GLOBALS['aufteilung'],$segler);
    array_push($GLOBALS['aufteilung'],$anzPersMit2er);
    array_push($GLOBALS['aufteilung'],$anzPersMit3er);
    array_push($GLOBALS['aufteilung'],$anzPersMit4er);
  }

  //============================
  //Generiert inserts für Person
  //============================
  function generatePerson($pname){
    //INSERT INTO person (name, geburtsdatum) VALUES (name, geburtsdatum);
    fwrite($GLOBALS['insertFile'], "-- INSERTs for Person --\n");
    for($i=1;$i <= $GLOBALS['anzahl'];++$i){
      $pnametmp = $pname[rand(0, count($pname)-1)]; //Wählt einen zufälligen Namen aus der Liste
      $gebdatumtmp = datum("1960-01-01","2001-12-31"); //Berechnet ein zufälliges Datum

      //Schreibt den SQL-Befehl in die Datei
			fwrite($GLOBALS['insertFile'], "INSERT INTO person (name, geburtsdatum) VALUES ('$pnametmp', '$gebdatumtmp');\n");
		}
  }

  //Alt
  function generateTrainerAlt(){
    //INSERT INTO trainer (key) VALUES (key);
    fwrite($GLOBALS['insertFile'], "\n-- INSERTs for Trainer --\n");
    for($i=1;$i<=$GLOBALS['aufteilung'][0];$i++){
      fwrite($GLOBALS['insertFile'], "INSERT INTO trainer (key) VALUES ($i);\n");
    }
  }

  //=============================
  //Generiert inserts für Trainer
  //=============================
  function generateTrainer(){
    //INSERT INTO trainer (key) VALUES (key);
    fwrite($GLOBALS['insertFile'], "\n-- INSERTs for Trainer --\n");

    //Berechnen der Anzahl der Trainer
    do{
      $anztrainer  = $GLOBALS['anzahl']*rand($GLOBALS['trainerminmax'][0], $GLOBALS['trainerminmax'][1])/100;
    }while(!is_int($anztrainer)); //Wird erneut berechnet  wenn es keine ganze Zahl ist

    //Schreiben der SQL-Befehle in die Datei
    for($i=1;$i<=$anztrainer;$i++){
      fwrite($GLOBALS['insertFile'], "INSERT INTO trainer (key) VALUES ($i);\n");
      ++$GLOBALS['anztrainer'];
    }
  }

  //Alt
  function generateSeglerAlt(){
    //INSERT INTO segler (key) VALUES (key);
    fwrite($GLOBALS['insertFile'], "\n-- INSERTs for Segler --\n");
    for($i=$GLOBALS['aufteilung'][0]+1;$i<=$GLOBALS['aufteilung'][1];$i++){
      fwrite($GLOBALS['insertFile'], "INSERT INTO segler (key) VALUES ($i);\n");
    }
  }

  //============================
  //Generiert inserts für Segler
  //============================
  function generateSegler(){
    //INSERT INTO segler (key) VALUES (key);
    fwrite($GLOBALS['insertFile'], "\n-- INSERTs for Segler --\n");

    //Berechnen der Anzahl der Segler
    do{
      $anzsegler  = $GLOBALS['anzahl']*rand($GLOBALS['seglerminmax'][0], $GLOBALS['seglerminmax'][1])/100; //Berechnen der Anzahl der Segler
    }while(!is_int($anzsegler));//Erneut, wenn es keine ganze Zahl ist

    //Schreiben der SQL-Befehle in die Datei
    for($i=$GLOBALS['anzahl'];$i>$GLOBALS['anzahl']-$anzsegler;$i--){
      fwrite($GLOBALS['insertFile'], "INSERT INTO segler (key) VALUES ($i);\n");
      ++$GLOBALS['anzsegler'];
    }
  }

  //==========================
  //Generiert inserts für Boot
  //==========================
  function generateBoot($bname){
    /*
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(50),
    personen  INTEGER,
    tiefgang  INTEGER
    */
    //INSERT INTO boot (name,personen,tiefgang) VALUES(bname,personen,tiefgang);
    fwrite($GLOBALS['insertFile'], "\n-- INSERTs for Boot --\n");

    //Berechnen der Anzahl der Boote
    do{
      $anzboot  = $GLOBALS['anzahl']*rand($GLOBALS['bootminmax'][0], $GLOBALS['bootminmax'][1])/100;
    }while(!is_int($anzboot));//Wenn die Zahl nicht Ganzzahlig ist wird es erneut berechnet

    //Erzeugen der Daten für den insert
    for($i=1;$i <= $anzboot;++$i){
      $bnametmp = $bname[rand(0, count($bname)-1)]; //Wählt einen Namen aus der Liste
      $personen = rand(4,10);
      $tiefgang = rand(50,500);

      //Einfügen des SQL-Befehls in die Datei
			fwrite($GLOBALS['insertFile'], "INSERT INTO boot (name,personen,tiefgang) VALUES('$bnametmp',$personen,$tiefgang);\n");
      ++$GLOBALS['anzboot'];
		}
  }

  //================================
  //Generiert inserts für Tourenboot
  //================================
  function generateTourenboot($bklasse){
    //INSERT INTO tourenboot (id,bootsklasse) VALUES (id, bklasse);
    fwrite($GLOBALS['insertFile'], "\n-- INSERTs for Tourenboot --\n");
    for($i=$GLOBALS['anzboot'];$i > $GLOBALS['anzsboot'];--$i){
      $bklassetmp = $bklasse[rand(0, count($bklasse)-1)]; //Wählen einer Bootklasse aus der Liste

      //Einfügen des SQL-Befehls in die Datei
      fwrite($GLOBALS['insertFile'], "INSERT INTO tourenboot (id,bootsklasse) VALUES ($i, '$bklassetmp');\n");
    }
  }

  //===============================
  //Generiert inserts für Sportboot
  //===============================
  function generateSportboot(){
    //INSERT INTO sportboot (id,segelflaeche) VALUES (id, segelflaeche);
    fwrite($GLOBALS['insertFile'], "\n-- INSERTs for Sportboot --\n");

    //Berechnen der Anzahl der Sportboote
    do{
      $anzsboot  = $GLOBALS['anzboot']*rand($GLOBALS['sbootminmax'][0], $GLOBALS['sbootminmax'][1])/100;
    }while(!is_int($anzsboot));//Wenn es keine ganze zahl ist wird es erneut berechnet

    for($i=1;$i <= $anzsboot;++$i){
      //Zufällige Größe für die Segelflaeche
      $segelflaechetmp= rand(5,25);

      //Schreiben des SQL-Befehls in die Datei
      fwrite($GLOBALS['insertFile'], "INSERT INTO sportboot (id,segelflaeche) VALUES ($i, '$segelflaechetmp');\n");
      ++$GLOBALS['anzsboot'];
    }
  }

  //================================
  //Generiert inserts für Mannschaft
  //================================
  function generateMannschaft($mname,$aklasse){
    /*
    name    VARCHAR(50) PRIMARY KEY,
    aklasse VARCHAR(15),
    key     INTEGER REFERENCES trainer
    */
    //INSERT INTO mannschaft (name, aklasse,key) VALUES (name,aklasse,key);

    fwrite($GLOBALS['insertFile'], "\n-- INSERTs for Mannschaft --\n");
    $zaehler=1;
    for($i=1;$i<=$GLOBALS['anzmannschaft'];++$zaehler){
      foreach($mname as $mnametmp){
        $mnametmp = $mnametmp." ".$zaehler;

        //Wählen eines trainers
        $trainertmp = rand(round($GLOBALS['anztrainer']*0.2),round($GLOBALS['anztrainer']*0.4));
        if($i<=$GLOBALS['anzmannschaft']){
          $aklassetmp = $aklasse[rand(0,count($aklasse)-1)]; //Waehlen einer alltersklasse aus der Liste

          //Schreiben des SQL-Befehls in die Datei
          fwrite($GLOBALS['insertFile'], "INSERT INTO mannschaft (name, aklasse,key) VALUES ('$mnametmp','$aklassetmp',$trainertmp);\n");
          array_push($GLOBALS['usedmname'],$mnametmp);//Speichern der Namen
          ++$i;
        }
      }
      $GLOBALS['usedmtrainer']=$i-1; //Speichern der verwendeten trainers
    }
  }

  //=============================
  //Generiert inserts für Regatta
  //=============================
  function generateRegatta($rname,$rland){
    /*
    name  VARCHAR(50),
    jahr  SMALLINT,
    land  VARCHAR(40),
    PRIMARY KEY (name, jahr)
    */

    //INSERT INTO regatta (name,jahr,land) VALUES (name,jahr,land);
    fwrite($GLOBALS['insertFile'], "\n-- INSERTs for Regatta --\n");
    //Einfügen einer Regatta am Bodensee 2014
    fwrite($GLOBALS['insertFile'], "INSERT INTO regatta (name,jahr,land) VALUES ('Bodenseeregatta',2014,'Oesterreich');\n");
    array_push($GLOBALS['usedrname'],'Bodenseeregatta');
    array_push($GLOBALS['usedrjahr'],2014);
    for($i=1;$i <= $GLOBALS['anzregatta'];++$i){
      $rnametmp = $rname[rand(0, count($rname)-1)];//Waehlen eines namens aus der Liste
      $rjahrtmp = mt_rand(1950,2014); //Zufaelliges Jahr
      $rlandtmp = $rland[rand(0, count($rland)-1)];//Waehlen eines landes aus der Liste
      $geht = true;

      //Überprüfen, ob die Regatta nicht schon vorhanden ist.
      for($j = 0; $j < count($GLOBALS['usedrname']);++$j){
        if($GLOBALS['usedrname'][$j] == $rnametmp){
          if($GLOBALS['usedrjahr'][$j] == $rjahrtmp){
            $geht = false;
            $j = count($GLOBALS['usedrname']);
            $i--;
          }
        }
      }

      if($geht){
        //Speichern der verwnedenten Werte
        array_push($GLOBALS['usedrname'],$rnametmp);
        array_push($GLOBALS['usedrjahr'],$rjahrtmp);

        //Schreiben des SQL-Befehls in die Datei
        fwrite($GLOBALS['insertFile'], "INSERT INTO regatta (name,jahr,land) VALUES ('$rnametmp',$rjahrtmp,'$rlandtmp');\n");
      }
    }
  }

  //===============================
  //Generiert inserts für Wettfahrt
  //===============================
  function generateWettfahrt(){
    /*
    name    VARCHAR(50),
    jahr    SMALLINT,
    datum   DATE,
    laenge  INTEGER,
    PRIMARY KEY (name, jahr, datum),
    FOREIGN KEY (name,jahr) REFERENCES regatta
    */
    //INSERT INTO wettfahrt (name,jahr,datum,laenge) VALUES (wname,wjahr,wdatum,wlaenge);

    fwrite($GLOBALS['insertFile'], "\n-- INSERTs for Wettfahrt --\n");

    for($i=0;$i< count($GLOBALS['usedrname']);$i++){

      //Waehlen einer Regatta
      $wnametmp = $GLOBALS['usedrname'][$i];
      $wjahrtmp = $GLOBALS['usedrjahr'][$i];

      $wdatum = array();
      for($j=1;$j<=rand(3,5);++$j){

        //Generieren von noch nicht verwendeten daten
        do{
          $neu = true;
          $wdatumtmp = datum("$wjahrtmp-01-01","$wjahrtmp-12-31");  //speichern damits nicht doppelt kommt!!!
          for($k=0;$k<count($wdatum);++$k){
            if($wdatumtmp==$wdatum[$k]){
              $neu = false;
              $k=count($wdatum);
            }
          }
        }while(!$neu);

        //Zufaellige laenge
        $wlaengetmp = rand(1000,10000);

        //Schreiben des SQL-Befehls in die Datei
        fwrite($GLOBALS['insertFile'], "INSERT INTO wettfahrt (name,jahr,datum,laenge) VALUES ('$wnametmp',$wjahrtmp,'$wdatumtmp','$wlaengetmp');\n");
        array_push($wdatum,$wdatumtmp); //Speichern des verwendeten Datums
      }
      array_push($GLOBALS['usedwdatum'],$wdatum); //Speichern der verwendeten Daten
    }
  }

  //============================
  //Generiert inserts für bildet
  //============================
  function generateBildet(){
    /*
    key   INTEGER,     //$anzahl-$usedmtrainer
    name  VARCHAR(50), //$usedmname
    PRIMARY KEY (key,name),
    FOREIGN KEY (key) REFERENCES segler (key) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (name) REFERENCES mannschaft
    */
    //INSERT INTO bildet (key, name) VALUES (skey,mname);

    fwrite($GLOBALS['insertFile'], "\n-- INSERTs for bildet --\n");

    //Berechnen der anzahl für bildet
    $anzbildet  = round(count($GLOBALS['usedmname'])*rand($GLOBALS['bildetminmax'][0], $GLOBALS['bildetminmax'][1])/100);

    $zaehler = $GLOBALS['anzahl'];
    for($i=0;$i<$anzbildet;++$i){

      //Waehlen eines verwendeten Mannschaftsnamens
      $mnametmp = $GLOBALS['usedmname'][$i];

      for($j=1;$j<=rand(2,4);++$j){
        //Speichern des SQL-Befehls in die Datei
        fwrite($GLOBALS['insertFile'], "INSERT INTO bildet (key, name) VALUES ($zaehler,'$mnametmp');\n");
        --$zaehler;
      }
    }
  }

  //================================
  //Generiert inserts für zugewiesen
  //================================
  function generateZugewiesen(){
    /*
    id    INTEGER,
    name  VARCHAR(50),
    FOREIGN KEY (id) REFERENCES boot
    FOREIGN KEY (name) REFERENCES mannschaft
    */
    //INSERT INTO zugewiesen (id,name) VALUES (id,name);

    fwrite($GLOBALS['insertFile'], "\n-- INSERTs for zugewiesen --\n");

    //Berechnen der Anzahl für zugewiesen
    $anzzugewiesen = round($GLOBALS['anzboot']*rand($GLOBALS['zugewiesenminmax'][0], $GLOBALS['zugewiesenminmax'][1])/100);

    //Variablen fuer die verwendeten Werte
    $usedmname = array();
    $usedid = array();

    for($i=1;$i <= $anzzugewiesen;++$i){
      $mnametmp = $GLOBALS['usedmname'][rand(0,count($GLOBALS['usedmname'])-1)]; //Waehlen eines verwendeten Mannschaftsnamen
      $id = rand(1,$GLOBALS['anzboot']); //Waehlen einesBootes

      $geht = true;

      //Überprüfen, ob die Mannschaft diesem Boot nicht schon zugewiesen ist
      for($j = 0; $j < count($usedmname);++$j){
        if($usedmname[$j] == $mnametmp){
          if($usedid[$j] == $id){
            $geht = false;
            $j = count($usedmname);
            --$i;
          }
        }
      }

      if($geht){
        //Speichern er verwendeten Werte
        array_push($usedmname,$mnametmp);
        array_push($usedid,$id);
        //Schreiben des SQL-Befehls in die Datei
        fwrite($GLOBALS['insertFile'], "INSERT INTO zugewiesen (id,name) VALUES ($id,'$mnametmp');\n");
      }
    }
  }

  //================================
  //Generiert inserts für nimmt_teil
  //================================
  function generateNimmtteil(){
    /*
    rname     VARCHAR(50),
    mname     VARCHAR(50),
    rjahr     SMALLINT,
    sportboot INTEGER,
    startnr   SMALLINT,
    */
    //INSERT INTO nimmt_teil (mname,rname,rjahr,sportboot,startnr) VALUES (mname,rname,rjahr,sportboot,startnr);

    fwrite($GLOBALS['insertFile'], "\n-- INSERTs for nimmt_teil --\n");

    //Variablen fuer die verwendeten Werte
    $usedmname = array();
    $usedrname = array();
    $usedrjahr = array();
    $usedsboot = array();

    for($i=1;$i<=$GLOBALS['anznimmtteil'];++$i){
      //Waehlen eines verwendeten Mannschaftsnamen
      $mnametmp = $GLOBALS['usedmname'][rand(0,count($GLOBALS['usedmname'])-1)];

      //Waehlen eine Regatta
      $x=rand(0,count($GLOBALS['usedrname'])-1);
      $rnametmp = $GLOBALS['usedrname'][$x];
      $rjahrtmp = $GLOBALS['usedrjahr'][$x];

      //Waehlen eines Bootes
      $sboottmp = rand(1,$GLOBALS['anzsboot']);

      $startnrtmp = 1;

      $geht = true;

      //Überprüfen, ob Kombination von Mannschaft, Regatta und Boot nicht schon vorhanden ist
      //und erhoehen der Startnummer, wenn schon boote bei dieser Regatta teilnehmen.
      for($j = 0; $j < count($usedmname);++$j){
        if($usedrname[$j] == $rnametmp){
          if($usedrjahr[$j] == $rjahrtmp){
            ++$startnrtmp; //Erhöhen der Startnummer
            if($usedmname[$j] == $mnametmp){
              if($usedsboot[$j] == $sboottmp){
                $geht = false;
                $j = count($usedmname);
                $i--;
              }
            }
          }
        }
      }

      if($geht){
        //Speichern der verwendeten Werte
        array_push($usedmname,$mnametmp);
        array_push($usedrname,$rnametmp);
        array_push($usedrjahr,$rjahrtmp);
        array_push($usedsboot,$sboottmp);
        array_push($GLOBALS['mnimmtteil'],[$mnametmp,$x]);
        //Schreiben des SQL-Befehls in die Datei
        fwrite($GLOBALS['insertFile'], "INSERT INTO nimmt_teil (mname,rname,rjahr,sportboot,startnr) VALUES ('$mnametmp','$rnametmp',$rjahrtmp,$sboottmp,$startnrtmp);\n");
      }
    }
  }

  //=============================
  //Generiert inserts für erzielt
  //=============================
  function generateErzielt(){
    /*
    mname   VARCHAR(50),
    wname   VARCHAR(50),
    wjahr   SMALLINT,
    wdatum  DATE,
    punkte  INTEGER,
    */
    //INSERT INTO erzielt (mname,wname,wjahr,wdatum,punkte) VALUES (wname,mname,wjahr,wdatum,punkte);

    fwrite($GLOBALS['insertFile'], "\n-- INSERTs for erzielt --\n");
    $used = array(); //Variable fuer die verwendeten Mannschaftsnamen
    for($i=0;$i<count($GLOBALS['mnimmtteil']);++$i){

      //Ueberpruefen ob die Mannschaft nicht schon bewertet wurde
      $neu = true;
      for($k=0;$k<count($used);++$k){
        if($GLOBALS['mnimmtteil'][$i]==$used[$k]){
          $neu=false;
          $k=count($used);
        }
      }

      if($neu){
        $mnametmp = $GLOBALS['mnimmtteil'][$i][0]; //Waehlen des Mannschaftnamens
        for($j=0;$j<count($GLOBALS['usedwdatum'][$GLOBALS['mnimmtteil'][$i][1]]);++$j){
          //Waehlen der Wettfahrt
          $wnametmp = $GLOBALS['usedrname'][$GLOBALS['mnimmtteil'][$i][1]];
          $wjahrtmp = $GLOBALS['usedrjahr'][$GLOBALS['mnimmtteil'][$i][1]];
          $wdatumtmp= $GLOBALS['usedwdatum'][$GLOBALS['mnimmtteil'][$i][1]][$j];

          //Zufahlspunkte
          $punktetmp = rand(0,100);

          //Schreiben des SQL-Befehls in die Datei
          fwrite($GLOBALS['insertFile'], "INSERT INTO erzielt (mname,wname,wjahr,wdatum,punkte) VALUES ('$mnametmp','$wnametmp',$wjahrtmp,'$wdatumtmp',$punktetmp);\n");
        }
        //Speicher der verwendteten Mannschaftsnamen
        array_push($used,$GLOBALS['mnimmtteil'][$i]);
      }
    }
  }

  //Führt alle generatortfunktionen aus und uebergibt die Namenslisten
  function generate($pname,$bname,$rname,$bklasse,$mname,$aklasse,$rland){
    //aufteilung();
    generatePerson($pname);
    generateTrainer();
    generateSegler();
    generateboot($bname);
    generateSportboot();
    generateTourenboot($bklasse);
    generateMannschaft($mname,$aklasse);
    generateRegatta($rname,$rland);
    generateWettfahrt();
    generateBildet();
    generateZugewiesen();
    generateNimmtteil();
    generateErzielt();
  }
?>
