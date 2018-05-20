<?php
 include 'db.php';
 $plt_name=$_REQUEST["plt_name"];
 $analyzer=$_REQUEST["analyzer"];
 $result=mysqli_query($con,"SELECT short_name from station_info,plant_info where plant_info.plant_id = station_info.plant_id and plant_info.plant_name = '$plt_name' and station_info.analyzerv2 = '$analyzer'");
$arr=mysqli_fetch_all($result,MYSQLI_ASSOC);
echo json_encode($arr);
?>
