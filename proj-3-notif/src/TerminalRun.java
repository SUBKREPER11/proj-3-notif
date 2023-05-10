import java.io.IOException;

public class TerminalRun {
    public static void main(String[] args) {
        String dir = System.getProperty("dir");
        System.out.println("Location: "+dir+"/terminal-notifier.app/Contents/MacOS/terminal-notifier -title 'tyt' -message 'mag' -open 'https://www.google.com/'");
    ProcessBuilder builder = new ProcessBuilder(dir+"/terminal-notifier.app/Contents/MacOS/terminal-notifier -title 'tyt' -message 'mag' -open 'https://www.google.com/'");
        try {
            builder.inheritIO().start();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
