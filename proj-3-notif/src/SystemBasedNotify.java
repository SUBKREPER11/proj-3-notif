import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.Object;
public class SystemBasedNotify {
    public static void main(String[] args) throws IOException, AWTException {
        String title = "Title";
        String message = "Message";
        String link = "https://www.google.com/";
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

        String os = System.getProperty("os.name");
        if (os.contains("Linux")) {
            if(link != null){
                message += " " + link;
            }
            ProcessBuilder builder = new ProcessBuilder(
                    "notify-send", title, message);
            builder.inheritIO().start();
        } else if (os.contains("Mac")) {
            if(link != null){
                message +=" " + link;
            }
            ProcessBuilder builder = new ProcessBuilder(
                    "osascript", "-e",
                    "display notification \"" + message + "\""
                            + " with title \"" + title + "\"");
            builder.inheritIO().start();
        } else if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
            trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);
            if(link != null) {
                ActionListener al = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(link);
                    }
                };
                trayIcon.addActionListener(al);
            }
            trayIcon.displayMessage(title, message+" ", TrayIcon.MessageType.INFO);
        }
    }

}