<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "akl4pt5";

$mysql = new mysqli($servername,$username,$password,$dbname);
if($mysql->connect_error){
    die("conection error");
}
$q1 = "SELECT * FROM studenci";

?>
<div>
    <form method="GET">
        <input type="text" id="imie">
        <input type="text" id="nazwisko">
        <input type="text" id="date">
        <input type="submit" id="button1" value="prześlij">
        <?php 
        if(isset($_POST['button1'])){
            $sql1 = "INSERT INTO `studenci`(`id`, `imie`, `nazwisko`, `date`) VALUES (NULL, '".$_GET['imie']."', '".$_GET['nazwisko']."','".$_GET['date']."')";
        }
        ?>
    </form>
</div>
<div>
    <?php 
    while($rows = $result->fetch_assoc())
    ?>
<table>
    <tr>
        <td>
            <?php echo $rows['imie'] ?>
        </td>
        <td>
             <?php echo $rows['nazwisko']?>
        </td>
    </table>
</div>>
