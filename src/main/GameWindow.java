package main;
import javax.swing.*;
import java.awt.*;

public class GameWindow {
    public GameWindow(GamePanel gamePanel){
        JFrame jframe;
        jframe = new JFrame();
        jframe.setFocusable(true);
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setUndecorated(true);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        jframe.setVisible(true);
        jframe.requestFocus();
    }
}
