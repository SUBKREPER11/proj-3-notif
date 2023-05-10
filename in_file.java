import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.sql.*;

public class in_file {
    public static void main(String[] args) {
        String username = "notif";
        String password = "notif";
        String dbname = "JavaTestDB";
        String servername = "jdbc:mysql://192.168.109.120:3306/"+dbname;
        System.out.println("test");
        Connection connection = null;
        try {
            File myObj = new File("test.txt");
            Scanner myReader = new Scanner(myObj);
            String title;
            String text;
            String data;
            String time
            while (myReader.hasNextLine()) {
                title = myReader.nextLine();
                text = myReader.nextLine();
                data = myReader.nextLine();
                time = myReader.nextLine();


            }
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(servername, username, password);
                Statement statement;
                statement = connection.createStatement();
                Integer IntoStetment;
                IntoStetment = statement.executeUpdate("INSERT INTO `Powiadomienia` (`id`, `title`, `text`, `date`, `time`) VALUES (NULL, '"+title+"', '"+text+"', '"+data+"','"+time+"');");
                statement.close();
                connection.close();
            } catch (Exception exception) {
                System.out.println(exception);
    
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

