<?php
include 'db.php';
$station=$_REQUEST["station"];
$result=mysqli_query($con,"SELECT short_name FROM station_info WHERE plant_slug='$station'");
$arr=mysqli_fetch_all($result,MYSQLI_ASSOC);
echo json_encode($arr);
?>