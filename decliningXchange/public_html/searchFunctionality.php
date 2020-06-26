/*
 * Copyright (c) Clara Martinez Rubio, Thomas Kaizer, Matthew Hood 2020. All rights reserved.
 * Last modified 5/10/20 10:33 AM
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 */


<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="./nav.css">
    <link rel="stylesheet" type = "text/css" href="./table.css">
    <link rel="stylesheet" type="text/css" href="./style.css">
</head>
<body>

<div id="nav">
        <h1 id="title">Declining Xchange</h1>
        <div id="userDropdown"  style="display:inline-block">
            <button class='dropButton'>Menu</button> 
            <div class="dropdown-content" id = "userDropContent">
                <p onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/signOut.php'">Sign Out</p>
                <p onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/userListings.php'">Your Listings</p>
                <p onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/history.php'">History</p>
		<p onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/homePage.php'">Home</p>
            </div>
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
	<form action="listingSearch.php" method="post">
        <input id="searchBar" type="text" name="search" placeholder="Listing Value">
        <button class="dropButton" id="searchButton" name="submit-search">Search</button>
</form>
    </div>

<div id="container">

	<table>
<tr>
        <th>Student Name</th>
        <th>Item Name</th>
        <th>Requested Declining</th>
</tr>
    <?php
 $conn = new mysqli("127.0.0.1","cmarti21", "dwnvs7Xp", "cmarti21_1");
                if($conn-> connect_errno){
                    echo "Failed to connect to MySQL: (" . $conn->connect_errno . ") " . $conn->connect_error;
                }
    $search = mysqli_real_escape_string($conn, $_POST['search']);
    $sql = "SELECT * FROM listings INNER JOIN product on listings.ItemID = product.ItemID INNER JOIN user on listings.StudentID = user.StudentID WHERE product.ItemName LIKE '%$search%' OR  product.ItemID LIKE '%$search%' OR  user.StudentName LIKE '%$search%'";
    $result = mysqli_query($conn, $sql);
    $queryResult = mysqli_num_rows($result);

    if ($queryResult) {
        while($row = mysqli_fetch_assoc($result)) {
            echo "<tr><td>{$row['StudentName']}</td><td>{$row['ItemName']}</td><td>{$row['RequestedDeclining']}</td></tr>";
        }
    }
    ?>
</table>
</div>
</body>
</html>
