<?php
  $insertFile = fopen("insert.sql", "w");
  //$primaryPer = array();
  //array_push($primaryPer, 0);

  $anzahl = 10;

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
  $rname = array('Ja', 'Nein', );
  //rjahr
  $rland = array('Belgien', 'Bulgarien', 'Deutschland', 'Estland', 'Finnland', 'Frankreich', 'Griechenland', 'Irland', 'Italien', 'Kroatien', 'Lettland', 'Litauen', 'Luxemburg', 'Malta', 'Niederlande', 'Polen', 'Portugal', 'Rumänien', 'Schweden', 'Slowakei', 'Slowenien', 'Spanien', 'Ungarn');
  $anzregatta = $anzahl/4;

  generate($pname,$rname,$rland);

  function datum($startdatum, $enddatum){
    return date("Y-m-d", mt_rand(strtotime($startdatum), strtotime($enddatum)));
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

  function randomRegatta($rname,$rland){
    /*
    name  VARCHAR(50),
    jahr  SMALLINT,
    land  VARCHAR(40),
    PRIMARY KEY (name, jahr)
    */

    //INSERT INTO regatta (name,jahr,land) VALUES (name,jahr,land);
    fwrite($GLOBALS['insertFile'], "-- INSERTs for Regatta --\n");
    for($i=1;$i <= $GLOBALS['anzregatta'];++$i){
      $rnametmp = $rname[rand(0, count($rname)-1)];
      $rjahrtmp = mt_rand(1900,2014);
      $rlandtmp = $rland[rand(0, count($rland)-1)];

      $usedrname = array();
      $usedrjahr = array();

      $geht = true;
      $first = true;

    if(!$first){
      for($i = 0; $i < count($usedrname);++$i){
        if($usedrname[$i] == $rnametmp){
          if($usedjahr[$i] == $rjahrtmp){
            $geht = false;
            $i = count($usedrname);
          }
        }
      }
    }

      $first = false;
      if($geht){
        array_push($usedrname,$rnametmp);
        array_push($usedrjahr,$rjahrtmp);
        fwrite($GLOBALS['insertFile'], "INSERT INTO regatta (name,jahr,land) VALUES ('$rnametmp',$rjahrtmp,'$rlandtmp');\n");
      }
    }
  }

  function generate($pname,$rname,$rland){
    randomPerson($pname);
    randomRegatta($rname,$rland);
  }
?>
