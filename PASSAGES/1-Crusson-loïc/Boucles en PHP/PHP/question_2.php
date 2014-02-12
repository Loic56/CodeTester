<?php 
$array = array( 'fruits' => array( 'pommes', 'tomates', 'abricots' ), 
                'animaux' => array( 'chats', 'chiens' ), 
                'pays' => array( 'Suisse', 'France', 'Angleterre' ) ); 

foreach( $array as $key => $value ) 
{ 
  echo $key . ': <br />'; 
  
  foreach( $value as $valeur ) 
    echo '  ' . $valeur . '<br />'; 
    
  echo '<br />'; 
} 
?> 
