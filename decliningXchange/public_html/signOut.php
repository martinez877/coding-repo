/*
 * Copyright (c) Clara Martinez Rubio, Thomas Kaizer, Matthew Hood 2020. All rights reserved.
 * Last modified 5/10/20 10:34 AM
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 */


<?php
	session_start();
?>
<html>
<head>
</head>
<body>
<?php
	$_SESSION = array();
	session_destroy();
	header("Location: http://betaweb.csug.rochester.edu/~cmarti21/welcome.html");
?>
</body>
</html>
