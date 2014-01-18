<?php

function editor_connect(){
	try
	{
		$connexion = new PDO('mysql:host=localhost;dbname=testeur_de_code', 'root', '');
	}
	  
	catch(Exception $e)
	{
		die();
	}
	return $connexion;
}


function create_passage($candidat_id){
	$connexion = editor_connect();
	$date = date('d/m/Y');	
	echo $date;
	$req_passage = '</br>'.'INSERT INTO passage ( TESTID, CANDIDATID, PASSAGE_DATE) VALUES ( 1, '.$candidat_id.',\''.$date.'\');';
	echo $req_passage;
	$requete = $connexion->exec($req_passage);
}


function enreg_candidat($civ, $nom, $prenom, $mail, $date){
	$connexion = editor_connect();
	$req_enreg_candidat = 'INSERT INTO candidat (CANDIDAT_CIVILITE, CANDIDAT_NOM, CANDIDAT_PRENOM, CANDIDAT_MAIL, CANDIDAT_DATE_NAISSANCE) VALUES (\''.$civ.'\',\''.$nom.'\',\''.$prenom.'\',\''.$mail.'\','.$date.');';
	$requete = $connexion->exec($req_enreg_candidat);
	
	$reponse = $connexion->query('SELECT * FROM candidat WHERE CANDIDAT_NOM LIKE \''.$nom. '\' AND CANDIDAT_PRENOM LIKE \''.$prenom. '\'');
	$donnees = $reponse->fetch();
	$candidat_id = $donnees['CANDIDATID'];
	echo '</br>'.'nom = '.$donnees['CANDIDAT_NOM'];
	echo '</br>'.'prenom = '.$donnees['CANDIDAT_PRENOM'];
	echo '</br>'.'id = '.$candidat_id ; 
	create_passage($candidat_id, $date);
}


function create_rep($nom, $prenom){
	mkdir('C:\wamp\www\ace-master\ace-builds-master\ace-builds-master\resultats_test\\'.$prenom.'_'.$nom , 0777, true);
}


// récupére tableau de questions au hsard -> juste leur ID
function getTabIdQuestion(){
	$connexion=editor_connect();
	$tab_id_question=array();

	$reponse = $connexion->query('SELECT min(QUESTIONID) as ID FROM question');
	$min = $reponse->fetch();
	$min=$min['ID'];
	
	$reponse = $connexion->query('SELECT max(QUESTIONID) as ID FROM question');
	$max = $reponse->fetch();
	$max=$max['ID'];
	
	for($i=0;$i<5;$i++){
		do{
			$tab_id_question[$i]=rand($min, $max);
			/*echo '<a style="color:white;"> VAL = '.$tab_id_question[$i].'</a>';
			echo '<br />'; // */
			}while(!in_array($tab_id_question[$i], $tab_id_question));
	}

	return $tab_id_question;
	
}


// récupère toutes les informations d'un tableau de question
function getTabQuestion(){
	$tab_question=array();
	$tab_id_question = getTabIdQuestion();
	
	$connexion=editor_connect();
	
	for ($i = 0; $i < sizeof($tab_id_question); $i++) { 
		$reponse = $connexion->query('SELECT * FROM question WHERE QUESTIONID = '.$tab_id_question[$i]);
			while ($donnees = $reponse->fetch())
			{
				$tab_question[$i]['TESTID']=$donnees['TESTID'];
				$tab_question[$i]['RUBRIQUEID']=$donnees['RUBRIQUEID'];
				$tab_question[$i]['QUESTIONID']=$donnees['QUESTIONID'];
				$tab_question[$i]['QUESTIONTEXT']=$donnees['QUESTIONTEXT'];
			}
	}  
	return $tab_question;
}
?>