<?php
  $insertFile = fopen("insert.sql", "w");
  //$primaryPer = array();
  //array_push($primaryPer, 0);

  $anzahl = 1000;

  //person
  $pname = array('Ernhofer','Adler','Karic','Kopec', 'Stedronsky', 'Kreutzer','Lehner','Zainzinger','Schwarz','Lupinek','Anil','Perny','Mustermann','Fischer','Meier','Svatunek','Haiderer','Pichler', 'Captain Hook', 'Captain Jack', 'Flotte Lotte');
  //geburtsdatum?

  $bname = array('Abracadabra', 'Affähre', 'Ali Baba', 'Alligator', 'Anaconda', 'Anna Nass', 'AquaDuck', 'Aquaholic', 'Ausreißer', 'Airwave', 'Chili Lilly', 'Die faule Paula', 'Butterfly', 'Chouchou', 'Exotica');
  // personen zufallszahlen
  // tiefgang zufallszahlen

  $bklasse = array('Finn-Dinghy', 'Laser', 'Laser Radial', '49er', '49erFX', '470er', 'Nacra 17', 'RS:X', 'Sonar', ' SKUD18', '2.4mR');

  //saegelflaeche

  //Trouble mname is PK...
  $mname = array('Wilde Affen', 'Here for Beer', 'Die Hopfentropfen', 'Hello Titty', 'Die Durstigen', 'The Chicks', 'Die Gipfelstürmer', 'Die taffen Giraffen', 'Der Name zum Sieg', 'Namenlose Qualität');
  $aklasse = array('Junior 1', 'Junior 2', 'Junior 3', 'Profi 1', 'Profi 2', 'Ultimate', 'Rookie');

  //regatta
  $rname = array('Bodenseeregatta', 'Nordseewoche', 'Kieler Woche', 'Bundesliga', 'Pantaenius Rund Skagen Race', 'Rund um den Bodensee', 'Rheinwoche', 'Swan Baltic Challenge', 'Berliner Yardstick CUP', '20 Stunden Wettfahrt', 'Ostsee Cup', 'Baltic Match Race', 'Rolex International Regatta', 'Rolex Swan American Regatta', 'Onion Patch', 'Antigua Sailing Week', '24 Uurs Zeilrace');
  //rjahr
  $rland = array('Belgien', 'Bulgarien', 'Deutschland', 'Estland', 'Finnland', 'Frankreich', 'Griechenland', 'Irland', 'Italien', 'Kroatien', 'Lettland', 'Litauen', 'Luxemburg', 'Malta', 'Niederlande', 'Polen', 'Portugal', 'Rumänien', 'Schweden', 'Slowakei', 'Slowenien', 'Spanien', 'Ungarn');
  $anzregatta = $anzahl/100;

  $aufteilung = array();

  generate($pname,$rname,$rland);

  function datum($startdatum, $enddatum){
    return date("Y-m-d", mt_rand(strtotime($startdatum), strtotime($enddatum)));
  }

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

  function randomPerson($pname){
    //INSERT INTO person (name, geburtsdatum) VALUES (name, geburtsdatum);
    fwrite($GLOBALS['insertFile'], "-- INSERTs for Person --\n");
    for($i=1;$i <= $GLOBALS['anzahl'];++$i)
		{
			//array_push($GLOBALS['primaryPer'], $i);
      $pnametmp = $pname[rand(0, count($pname)-1)];
      $gebdatumtmp = datum("1960-01-01","2001-12-31");

			fwrite($GLOBALS['insertFile'], "INSERT INTO person (name, geburtsdatum) VALUES ('$pnametmp', '$gebdatumtmp');\n");
		}
  }

  function randomTrainer(){
    //INSERT INTO trainer (key) VALUES (key);
    fwrite($GLOBALS['insertFile'], "-- INSERTs for Trainer --\n");
    for($i=1;$i<=$GLOBALS['aufteilung'][0];$i++){
      fwrite($GLOBALS['insertFile'], "INSERT INTO trainer (key) VALUES ($i);\n");
    }
  }

  function randomSegler(){
    //INSERT INTO segler (key) VALUES (key);
    fwrite($GLOBALS['insertFile'], "-- INSERTs for Segler --\n");
    for($i=$GLOBALS['aufteilung'][0]+1;$i<=$GLOBALS['aufteilung'][1];$i++){
      fwrite($GLOBALS['insertFile'], "INSERT INTO segler (key) VALUES ($i);\n");
    }
  }

  function randomRegatta($rname,$rland){
    /*
    name  VARCHAR(50),
    jahr  SMALLINT,
    land  VARCHAR(40),
    PRIMARY KEY (name, jahr)
    */

    //INSERT INTO regatta (name,jahr,land) VALUES (name,jahr,land);
    fwrite($GLOBALS['insertFile'], "-- INSERTs for Regatta --\n");
    fwrite($GLOBALS['insertFile'], "INSERT INTO regatta (name,jahr,land) VALUES ('Bodenseeregatta',2014,'Deutschland');\n");
    $usedrname = array('Bodenseeregatta');
    $usedrjahr = array(2014);
    for($i=1;$i <= $GLOBALS['anzregatta'];++$i){
      $rnametmp = $rname[rand(0, count($rname)-1)];
      $rjahrtmp = mt_rand(1800,2014);
      $rlandtmp = $rland[rand(0, count($rland)-1)];

      $geht = true;

      //Überprüfen, ob die Regatta nicht schon vorhanden ist.
      for($j = 0; $j < count($usedrname);++$j){
        if($usedrname[$j] == $rnametmp){
          if($usedrjahr[$j] == $rjahrtmp){
            $geht = false;
            $j = count($usedrname);
          }
        }
      }

      if($geht){
        array_push($usedrname,$rnametmp);
        array_push($usedrjahr,$rjahrtmp);
        fwrite($GLOBALS['insertFile'], "INSERT INTO regatta (name,jahr,land) VALUES ('$rnametmp',$rjahrtmp,'$rlandtmp');\n");
      }
    }
  }

  function generate($pname,$rname,$rland){
    aufteilung();
    //print_r($GLOBALS['aufteilung']);
    randomPerson($pname);
    randomRegatta($rname,$rland);
    randomTrainer();
    randomSegler();
  }
?>
