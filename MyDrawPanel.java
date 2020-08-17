package com.company;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.ShortMessage;
import javax.swing.*;
import java.awt.*;

public class MyDrawPanel extends JPanel implements ControllerEventListener {
    boolean msg = false;

    @Override
    public void controlChange(ShortMessage event) {

        msg =true;
        repaint();
        System.out.println("yes");
    }

    @Override
    public void paintComponent(Graphics g) {
        if(msg){
            System.out.println("hi");
            Graphics2D g2 = (Graphics2D) g;
            int r = (int)(Math.random() *250);
            int gb = (int)(Math.random() *250);
            int b = (int)(Math.random() *250);

            g.setColor(new Color(r,gb,b));

            int ht = (int)((Math.random() *120) + 10);
            int wid = (int)((Math.random() *120) + 10);
            int x = (int)((Math.random() *40) + 10);
            int y = (int)((Math.random() *40) + 10);

            g.fillRect(x,y,ht,wid);
            msg= false;
        }
    }
}
