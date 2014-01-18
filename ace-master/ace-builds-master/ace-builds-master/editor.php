<?php
session_start();
?>
<!DOCTYPE html>
<html lang=">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
  <title>Editor</title>
  <link rel="stylesheet" type="text/css" href="style.css">
</head>

<?php
include 'editor_fonctions.php'; 
$bdd = editor_connect();

if(isset($_POST['btn_inscription'])){
	 $civ=$_POST['la_civilite'];
	 $nom=$_POST['le_nom'];
	 $prenom=$_POST['le_prenom'];
	 $mail=$_POST['le_mail'];
	 $date=$_POST['la_date_naissance'];
	 enreg_candidat($civ, $nom, $prenom, $mail, $date);
	 create_rep($nom, $prenom);
     $tab_question=getTabQuestion();
	 $_SESSION['tab_quest'] = $tab_question;
	 $i=0;
	 $_SESSION['i'] = 0;
 }

 if(isset($_POST['bt_submit'])){
	  $sourcecode = trim($_POST['txt_area']);
	  $fichier = "le_code.php";
	  file_put_contents($fichier, $sourcecode);
	  $tab_question = $_SESSION['tab_quest'];
	  $i = $_SESSION['i'];
  }
  
 if(isset($_POST['bt_suivant'])){
	// enreg_reponse();
	 $tab_question = $_SESSION['tab_quest'];
	 $i = $_SESSION['i'];
	 $i++;
	 $_SESSION['i'] = $i;
 }
 
if($_SESSION['i'] === sizeof($tab_question)-1){
		//echo '<a style="color:white;"> Fin du test !!! </a>';
		header('Location: fin_de_test.php'); 
	 }
/* 	 
 echo '<a style="color:white;"> i = '.$i.'</a>';
 echo '<br />';
 echo '<a style="color:white;"> taille = '.sizeof($tab_question).'</a>'; // */
?>


<body>
	 <form id="form" name="form" action="" method="post">	
		<div id="container" class="gradient">	
			<h1 style="font-family:;margin-top: 0px;font-size:35px;font-style:italic;margin-left:65px;margin-top:0px;color:silver"> TESTEUR DE CODE </h1>
			<p style="font-size:25px;margin-left:80px;margin-top:-20px;color:silver">test PHP</p>
			<div style="width:100%;border-bottom:1px solid grey;margin-top:-20px;"></div>
			<div id="container_question">
				<div id="question">
					<?php 
					echo $tab_question[$i]['QUESTIONTEXT'];
					$filename='PHP-CodeTester\propositions\images\r'.$tab_question[$i]['RUBRIQUEID'].'_q'.$tab_question[$i]['QUESTIONID'].'.jpg';
						if (file_exists($filename)) {
							?>
							<br />
							<center><img src="<?php echo $filename ?>" style="border:1px solid black;width:320px;margin-top:20px;"></center>
							<?php
						} 
					?>
				</div>
			</div>
			
			<input type="text" name="txt_data" style="visibility:hidden;" />
			
			<div id="container_editor">
				<div id="editor" name="editor">
					<?php 
					 if(isset($_POST['bt_submit'])){	
						echo htmlspecialchars(trim(file_get_contents("le_code.php"))); 
					}
					else {
						$enonce='PHP-CodeTester\propositions\propositions\prop_r'.$tab_question[$i]['RUBRIQUEID'].'_q'.$tab_question[$i]['QUESTIONID'].'.php';
						echo htmlspecialchars(trim(file_get_contents($enonce))); 
					}
					?>
				</div> 
			</div>
		</div>	
		
		
		<div id="div_btn">
			<div id="btn">
				<input id="btn_suivant" type="submit" name="bt_suivant" value="Suivant" onClick="data_submit();">
			</div> 
			<div id="btn">
				<input id="btn_interpreter" type="submit" name="bt_submit" value="Visualiser" onClick="data_submit2();">
			</div> 
			<input type="text" name="btn" style="visibility:hidden;" />
		<div>
			

		
		<div id="container_bas">		
			<center>
				<div id="frame">
					<iframe id="iframe" src="le_code.php" width="675"> </iframe>
				</div>
			</center>
		</div>		

			<!-- invisible -->
		<div style="width:700px;height:150px;float: left;">
			<textarea id="txt_area" name="txt_area" style="width:100%;height:100%;border:1px solid #999;padding:5px;margin:5px;display:none;"> 
			</textarea>
		</div>
			
		
	</form>		
	<!-- pastel_on_dark ; solarized_dark ;  -->
	<!-- ------------------------------------------------------------------------------------------------------------------------------------- -->
	<script src="src-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>
	
	<script>
		var editor = ace.edit("editor");
		editor.setTheme("ace/theme/tomorrow_night_eighties");
		editor.getSession().setMode("ace/mode/php");
		document.getElementById('editor').style.fontSize='16px';
		editor.gotoLine(1);
	</script>

	<script type="text/javascript">
	function data_submit() {
		document.form.txt_area.value = editor.getSession().getValue();
	}
	function data_submit2() {
		document.form.txt_area.value = editor.getSession().getValue();
		document.form.btn.value = "clic";
	}
	</script>
	
</body>
</html>
