<?php
include 'db.php';
$plant_name=$_REQUEST["plant_name"];
$short_name=$_REQUEST["short_name"];
$analyzer=$_REQUEST["analyzer"];
$parameter_code=$_REQUEST["parameter_code"];

$result=mysqli_query($con,"SELECT DISTINCT(recorded_time),recorded_level FROM pollutant_level_infos_hourly,plant_info,station_info WHERE parameter_code='$parameter_code' AND pollutant_level_infos_hourly.plant_id=plant_info.plant_id AND pollutant_level_infos_hourly.station_id=station_info.analyzer='$analyzer' AND pollutant_level_infos_hourly.station_id=station_info.station_id AND plant_info.plant_name='$plant_name' AND pollutant_level_infos_hourly.station_id=station_info.station_id AND station_info.short_name='$short_name' ORDER BY recorded_time ASC  LIMIT 10 OFFSET 10");
$arr=mysqli_fetch_all($result,MYSQLI_ASSOC);
echo json_encode($arr);
?>