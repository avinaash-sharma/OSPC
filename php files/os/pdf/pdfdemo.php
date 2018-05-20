<?php
//include connection file
include_once("connection.php");
include_once('libs/fpdf.php');
 
class PDF extends FPDF
{
// Page header
function Header()
{
    // Logo
    //$this->Image('logo.png',10,-1,70);
    $this->SetFont('Arial','B',13);
    // Move to the right
    $this->Cell(80);
    // Title
    // $this->Cell(80,10,'Employee List',1,0,'C');
    // Line break
    $this->Ln(18);
}
 
// Page footer
function Footer()
{
    // Position at 1.5 cm from bottom
    $this->SetY(-15);
    // Arial italic 8
    $this->SetFont('Arial','I',8);
    // Page number
    $this->Cell(0,10,'Page '.$this->PageNo().'/{nb}',0,0,'C');
}
}
 
$db = new dbObj();
$connString =  $db->getConnstring();
$display_heading = array('time'=>'Date', 'station_id'=> 'Station Id.', 'parameter_code'=> 'PC','recorded_level'=> 'RL','min_recorded_level'=> 'MinRL','max_recorded_level'=> 'MaxRL','recorded_time'=> 'RT');
 
 $result = mysqli_query($connString, "SELECT DATE_FORMAT(recorded_time, '%b-%Y %d/%m/%Y') TIME,station_id,parameter_code,recorded_level,min_recorded_level,max_recorded_level,recorded_time FROM db_migrate_pollutant_level_data_2017_9 LIMIT 10 OFFSET 10") or die("database error:". mysqli_error($connString));

// $result = mysqli_query($connString, "SELECT station_id,parameter_code,recorded_level,min_recorded_level,max_recorded_level FROM db_migrate_pollutant_level_data_2017_9 LIMIT 10 OFFSET 10") or die("database error:". mysqli_error($connString));

//$header = mysqli_query($connString, "SHOW columns FROM db_migrate_pollutant_level_data_2017_9");
 
$pdf = new PDF();
//header
$pdf->AddPage();
//foter page
$pdf->AliasNbPages();
$pdf->SetFont('Arial','B',8);
 //foreach($header as $heading) {
// $pdf->Cell(25,12,$display_heading[$heading['Field']],1);
// }

$res=mysqli_fetch_all($result);
$sizearr=sizeof($res); 

 // echo $res[0][0];
 // echo $res[1][0];
 // echo $res[2][0];
 // echo $res[3][0];
 // echo $res[4][0];
 // echo $res[5][0];
 // echo $res[6][0];
$count=0;
for($i=0;$i<$sizearr;$i++)
{
     $pdf->Ln();
     for($j=0;$j<6;$j++)
     {
         $pdf->Cell(28,12,$res[$i][$j] ,1,0,'C');     

     }
}



// foreach($res as $row) 
// {
//  
  
//   foreach($row as $column)
//       {

//       if($column==3)
//       {
//       $pdf->Cell(28,12,$column[0] ,1,0,'C');  
//       }

//       // if($count==0||$count==1||$count==6)
//       // {
//       // $pdf->Cell(28,12,$column[0] ,1,0,'C');
//       // }


//       if($count==2)
//       {
//       $pdf->Cell(28,12,$column ,1,0,'C');
//       }
    
//       if($count==3||$count==4||$count==5)
//        {
//         $count++;
//         continue ;
//        }
//   }
// }

$pdf->Output(); 



?>
<!-- 
