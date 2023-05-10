import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        int hour, min, day, month, year;
        String currTim, currDat;
        String link = "", message = "";
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://192.168.109.120/JavaTestDB","notif","notif");
            Statement stmt=con.createStatement();
            while(true){
                Calendar date = Calendar.getInstance();
                hour = date.get(Calendar.HOUR_OF_DAY);
                min = date.get(Calendar.MINUTE);
//                min2 = date.get(Calendar.);
                currTim = String.format("%02d:%02d", hour, min);
//                System.out.println(currTim);
                day = date.get(Calendar.DAY_OF_MONTH);
                month = date.get(Calendar.MONTH) + 1;
                year = date.get(Calendar.YEAR);
//                currTim = hour+"."+min;
//                currDat = day+"."+month+"."+year;
//                System.out.println(month);
                currDat = String.format("%04d-%02d-%02d",year,month,day);
                System.err.println(currTim+" - "+currDat);
                DatabaseMetaData dbm = con.getMetaData();
                ResultSet checkTableEist = dbm.getTables(null, null, currDat, null);
//                if(checkTableEist.next()) {
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `JavaTestDB`.`Powiadomienia`" +
                            "( `id` INT AUTO_INCREMENT , \n" +
                            "    `title` VARCHAR(500) NOT NULL,\n" +
                            "    `text` VARCHAR(500) NOT NULL , \n" +
                            "    `time` VARCHAR(50) NOT NULL , \n" +
                            "    `date` VARCHAR(50) NOT NULL, \n" +
                            "    PRIMARY KEY (`id`)) \n" +
                            "    ENGINE = InnoDB;");
                    ResultSet rs=stmt.executeQuery("SELECT title, text FROM `Powiadomienia` WHERE date=\""+currDat+"\" AND time=\""+currTim+"\"");
                    while (rs.next()) {
                        if (rs.getString(2) == " ")
                            System.out.println(currTim);
                        else {
//                        ProcessBuilder builder = new ProcessBuilder(
//                                "notify-send", "Notify", rs.getString(1));
//                        builder.inheritIO().start();
                            String os = System.getProperty("os.name");
                            if (os.contains("Linux")) {
//                            if(link != null){
//                                message += " " + link;
//                            }
                                ProcessBuilder builder = new ProcessBuilder(
                                        "notify-send", rs.getString(1), rs.getString(2));
                                builder.inheritIO().start();
                            } else if (os.contains("Mac")) {
//                            if(link != null){
//                                message +=" " + link;
//                            }
                                ProcessBuilder builder = new ProcessBuilder(
                                        "osascript", "-e",
                                        "display notification \"" + rs.getString(2) + "\""
                                                + " with title \"" + rs.getString(1) + "\"");
                                builder.inheritIO().start();
                            } else if (SystemTray.isSupported()) {
                                SystemTray tray = SystemTray.getSystemTray();

                                TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
                                trayIcon.setImageAutoSize(true);
                                tray.add(trayIcon);
                                if (link != null) {
                                    ActionListener al = new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            System.out.println(link);
                                        }
                                    };
                                    trayIcon.addActionListener(al);
                                }
                                trayIcon.displayMessage("title", rs.getString(2), TrayIcon.MessageType.INFO);
                            }
                        }
                    }
//                }else System.err.println("No table `"+currDat+"` yet");
//           con.close();
                Thread.sleep(55000);
//                Thread.sleep(2000);
//                while(String.format("%02d:%02d",Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE)) != currTim){
//
//                    System.out.println(currTim+" ---- "+String.format("%02d:%02d",Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE)));
//                    Thread.sleep(5000);
//                }
            }
        } catch (Exception e) {System.out.println(e);}
    }
}