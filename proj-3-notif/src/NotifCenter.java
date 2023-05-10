import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NotifCenter implements ActionListener{
    JFrame box = new JFrame();
    NotifCenter(){
        JButton btn1 = new JButton("SaveFile");
        btn1.addActionListener(this);
        box.add(btn1);
        box.setTitle("Notification Center");
        box.setSize(800,600);
        box.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        box.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("story.txt"));
            writer.newLine();
            writer.write("test");
            writer.append("test");
            writer.close();
        }catch (IOException ee){
            ee.printStackTrace();
        }
    }
    public static void main(String[] args) {new NotifCenter();}
}
