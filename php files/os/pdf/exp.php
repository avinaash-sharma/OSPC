

<?php
//include connection file
include_once("connection.php");
include_once('libs/fpdf.php');
$filename=$_REQUEST["filename"];
$query=$_REQUEST["query"];
$fromdate=$_REQUEST["fromdate"];
$todate=$_REQUEST["todate"];
$station_id=$_REQUEST["station_id"];
$plantname=$_REQUEST["plantname"];

// $address=$_REQUEST["address"];
// $status=$_REQUEST["status"];
 
class PDF extends FPDF
{


// Page header
function Header()
{
    // Logo
    $this->Image('logo.png',10,10,80);
    $this->SetFont('Arial','B',8);
    // Move to the right
    $this->Cell(80);
    // Title
	$this->Multicell(100,4.7,"Daily Aggregate Ambient Air Quality Monitoring Test System Report.\nFrom Date: " . $this->getFromDate() . " To Date: " . $this->getToDate() . "\nPlant: " . $this->getPlantName() ." \n",0,'R');
    // Line break
    $this->Ln(20);
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
function setFromDate($fromdate){
$this->fromdate = $fromdate;
}
function getFromDate(){
        return $this->fromdate;
}
function setToDate($todate){
$this->todate = $todate;
}
function getToDate(){
        return $this->todate;
}
function setPlantName($plantName){
$this->plantName = $plantName;
}
function getPlantName(){
        return $this->plantName;
}

}

  

$db = new dbObj();
$connString =  $db->getConnstring();
$result = mysqli_query($connString,$query) or die("database error:". mysqli_error($connString));


 
$pdf = new PDF();
$pdf->setFromDate($fromdate);
$pdf->setToDate($todate);
$pdf->setPlantName($plantname);
//header
$pdf->AddPage();
//foter page
$pdf->AliasNbPages();
$pdf->SetFont('Arial','B',12);
$pdf->Cell(50,14,"Day",1);
$pdf->Cell(35,14,"Station Id",1);
$pdf->Cell(40,14,"Parameter  Code",1);
// $x=$pdf->GetX();
// $y=$pdf->GetY();
// $pdf->SetXY($x+120,$y-14);  
$pdf->Cell(45,14,"Values",1);
$pdf->Ln();
$pdf->SetFont('Arial','',12);
 foreach($result as $row)  {
	//$pdf->Ln();
    $res1=mysqli_fetch_array($result,MYSQLI_NUM);
       
    $pdf->Cell(50,14,$res1[0],1);
    $pdf->Cell(35,14,$res1[1],1);
    $pdf->Cell(40,14,$res1[2],1);
    $pdf->Multicell(45,4.7,"Avg: " . $res1[3] . "\n" . "Min: " . $res1[4] . "\n" . "Max: " . $res1[5],1);
    //$x=$pdf->GetX();
    //$y=$pdf->GetY();
    // $pdf->SetXY($x+130,$y-14);
    // $pdf->Cell(40,14,$res1[6],1);
    
}

 $pdf->Output();
?>
