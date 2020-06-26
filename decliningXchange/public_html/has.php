/*
 * Copyright (c) Clara Martinez Rubio, Thomas Kaizer, Matthew Hood 2020. All rights reserved.
 * Last modified 5/10/20 10:32 AM
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 */


<?php
session_start();
?>
<html>
<head>
    <title> Has Table</title>
<link rel="stylesheet" href="style.css">
<link rel ="stylesheet" href="table.css">
<link rel="stylesheet" type="text/css" href="./nav.css">
<link rel="stylesheet" type="text/css" href="./nav.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
        <script>
                window.onload = function(){
                        <?php
                                if(isset($_SESSION['student_id'])){
                                        $id = $_SESSION['student_id'];
                                }
                                else{
                                        $id="null";
                                }
                        ?>
                        var id = <?php echo $id ;?>;
                        if(id==null){
                                document.getElementById("userDropdown").style.visibility="hidden";
                                document.getElementById("backButton").style.visibility="visible"
                        }
                        else{
                                document.getElementById("userDropdown").style.visibility="visible";
                                document.getElementById("backButton").style.visibility="hidden";
                        }
                }
        </script>

</head>

<body>
<div id="nav">
        <h1 id="title">Declining Xchange<h1>
        <div id="userDropdown"  style="display:inline-block;" >
            <button class='dropButton'>Menu</button> 
            <div class="dropdown-content" id = "userDropContent">
                <p onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/signOut.php'">Sign Out</p>
                <p onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/userListings.php'">Your Listings</p>
                <p onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/history.php'">History</p>
		<p onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/homePage.php'">Home</p>
            </div>
        </div>
        <div id="backButton" style="display:inline-block;">
            <button class='dropButton' onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/welcome.html'">Back</button>
        </div>
        <div class="dataDropdown" style="display: inline-block">
            <button class="dropButton">data tables</button>
            <div class="dropdown-content" id="dataDropContent">
                <p onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/user.php'">user</p>
                <p onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/product.php'">product</p>
                <p onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/listings.php'">listings</p>
                <p onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/has.php'">has</p>
                <p
                    onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/completedTransactions.php'">
                    completed transactions</p>
                <p onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/biddingOn.php'">bidding on
                </p>
                <p onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/are.php'">are</p>
                <p onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/compltedTransactionsLogs.php'"></p>
            </div>
        </div>
    </div>
<div id="container">
    <table>
        <tr>
            <th>Item ID</th>
            <th>Seller ID</th>
            <th>Buyer ID</th>
        </tr>
        <?php
                $conn = new mysqli("127.0.0.1","cmarti21", "dwnvs7Xp", "cmarti21_1");
                if($conn-> connect_errno){
                    echo "Failed to connect to MySQL: (" . $conn->connect_errno . ") " . $conn->connect_error;
                }

                $sql = "SELECT * from has";
                $result  = $conn->query($sql);

                if($result->num_rows > 0){
                    while($row =  mysqli_fetch_assoc($result)){

                        echo "<tr><td>{$row['ItemID']}</td><td>{$row['SellerID']}</td><td>{$row['BuyerID']}</td></tr>";                   
			 }
                }

                $conn-> close();
            ?>
    </table>
</div>
</body>

</html>
