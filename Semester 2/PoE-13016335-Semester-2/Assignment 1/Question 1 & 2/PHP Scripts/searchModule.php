<!DOCTYPE html>
<head>
<link rel="stylesheet" type="text/css" href="stylingVc.css">
</head>
<html>
<h1 align="center"><u>Student Attendance Record</u></h1>
		 <form name="Registration" align="center" id="myForm" method = "post">				
					<p>Module Code : <input type = "text" name = "modulecode" </p>
					<p><input type = "reset" value = "Clear" />&nbsp;
					&nbsp; <input type = "Submit" name = "submit"/>
			</form>	
                        <?php
		if(isset($_POST['submit']))
		{
                        //connect to database
			include("DBConn.php");
                        $ModuleCode = $_POST['modulecode'];
			$SqlQuery = "CALL searchModule('$ModuleCode')";
			echo "<table width = '100%' height = '50%' border = '3'>\n";
			echo "<tr><th>Student Number</th>
			<th>Module Code</th>
			<th>Check In Time</th>
			<th>Check Out Time</th>
			<th>Date of Check In</th></tr>\n";  
		
  
			//run the store proc
			$result = mysqli_query($Dbconnect, $SqlQuery);
      
			//loop the result set
			while ($rowRecord = mysqli_fetch_array($result))
			{   
				echo "<tr><td align=center>{$rowRecord['Student_Number']}</td>";	
                echo "<td align=center>{$rowRecord['Module_Code']}</td>";
				echo "<td align=center>{$rowRecord['CheckIn_Time']}</td>";
				echo "<td align=center>{$rowRecord['CheckOut_Time']}</td> \n";
                echo "<td align=center>{$rowRecord['Date']}</td> \n";
				echo "</tr>";
			}

		}
		?>
</html>
	