<?php
 include 'db.php';
 $plt_name=$_REQUEST["plt_name"];
 $analyzer=$_REQUEST["analyzer"];
 $result=mysqli_query($con,"SELECT DISTINCT(parameter_code) FROM pollutant_level_infos_hourly,plant_info where plant_name='$plt_name' AND plant_info.plant_id=pollutant_level_infos_hourly.plant_id AND pollutant_level_infos_hourly.analyzer='$analyzer'");
$arr=mysqli_fetch_all($result,MYSQLI_ASSOC);
echo json_encode($arr);
?>
