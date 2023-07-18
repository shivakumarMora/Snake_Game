package SnakeGame;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    JFrame frame;

    Main(){
        frame=new JFrame();

        frame.setBounds(10,10,905,700);
        frame.setResizable(false);

        GamePannel panel= new GamePannel();
        panel.setBackground(Color.DARK_GRAY);
        frame.add(panel);

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        frame.setVisible(true);

    }
    public static void main(String[] args) {

        Main f=new Main();
    }


}
