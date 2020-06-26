/*
 * Copyright (c) Clara Martinez Rubio, Thomas Kaizer, Matthew Hood 2020. All rights reserved.
 * Last modified 5/10/20 10:33 AM
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 */
 

<?php
$conn = new mysqli("127.0.0.1","cmarti21", "dwnvs7Xp", "cmarti21_1");
if($conn-> connect_errno){
	echo "Failed to connect to MySQL: (" . $conn->connect_errno . ") " . $conn->connect_error;
}
session_start();
?>
<?php
	$student_name = $_POST['studentName'];
	//print_r($student_name);
	$student_id = $_POST['studentID'];
	$email = $_POST['email'];
	$declining = $_POST['declining'];

	$sql = "INSERT INTO user VALUES ('$student_id','$student_name','$email','$declining',0)";
       $result = $conn->query($sql);	

	if(mysqli_affected_rows($conn) > 0){
		echo '<p>User Added</p>';
		echo '<a href="signUp.php">Go back</a>';
		echo '<br>';
		echo '<a href="user.php">Check the updated user table</a>';
		$_SESSION["name"] = $student_name;
		$_SESSION["student_id"] = $student_id;
		header("Location: http://betaweb.csug.rochester.edu/~cmarti21/homePage.php");
		exit;
	}
	else{
		echo '<p>User not added. There was a problem.</p>';
		echo mysqli_error($conn);
		header("Location: http://betaweb.csug.rochester.edu/~cmarti21/signUp.php");
	}
?>
