<?php
 include 'db.php';
 $state=$_REQUEST["state"];
 $result=mysqli_query($con,"SELECT DISTINCT(category) FROM plant_info WHERE state='$state'");
$arr=mysqli_fetch_all($result,MYSQLI_ASSOC);
echo json_encode($arr);
?>
