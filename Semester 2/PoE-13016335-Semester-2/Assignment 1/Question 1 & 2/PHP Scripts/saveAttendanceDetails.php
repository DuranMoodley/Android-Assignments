<?php
	    //Sends the relevant items to the item table
		include("DBConn.php");
		$studentNumber =  $_POST['studNumber'];
		$moduleCode = $_POST['studModuleCode'];
		$checkInTime = $_POST['checkIn'];
		$checkOutTime = $_POST['checkOut'];
		$date = $_POST['date'];
		
		//Insert the user entered values into db
		$SQLString = "INSERT INTO tbl_student_attendance".
					 "(Student_Number,Module_Code,CheckIn_Time,CheckOut_Time,Date)".
					 "VALUES ('$studentNumber','$moduleCode','$checkInTime','$checkOutTime','$date')" ;
					 $QueryResult = mysqli_query ($Dbconnect,$SQLString);
			
		//Checks if the query was successful
		if ($QueryResult === FALSE)
		{
			echo "<p> Unable to update table </p>" .  "<p>Error code" . mysqli_errno($Dbconnect). ":". mysqli_error ($Dbconnect)."</p>";
		}
		else 
		{
			echo "<p> Successfully added </p>";
		}	
?>