<?php
    $nomsAutorises = array('Albert', 'Bertrand');
    $nomEnCours    = 'Eve';
?>
 
<?php if (in_array($nomEnCours, $nomsAutorises)) : ?>
 
    <!-- code HTML -->
    <p>Bonjour <?php echo $nomEnCours ?> !</p>
 
<?php else : ?>
 
    <!-- code HTML -->
    <p>Vous n'êtes pas un utilisateur autorisé !</p>
 
<?php endif ?>