/*
 * Copyright (c) Clara Martinez Rubio, Thomas Kaizer, Matthew Hood 2020. All rights reserved.
 * Last modified 5/10/20 10:34 AM
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 */


<!DOCTYPE html>
<html lang="en">

<head>
    <title>Edit a Table</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="SignUpStyle.css">
</head>

<body>
    <div id= "signUpForm">
    <h1>Sign up decliningXchange</h1>
    <form method="post" action="process.php">
        <input type="text" placeholder="Student Name" name = "studentName" id="studentName">
        <br>
        <input type="text" placeholder = "Student ID" name ="studentID" id = "studentID">
        <br>
        <input type="email" placeholder = "Email" name="email" id="email">
        <br>
        <input type="number" placeholder = "Declining Balance" name= "declining"  id = "declining">
	<br>
	<input type="submit" id="submit" value="Sign Up">
    </form>
	<button id="cancel" onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/welcome.html'">Cancel</button>
    </div>
	<img src="https://image.freepik.com/free-vector/stock-exchange-isometric-banner-with-currency-exchange-headline-white-collar-make-money-illustration_1284-31324.jpg"
        alt="">
</body>

</html>

