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

$res=mysqli_fetch_array($result);

foreach($result as $row) 
{
  $pdf->Ln();
  
  foreach($row as $column)
    
    
      $pdf->Cell(28,12,$column   ,1,0,'C');
    
    // $pdf->Cell(28,12,$column,1,0,'C');
    
}

$pdf->Output();
?>
