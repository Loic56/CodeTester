<?php
    $nomsAutorises = array('Albert', 'Bertrand');
    $nomEnCours    = 'Eve';
?>
 
<?php if (in_array($nomEnCours, $nomsAutorises)) : ?>
 
    <!-- code HTML -->
    <p>Bonjour <?php echo $nomEnCours ?> !</p>
 
<?php else : ?>
 
    <!-- code HTML -->
    <p>Vous n'�tes pas un utilisateur autoris� !</p>
 
<?php endif ?>