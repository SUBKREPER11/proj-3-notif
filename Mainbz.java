import java.sql.*;//import biblioteki do obsługi bazy danych

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Mainbz {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Wpis do Bazydanych z pliku");
        JTextArea area1 = new JTextArea();
        //String username = "notif";
        String username = "root";
        //String password = "notif";
        String password = "";
        String dbname = "JavaTestDB";
        //String servername = "jdbc:mysql://192.168.236.120:3306/" + dbname;
        String servername = "jdbc:mysql://localhost:3306/" + dbname;
        System.out.println("test");
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(servername, username, password);
            Statement statement;
            statement = connection.createStatement();//połączenie z bazą danych
            Integer IntoStetment;
            IntoStetment = statement.executeUpdate(
                    "INSERT INTO `Powiadomienia` (`id`, `title`, `text`, `date`, `time`) VALUES (NULL, 'test', 'testowy taki jak twa śmierć www.gry.pl', '2023-04-24','15:33');");
            ResultSet resultSet;
            resultSet = statement.executeQuery(
                    "select * from Powiadomienia");

            String note;
            String title;
            String time;
            String date;
            // wyświetlenie w terminalu
            while (resultSet.next()) {

                title = resultSet.getString("title");
                note = resultSet.getString("text");
                time = resultSet.getString("time");
                date = resultSet.getString("date").trim();
                System.out.println("Title : " + title + " Note : " + note + " Time : " + time + " Date : " + date);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception exception) {
            System.out.println(exception);

        }
        frame.add(area1);
        frame.setSize(300,300);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
