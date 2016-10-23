<?php
	include("DBConn.php");
    $rankings = array();
    
	    
	//if true, run sql statement to pull details of that item
	$SQLString = "CALL liv_rankings()";
	$QueryResult = mysqli_query($Dbconnect,$SQLString);
	
	while(($rowRecord = mysqli_fetch_assoc($QueryResult)) == TRUE)
	{	
		 $rankings[] = $rowRecord;
	}
	
	echo json_encode(array("rankings"=>$rankings));
?>