<?php 
$array = array( 'premier' => 'N° 1', 'deuxieme' => 'N° 2', 'troisieme' => 'N° 3'); 

foreach( $array as $key => $value ) {
  echo 'Cet élément a pour clé "' . $key . '" et pour valeur "' . $value . '"<br />'; 
}
  ?>