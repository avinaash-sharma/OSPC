<?php
 include 'db.php';
$result=mysqli_query($con,"SELECT distinct(plant_id) FROM plant_info ");
$array=mysqli_fetch_all($result,MYSQLI_ASSOC);
echo json_encode($array);
?>
