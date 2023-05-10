import java.sql.*;
import java.util.Calendar;

public class DbTimeTest {
    public static void main(String[] args) {
        int hour, min, day, month, year;
        String currTim, currDat;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/JavaTestDB","phpma","pass");
            Statement stmt=con.createStatement();
            while(true){
                Calendar date = Calendar.getInstance();
                hour = date.get(Calendar.HOUR_OF_DAY);
                min = date.get(Calendar.MINUTE);
                day = date.get(Calendar.DAY_OF_MONTH);
                month = date.get(Calendar.MONTH);
                year = date.get(Calendar.YEAR);
                currTim = hour+"."+min;
                currDat = day+"."+month+"."+year;
                System.err.println(currTim+" : "+currDat);
            ResultSet rs=stmt.executeQuery("SELECT text FROM jvdb WHERE date="+currTim);
                while (rs.next()) {
                    if(rs.getString(1)==" ")
                        System.out.println(currTim);
                    else {
                        ProcessBuilder builder = new ProcessBuilder(
                                "notify-send", "Notify", rs.getString(1));
                        builder.inheritIO().start();
                    }
                }
//           con.close();
                Thread.sleep(45000);
            }
        } catch (Exception e) {System.out.println(e);}
    }
}