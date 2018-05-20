<?php
 include 'db.php';
 $plt_name=$_REQUEST["plt_name"];
 $analyzer=$_REQUEST["analyzer"];
 $result=mysqli_query($con,"SELECT TIME, recorded_level FROM db_migrate_pollutant_level_data_2017_9 WHERE station_id="AAQ-3" and parameter_code="PM2.5" LIMIT 11 OFFSET 11