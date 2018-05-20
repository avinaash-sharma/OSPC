<?php
 include 'db.php';
 $plant_name=$_REQUEST["plant_name"];
  $result=mysqli_query($con,"SELECT lat_long FROM plant_info WHERE plant_name='$plant_name'");
$arr=mysqli_fetch_all($result,MYSQLI_ASSOC);
echo json_encode($arr);
?>
