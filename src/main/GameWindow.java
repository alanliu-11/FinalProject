package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow {
    private JFrame jframe;
    public GameWindow(GamePanel gamePanel){
        jframe = new JFrame();
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setUndecorated(true);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        jframe.setVisible(true);
        //System.out.println(jframe.getHeight());
        //System.out.print(jframe.getWidth());
    }
    public int width(){
        return jframe.getSize().width;
    }
    public int length(){
        return jframe.getSize().width;
    }
}
