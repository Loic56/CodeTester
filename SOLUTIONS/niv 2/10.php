 <?php 
for( $i = 0; $i <= 11; $i++ ) 
{ 
  
  if( $i && $i != 11 ) 
    echo '| '; 
  else 
    echo '####'; 

  for( $j = 1; $j <= 10; $j++ ) 
  { 
  
    if( !$i || $i == 11 ) 
      echo '####'; 
    else 
    { 
      $produit = $i * $j; 
      
      if( $produit < 10 ) 
        echo '00' . $produit . ' | '; 
      else if( $produit < 100 ) 
        echo '0' . $produit . ' | '; 
      else 
        echo $produit . ' | '; 
    } 
    
  } 

  
  echo '<br />'; 
} // fin de la première boucle