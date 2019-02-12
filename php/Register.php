<?php
    $con = mysqli_connect("localhost", "tj3828", "dhxkrwhd38", "tj3828");

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $userName = $_POST["userName"];
    $userAge = $_POST["userAge"];
    $userBTName = $_POST["userBTName"];
    $userAddress = $_POST["userAddress"];

    $statement = mysqli_prepare($con, "INSERT INTO USER VALUES (?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssssss", $userID, $userPassword, $userName, $userAge, $userBTName, $userAddress);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["success"] = true;
    
    echo json_encode($response);

?>