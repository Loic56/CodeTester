<?php 
$array = array( 'premier' => 'N� 1', 'deuxieme' => 'N� 2', 'troisieme' => 'N� 3'); 

foreach( $array as $key => $value ) {
  echo 'Cet �l�ment a pour cl� "' . $key . '" et pour valeur "' . $value . '"<br />'; 
}
  ?>