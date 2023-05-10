// Java program to create a text File using FileWriter

import java.io.FileWriter;
import java.io.IOException;

class GFG {
    public static void main(String[] args)
            throws IOException {
        // initialize a string
        String Tresc = "A";
        try {
            FileWriter FileWrite = new FileWriter("file.txt",true);
            FileWrite.write(Tresc);
            FileWrite.append("\r\n");
            System.out.println("Successfully written");
            // close the file
            FileWrite.close();
        } catch (Exception e) {
            System.out.println(e);
            e.getStackTrace();
        }
    }
}