<?php
$servername = "localhost";
$username = "xxxxxxxxxxxx";
$password = "xxxxxxxxxx";
$dbname = "xxxxxxxxxx";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$USER_NAME=$_POST['USER_NAME'];
$DATE=$_POST['DATE'];
$TIME=$_POST['TIME'];
$LONGITUDE=$_POST['LONGITUDE'];
$LATITUDE=$_POST['LATITUDE'];
$WASTE_TYPE=$_POST['WASTE_TYPE'];



$sql = "INSERT INTO  USERDATA (USER_NAME, DATE, TIME, LONGITUDE, LATITUDE, WASTE_TYPE)
VALUES ('{$USER_NAME}', '{$DATE}', '{$TIME}', '{$LONGITUDE}', '{$LATITUDE}', '{$WASTE_TYPE}')";

if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?> 