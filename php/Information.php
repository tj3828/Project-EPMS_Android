<?php

require_once($_SERVER['DOCUMENT_ROOT'].'/preset.php');

$userID = $_GET['userID'];
$userPassword = $_GET['userPassword'];
$query = "SELECT * FROM USER where userID='$userID' and userPassword='$userPassword'";

$result = $mysqli->query($query);
$total_record = $result->num_rows;


$result_array = array();


for ( $i = 0; $i < $total_record; $i++ ) {

  $result->data_seek($i);
  $row = $result->fetch_array();
  $row_array = array(

    "userID" => $row['userID'],
    "userName" => $row['userName'],
    "userAge" => $row['userAge'],
    "userBTName" => $row['userBTName'],
    "userAddress" => $row['userAddress']
    );

  array_push($result_array,$row_array);
}

$arr = array(

  "status" => "OK",
  "num_result" => "$total_record",
  "results" => $result_array
  );

$json_array = json_encode($arr);

print_r($json_array);
?>