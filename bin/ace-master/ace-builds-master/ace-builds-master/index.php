<!DOCTYPE html>

<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
  <title>Testeur de code - PHP</title>
  <style type="text/css" media="screen">
 body {
		background-color:black;
    }
    #header{
		background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(149,149,149,1)), color-stop(46%,rgba(13,13,13,1)), color-stop(50%,rgba(1,1,1,1)), color-stop(53%,rgba(10,10,10,1)), color-stop(76%,rgba(78,78,78,1)), color-stop(87%,rgba(56,56,56,1)), color-stop(100%,rgba(27,27,27,1))); /* Chrome,Safari4+ */
		background: -webkit-linear-gradient(top, rgba(149,149,149,1) 0%,rgba(13,13,13,1) 46%,rgba(1,1,1,1) 50%,rgba(10,10,10,1) 53%,rgba(78,78,78,1) 76%,rgba(56,56,56,1) 87%,rgba(27,27,27,1) 100%); /* Chrome10+,Safari5.1+ */
		filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#959595', endColorstr='#1b1b1b',GradientType=0 ); /* IE6-9 */
		height:100px;
		width:100%;
		margin-left:auto;
		margin-right:auto;
		text-align:center;
	/*	box-shadow: 0px 0px 20px #f07a50;  */
		border-radius: 10px 10px 0 0px;
		color:#6EA2DE;
	}
	#container{
		margin-top : 0px;
		margin:0px;
		width:100%;
		height:500px;
		padding:5px;
		/* IE10 Consumer Preview */ 
		background-image: -ms-radial-gradient(center, ellipse farthest-corner, #FFFFFF 0%, #000000 100%);
		/* Mozilla Firefox */ 
		background-image: -moz-radial-gradient(center, ellipse farthest-corner, #FFFFFF 0%, #000000 100%);
		/* Opera */ 
		background-image: -o-radial-gradient(center, ellipse farthest-corner, #FFFFFF 0%, #000000 100%);
		/* Webkit (Safari/Chrome 10) */ 
		background-image: -webkit-gradient(radial, center center, 0, center center, 506, color-stop(0, #FFFFFF), color-stop(1, #000000));
		/* Webkit (Chrome 11+) */ 
		background-image: -webkit-radial-gradient(center, ellipse farthest-corner, #FFFFFF 0%, #000000 100%);
		/* W3C Markup, IE10 Release Preview */ 
		background-image: radial-gradient(ellipse farthest-corner at center, #FFFFFF 0%, #000000 100%);
		}
		
    #container_inscription{
		background-color: #425f78;
		border:1px solid silver;
		box-shadow: 0px 0px 15px #6EA2DE;
		margin-left:auto;
		margin-right:auto;
		margin-top:50px;
		padding:20px;
		width : 500px;
		background-image: -ms-linear-gradient(top, #FFFFFF 0%, #e6e0e0 100%);
		/* Mozilla Firefox */ 
		background-image: -moz-linear-gradient(top, #FFFFFF 0%, #e6e0e0 100%);
		/* Opera */ 
		background-image: -o-linear-gradient(top, #FFFFFF 0%, #e6e0e0 100%);
		/* Webkit (Safari/Chrome 10) */ 
		background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0, #FFFFFF), color-stop(1, #e6e0e0));
		/* Webkit (Chrome 11+) */ 
		background-image: -webkit-linear-gradient(top, #FFFFFF 0%, #e6e0e0 100%);
		/* W3C Markup, IE10 Release Preview */ 
		background-image: linear-gradient(to bottom, #FFFFFF 0%, #e6e0e0 100%);
		border:3px solid grey;
	}
	
	#btn_inscription{
		width:150px;
		height:40px;
		background-color:grey;
		margin-left:980px;
		margin-right:auto;
		margin-top:20px;
		border-radius:10px;
		color:white;
		font-size:15px;
	}
	.hover {
	}
	.color_typo{
		color:grey;
		height:30px;
		width:200px;
	}
  </style> 
</head>
<body>
	<div id="header">
		<h1 style="font-size:55px;margin-top:0px;"> Inscription  </h1>
	</div>
	<div id="header" style="height:50px;border-radius: 0px 0px 0 0px;">
	</div>
	<div id="container">
		<form action="editor.php" method="post">
			<fieldset id="container_inscription">
			<legend style="color:#6EA2DE;font-size:20px;"> Inscription </legend>
				<table>
					<tr>
						<td class="color_typo">Civilit&eacute; : </td>
						<td>
						<select name="la_civilite">
						<option>M.</option>
						<option>Mme.</option>
						<option>autre </option>
						</select>
						</td>
					</tr>
					<tr>
						<td class="color_typo">Nom : </td>
						<td><input type="text" name="le_nom"></td>
					</tr>
					<tr>
						<td class="color_typo" >Pr&eacute;nom :</td>
						<td><input type="text" name="le_prenom"></td>
					</tr>
					<tr>
						<td class="color_typo"> Mail : </td>
						<td><input type="text" name="le_mail"></td>
					</tr>
					<tr>
						<td class="color_typo">Date de naissance :</td>
						<td><input type="text" name="la_date_naissance"></td>
					</tr>
				</table>
			</fieldset>
			<input type="submit" id="btn_inscription" name="btn_inscription" value="Valider L'inscription">	
		</form>
	</div>
		<div id="header" style="height:100px;border-radius: 0px 0px 0 0px;">
	</div>
</body>