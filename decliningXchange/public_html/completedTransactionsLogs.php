/*
 * Copyright (c) Clara Martinez Rubio, Thomas Kaizer, Matthew Hood 2020. All rights reserved.
 * Last modified 5/10/20 10:31 AM
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 */
 

<html>
<head>
    <title>Completed Transactions Table</title>
<link rel="stylesheet" href="style.css">
<link rel ="stylesheet" href="table.css">
<link rel="stylesheet" type="text/css" href="./nav.css">
</head>

<body>
    <table>
        <tr>
            <th>Item ID</th>
            <th>Seller ID</th>
            <th>Buyer ID</th>
            <th>Purchase Price</th>
	    <th>Action</th>
	    <th>Date</th>
        </tr>
        <?php
                $conn = new mysqli("127.0.0.1","cmarti21", "dwnvs7Xp", "tkaizer_1");
                if($conn-> connect_errno){
                    echo "Failed to connect to MySQL: (" . $conn->connect_errno . ") " . $conn->connect_error;
                }

                $sql = "SELECT * from completedTransactionsLogs";
                $result  = $conn->query($sql);

                if($result->num_rows > 0){
                    while($row =  mysqli_fetch_assoc($result)){

                        echo "<tr><td>{$row['ItemID']}</td><td>{$row['SellerID']}</td><td>{$row['BuyerID']}</td><td>{$row['PurchasePrice']}</td><td>{$row['action']}</td><td>{$row['cdate']}</td></tr>";
                    }
                }

                $conn-> close();
            ?>
    </table>
</body>

</html>
