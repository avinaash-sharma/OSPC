 <?php
 include 'db.php';
 $plt_name=$_REQUEST["plt_name"];
 $analyzer=$_REQUEST["analyzer"];
 $fromdate=$_REQUEST["fromdate"];
 $todate=$_REQUEST["todate"];
 $result=mysqli_query($con,"SELECT short_name from station_info,plant_info
 where plant_info.plant_id = station_info.plant_id and plant_info.plant_name = '$plt_name'
 and station_info.analyzerv2 = '$analyzer'
 and modified_date >= '$fromdate' and modified_date <= '$todate'");
$arr=mysqli_fetch_all($result,MYSQLI_ASSOC);
 echo json_encode($arr);
?>
//http://localhost:8181/os/station_res1.php?plt_name=ACC%20Limited&analyzer=AAQ&fromdate=2017-01-01&todate=%272017-01-31%27