<?php
$servername = "localhost";
$username = "phpma";
$password = "pass";
$dbname = "JavaTestDB";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}
echo "Connected successfully</br>";
?>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Add notyfication (not from file yet)</title>
</head>

<body>
  <?php
  if (isset($_POST['button1'])) {
    //     $sql = "CREATE TABLE IF NOT EXISTS `".$dbname."`.`".$_POST['date']."`( `id` INT NOT NULL AUTO_INCREMENT ,
    //  `text` VARCHAR(500) NOT NULL,
    //  `date` VARCHAR NOT NULL,
    //   PRIMARY KEY (`id`));";
    $date = $_POST['date'];
    $sql = "CREATE TABLE IF NOT EXISTS `" . $dbname . "`.`Powiadomienia` 
    ( `id` INT AUTO_INCREMENT , 
    `title` VARCHAR(500) NOT NULL,
    `text` VARCHAR(500) NOT NULL ,
    `time` VARCHAR(50) NOT NULL ,  
    `date` VARCHAR(50) NOT NULL , 
    PRIMARY KEY (`id`)) 
    ENGINE = InnoDB;";
    mysqli_close($conn);
    $conn = new mysqli($servername, $username, $password, $dbname);
    if (mysqli_query($conn, $sql)) {
      echo "Table created successfully </br>";
      $sql2 = "INSERT INTO `Powiadomienia` 
            (`id`, `title`, `text`, `time`, `date`,`link`) 
            VALUES (NULL, '" . $_POST['title'] . "', '" . $_POST['text'] . "', '" . $_POST['time'] . "', '".$_POST['date']."','".$_POST['link']."')";
      if (mysqli_query($conn, $sql2)) {
        echo "Data inserted successfully </br>";
      } else {
        echo "Error inserting data: " . mysqli_error($conn) . "</br></br>";
      }
    } else {
      echo "Error creating table: " . mysqli_error($conn) . "</br></br>";
    }
    // echo $_POST['date'];
    // echo $sql;
  }
  ?>
  </br>
  <form method="post">
    Data pojawienia się powiadomienia:</br>
    <input type="date" name="date" required pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}"></br>
    Czas pojawienia się powiadomienia:</br>
    <input type="time" name="time"></br>
    Tytuł powiadomienia:</br>
    <input type="text" name="title" placeholder="Typle title here"></br>
    Treść powiadomienia:</br>
    <textarea name="text" placeholder="Type text here" maxlength="500"></textarea></br>
    Link (opcionalne):</br> 
    <textarea name="link" placeholder="Type link here" maxlength="500"></textarea></br></br>
    <input type="submit" name="button1" value="Submit" />
  </form>
</body>

</html>