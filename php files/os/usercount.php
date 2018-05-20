<?php
 include 'db.php';
 $result=mysqli_query($con,"SELECT DISTINCT COUNT(user_id) FROM user_info");
 $arr=mysqli_fetch_all($result,MYSQLI_ASSOC);
 echo json_encode($arr);
?>
