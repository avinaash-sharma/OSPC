<?php
include 'db.php';
$user_id=strip_tags($_REQUEST["user_id"]);
$password=strip_tags($_REQUEST["password"]);
$user_id=mysqli_real_escape_string($con,$user_id);
$password=mysqli_real_escape_string($con,$password);
$result=mysqli_query($con,"select * from user_info where user_id='$user_id' and password='$password'");
$arr=mysqli_fetch_assoc($result);
if($arr["user_id"]==$user_id)
{
	echo "success";
}
else
{
	echo "invalid";
}
?>