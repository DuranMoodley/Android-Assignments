<?php
	    //Sends the relevant items to the item table
		include("DBConn.php");
		$Username = $_POST['username'];
		$UserScore = $_POST['userscore'];
		//Insert the user entered values into db
		$SQLString = "CALL update_liv_ranking('$Username','$UserScore')" ;
		$QueryResult = mysqli_query ($Dbconnect,$SQLString);
			
		//Checks if the query was successful
		if ($QueryResult === FALSE)
		{
			echo "<p> Unable to update table </p>" .  "<p>Error code" . mysqli_errno($Dbconnect). ":". mysqli_error ($Dbconnect)."</p>";
		}
		else 
		{
			echo "Successfully Updated";
		}	
?>