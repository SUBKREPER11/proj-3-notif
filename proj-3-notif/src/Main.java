import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;

public class Main {

    public static void main(String[] args) {

        int hour, min, day, month, year; // deklaracja zmiennych czasu jako Int
        String currTim, currDat; // deklaracja zmiennych godziny i daty jako String
        String link = "", message = ""; // deklaracja zmiennych "link" (odnośnik do strony - nieurzywany) i "message" (zmienna na wiadomość)
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png"); // deklaracja obrazka (Urzywane w Windows Tray Icon)
        try { // rozpoczęcie pętli programu

            Class.forName("com.mysql.cj.jdbc.Driver"); // Urzycie klasy mysql do łączności z bazą danych
            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.55.112/JavaTestDB","notif","notif"); // Deklaracja zmiennej "con" do łączenia się z bazą danych "JavaTestDB"na serwerze 'PORT IP' dla urzytkownika 'user' z chasłem 'password'
            Statement stmt=con.createStatement(); // rozpoczęcie łączności z bazą danych

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `JavaTestDB`.`Powiadomienia`" +
                    "( `id` INT AUTO_INCREMENT , \n" +
                    "    `title` VARCHAR(500) NOT NULL,\n" +
                    "    `text` VARCHAR(500) NOT NULL , \n" +
                    "    `time` VARCHAR(50) NOT NULL , \n" +
                    "    `date` VARCHAR(50) NOT NULL, \n" +
                    "    PRIMARY KEY (`id`)) \n" +
                    "    ENGINE = InnoDB;"); // Utworzenie tabeli `Powiadomienia` na serwerze gdyby ta nie istniała
            while(true){ //nieskończona pętla - rozpoczęcie pobierania danych powiadomień i daty

                Calendar date = Calendar.getInstance(); //pobranie obecnej daty
                hour = date.get(Calendar.HOUR_OF_DAY); //zapisanie obecnej godziny do zmiennej "hour"
                min = date.get(Calendar.MINUTE); //zapisanie obecnej minuty do zmiennej "min"
                currTim = String.format("%02d:%02d", hour, min); //stworzenie obecnej godziny w formacie "hh:mm" ze zmiennych "hour" (hh) i "min" (mm)
                day = date.get(Calendar.DAY_OF_MONTH); //zapisanie obecnego dnia do zmiennej "day"
                month = date.get(Calendar.MONTH) + 1; // zapisanie obecnego miesiąca do zmiennej "month" (dodanie 1 do wartości w celu otrzymania poprawnej wartości {nie wiadomo czemu tak jest})
                year = date.get(Calendar.YEAR); // zapisanie obecnego roku do zmiennej "year"

                currDat = String.format("%04d-%02d-%02d",year,month,day); //stworzenie obecnej daty w formacie "yyyy-MM-dd" ze zmiennych "year" (yyyy) "month" (MM) "day" (dd)
                System.err.println(currTim+" - "+currDat); // wyświetlenie obecnej godziny i daty w formacie "hh:mm - yyyy-MM-dd" - podglądowe w konsoli

                ResultSet rs=stmt.executeQuery("SELECT title, text, link FROM `Powiadomienia` WHERE date=\""+currDat+"\" AND time=\""+currTim+"\""); // wyszukiwanie w tabeli "Powiadomienia" powiadomienia o obecnej dacie (currDat) i godzinie (currTim)
                while (rs.next()) { //rozpoczęcie pętli wysyłania powiadomnień gdzy zostaną jakieś znalezione
                    if (rs.getString(2) == " ")
                        System.out.println(currTim);
                    else {
                        String os = System.getProperty("os.name"); // pobranie nazwy systemu
                        if (os.contains("Linux")) { // gdy system to Linux
                            System.out.println("Linux");
                            ProcessBuilder builder = new ProcessBuilder(
                                    "notify-send", rs.getString(1), rs.getString(2)+" \n "+rs.getString(3)); //stworzenie powiadomienia w systemie Linux gdzie "rs.String(1)" oznacza tytuł powiadomienia a "rs.String(2)" oznacza treść powiadomienia
                            builder.inheritIO().start(); //wysłanie gotowego powiadomienia
                        } else if (os.contains("Mac")) { // gdy system to MacOS
                            System.out.println("MacOS X");
                            String dir = System.getProperty("user.dir");
                            ProcessBuilder builder = new ProcessBuilder(
//                                    "osascript", "-e", "display notification \"" + rs.getString(2) + "\" with title \"" + rs.getString(1) + "\""); //stworzenie powiadomienia w systemie MacOS gdzie "rs.String(1)" oznacza tytuł powiadomienia a "rs.String(2)" oznacza treść powiadomienia
                                    dir+"/terminal-notifier.app/Contents/MacOS/terminal-notifier -title 'tyt' -message 'mag' -open 'https://www.google.com/'");
                                    builder.inheritIO().start(); //wysłanie gotowego powiadomienia (pojawia się błąd skutkujący brakiem powiadomienia)

                        } else if (os.contains("Windows")) {
                            System.out.println("Windows");
                            if(rs.getString(3)==null){
                                ProcessBuilder builder = new ProcessBuilder(
                                        "C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe","-Command","New-BurntToastNotification -Text '"+rs.getString(1)+"','"+rs.getString(2)+"'"
                                );
                                builder.inheritIO().start();
                            }else{
                                ProcessBuilder builder = new ProcessBuilder(
                                        "C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe","-Command","$button = New-BTButton -Content 'Zobacz więcej' -Arguments '"+rs.getString(3)+"'; New-BurntToastNotification -Text '"+rs.getString(1)+"','"+rs.getString(2)+"' -Button $button"
                                );
                                builder.inheritIO().start();
                            }
                        }
                        else if (SystemTray.isSupported()) { // gdy zasobnik systemowy jest wspierany (system to Windows)
                            SystemTray tray = SystemTray.getSystemTray(); // pobranie zasobnika systemowego

                            TrayIcon trayIcon = new TrayIcon(image, "proj-3-notif"); // ustalenie ikony w zasobniku systemowym o obrazku "image" i tytule "proj-3-notif"
                            trayIcon.setImageAutoSize(true); // ustalenie rozmiaru obrazka w zasobniku systemowym na automatyczny
                            tray.add(trayIcon); // dadanie ikony do do zasobnika systemowego
                            trayIcon.displayMessage(rs.getString(1), rs.getString(2), TrayIcon.MessageType.INFO); // wysłanie powiadomienia w systemie Windows gdzie "rs.String(1)" oznacza tytuł powiadomienia a "rs.String(2)" oznacza treść powiadomienia
                        }
                    }
                }
                Thread.sleep(55000); // odczekanie 55 sekund przed kolejnym pobraniem powiadomień (i ich wyświetleniem) i daty
            }
        } catch (Exception e) {System.out.println(e);}
    }
}