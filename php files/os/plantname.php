<?php
 include 'db.php';
 $state=$_REQUEST["state"];
 $category=$_REQUEST["category"];
 $result=mysqli_query($con,"SELECT plant_name FROM plant_info WHERE state='$state' AND category='$category'
");
$arr=mysqli_fetch_all($result,MYSQLI_ASSOC);
echo json_encode($arr);
?>
