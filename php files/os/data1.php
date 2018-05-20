<?php
include 'db.php';
$result=mysqli_query($con,"SELECT plant_name,address, CONCAT(short_name,' (',location,')') station_id,pollutants,status_,a.plant_id plant_id,a.station_id st_id

FROM station_info

INNER JOIN

(

SELECT real_pollutant_level_infos.plant_id, CONCAT(plant_nm,' ','(',plant_info.type,')') plant_name,

CONCAT(district,'\\,',town) address,station_id, GROUP_CONCAT(CASE WHEN TIMESTAMPDIFF(HOUR,recorded_time, NOW()) > 24 THEN CONCAT('<font color=\"red\">',parameter_code,'</font>') ELSE CONCAT('<font color=\"green\">',parameter_code,'</font>') END

ORDER BY parameter_code) pollutants, GROUP_CONCAT(CASE WHEN TIMESTAMPDIFF(HOUR,recorded_time, NOW()) > 24 THEN CONCAT(parameter_code,': LAST Received ON ',recorded_time) END

ORDER BY parameter_code SEPARATOR '<br>') status_

FROM real_pollutant_level_infos

INNER JOIN

plant_info ON real_pollutant_level_infos.plant_id = plant_info.plant_id

WHERE real_pollutant_level_infos.plant_id LIKE 'hindalco' AND analyzer='Stack'

GROUP BY real_pollutant_level_infos.plant_id,station_id) a ON station_info.plant_id = a.plant_id AND station_info.station_id = a.station_id

ORDER BY station_id");

$arr=mysqli_fetch_all($result,MYSQLI_ASSOC);
echo json_encode($arr);
?>