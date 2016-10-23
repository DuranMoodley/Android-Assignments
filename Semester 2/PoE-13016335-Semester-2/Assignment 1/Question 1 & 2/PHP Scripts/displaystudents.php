<?php
	include("DBConn.php");
        $studRow = array();
	//if true, run sql statement to pull details of that item
	$SQLString = "SELECT * FROM tbl_student_attendance";
	$QueryResult = mysqli_query($Dbconnect,$SQLString);
        echo "<table width = '100%' height = '50%' border = '3'>\n";
        echo "<tr><th>Student Number</th>
		 <th>Module Code</th>
		 <th>Check In Time</th>
		 <th>Check Out Time</th>
		 <th>Date of Check In</th></tr>\n"; 
	
	while(($rowRecord = mysqli_fetch_assoc($QueryResult)) == TRUE)
	{		
                echo "<tr><td align=center>{$rowRecord['Student_Number']}</td>";	
                echo "<td align=center>{$rowRecord['Module_Code']}</td>";
		echo "<td align=center>{$rowRecord['CheckIn_Time']}</td>";
		echo "<td align=center>{$rowRecord['CheckOut_Time']}</td> \n";
                echo "<td align=center>{$rowRecord['Date']}</td> \n";
		echo "</tr>";
               
                
	};	
        
          

?>