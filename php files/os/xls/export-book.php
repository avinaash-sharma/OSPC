<?php
include('db_con.php');

$conn = new mysqli('localhost', 'root', '');

$filename=$_REQUEST["filename"];
$query=$_REQUEST["query"];
$fromdate=$_REQUEST["fromdate"];
$todate=$_REQUEST["todate"];
$station_id=$_REQUEST["station_id"];
$plantname=$_REQUEST["plantname"];






$setSql = $query;
$setRec = mysqli_query($conn,$setSql);



$stmt=$db_con->prepare($setSql);
$stmt->execute();


$columnHeader ='';
$columnHeader="\n"."station id '$station_id'"."\t\t"."name '$plantname'"."\t\t"."fromdate '$fromdate'"."\t\t"."todate '$todate'"."\n\n\n"."TIME"."\t\t\t"."station_id"."\t\t\t"."parameter_code"."\t\t\t"."AVERAGE"."\t\t\t"."MINIMUM"."\t\t\t"."MAXIMUM"."\t\t\t";


$setData='';

while($rec =$stmt->FETCH(PDO::FETCH_ASSOC))
{
  $rowData = '';
  foreach($rec as $value)
  {
    $value = '"' . $value . '"' . "\t\t\t";
    $rowData .= $value;
  }
  $setData .= trim($rowData)."\n";
}


header("Content-type: application/octet-stream");
header("Content-Disposition: attachment; filename='$filename'");
header("Pragma: no-cache");
header("Expires: 0");

echo ucwords($columnHeader)."\n".$setData."\n";

?>
