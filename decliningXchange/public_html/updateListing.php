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
    <link rel="stylesheet" href="listingStyle.css">
     <script>
                const queryString = window.location.search;
                const urlParams = new URLSearchParams(queryString);
                const failed = urlParams.get('failed');
                if (failed == "true"){
                        alert("Error: You tried to update an invalid item")
                }
     </script>
</head>

<body>
    <div id="listingForm">
        <h1>Update a listing</h1>
        <form method="post" action="updateProcess.php">
	    <input type="number" placeholder="Item ID" name="itemID">
            <br>
            <input type="number" placeholder="Requested Declining" name = "requestedDeclining">
            <br>
            <input type="submit" value = "Update Listing" id="submit">
            <br>
        </form>
         <button id="cancel" onclick="window.location.href='http://betaweb.csug.rochester.edu/~cmarti21/userListings.php'">Cancel</button>
    </div>
    <img
        src="https://i0.wp.com/www.ecommerce-nation.com/wp-content/uploads/2016/10/How-to-use-Marketplaces-for-Cross-Border-E-Commerce-1.png?zoom=2&resize=713%2C535&ssl=1">
</body>

</html>
