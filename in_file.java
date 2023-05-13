import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.nio.charset.Charset;
import java.util.Scanner; // Import the Scanner class to read text files
import javax.swing.*;

import com.mysql.cj.result.Field;

import java.sql.*;

public class in_file {
    public static void main(String[] args) {
        //String username = "notif";
        //String password = "notif";
        String dbname = "JavaTestDB";
        //String servername = "jdbc:mysql://192.168.109.120:3306/"+dbname;
        String username = "root";
        String password = "";
        String servername = "jdbc:mysql://localhost:3306/" + dbname;
        System.out.println("test");
        
            try {
                JFrame frame = new JFrame("Wpis do Bazydanych z pliku");
        JPanel p1 = new JPanel();
        JButton button = new JButton("wybierz plik");
        
        p1.add(button);
        frame.add(p1);
        frame.setSize(200,80);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                button.addActionListener(e -> {
                    // Get the contents of the JTextArea component.
                    JFileChooser chosero = new JFileChooser();
                    chosero.showSaveDialog(null);
                    File myObj = new File(chosero.getSelectedFile().getAbsolutePath()); 
                    //System.out.println("contents = " + contents);
                    System.setProperty("file.encoding","UTF-8");
                    try {
                        
                    
                java.lang.reflect.Field charset = Charset.class.getDeclaredField("defaultCharset");
                charset.setAccessible(true);
                charset.set(null, null);
                 
                String title;
                String text;
                String link;
                String data;
                String time;
                
                Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        title = myReader.nextLine();
                        text = myReader.nextLine();
                        link = myReader.nextLine();
                        data = myReader.nextLine();
                        time = myReader.nextLine();
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = null;
                connection = DriverManager.getConnection(servername, username, password);
                Statement statement;
                statement = connection.createStatement();
                Integer IntoStetment;
                        IntoStetment = statement.executeUpdate("INSERT INTO `Powiadomienia` (`id`, `title`, `text`, `link`, `date`, `time`) VALUES (NULL, '"+title+"', '"+text+"','"+link+"', '"+data+"','"+time+"');");
                        
                
                
                statement.close();
                connection.close();        
                }
                myReader.close();
            }catch (Exception ex) {
                System.out.println("An error occurred.");
            ex.printStackTrace();
            }
                });
                
            
            
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }
}

