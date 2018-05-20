<?php
          include "db.php";
          $filename = "excelwork.xls";
          $exists = file_exists('excelwork.xls');
          if($exists)
          {
                   unlink($filename);
          }
                   $filename = "excelwork.xls";
                   $fp = fopen($filename, "wb");
                   $sql = "SELECT * FROM `plant_info`";

                   $result = mysqli_query($con,$sql);
                   $schema_insert = "";
                   $schema_insert_rows = "";
                   for ($i = 1; $i < mysqli_num_fields($result); $i++)
                   {
                   $insert_rows .= mysqli_field_name($result,$i) . "\t";
                   }
                   $insert_rows.="\n";
                   fwrite($fp, $insert_rows);
                   while($row = mysqli_fetch_row($result))
                   {
                   $insert = $row[1]. "\t" .$row[2]. "\t".$row[3]. "\t".$row[4]. "\t".$row[5];
                   $insert .= "\n";               //       serialize($assoc)
                   fwrite($fp, $insert);
                   }
                   if (!is_resource($fp))
                   {
                             echo "cannot open excel file";
                   }
                   echo "success full export";
                   fclose($fp);
?>