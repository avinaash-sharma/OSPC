<?php
include 'db.php';
$plant_name=$_REQUEST["plant_name"];
$short_name=$_REQUEST["short_name"];
$analyzer=$_REQUEST["analyzer"];
$result=mysqli_query($con,"SELECT distinct(parameter_code) FROM pollutant_level_infos_hourly,plant_info,station_info WHERE pollutant_level_infos_hourly.plant_id=plant_info.plant_id AND pollutant_level_infos_hourly.analyzer='$analyzer' AND pollutant_level_infos_hourly.station_id=station_info.station_id AND plant_info.plant_name='$plant_name' AND station_info.station_id=station_info.short_name AND station_info.short_name='$short_name'");
$arr=mysqli_fetch_all($result,MYSQLI_ASSOC);
echo json_encode($arr);
?>


