<?php
	define('DB_SERVER', 'pick-pack-go.ckjoynyz9lxz.us-east-1.rds.amazonaws.com');
   	define('DB_USERNAME', 'admin');
   	define('DB_PASSWORD', 'emcorpstudioadmin');
   	define('DB_DATABASE', 'pick-pack-go');
   	$db = mysqli_connect(DB_SERVER,DB_USERNAME,DB_PASSWORD,DB_DATABASE);

 	// define('DB_SERVER', 'localhost');
  //  	define('DB_USERNAME', 'root');
  //  	define('DB_PASSWORD', '');
  //  	define('DB_DATABASE', 'pick-pack-go');
  //  	$db = mysqli_connect(DB_SERVER,DB_USERNAME,DB_PASSWORD,DB_DATABASE);
?>