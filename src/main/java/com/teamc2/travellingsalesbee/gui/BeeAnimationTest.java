package com.teamc2.travellingsalesbee.gui;

import com.teamc2.travellingsalesbee.gui.elements.cells.Cell;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BeeAnimationTest extends JPanel {
    private JPanel map;
    private ArrayList<Cell> list;
    int x = -100, y = 100;
    private int width, height;

    public BeeAnimationTest(JPanel map, ArrayList<Cell> list) {
        this.map = map;
        this.list = list;
        this.width = map.getWidth();
        this.height = map.getHeight();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        Font font = new Font("Tahoma", Font.BOLD + Font.PLAIN, 100);
        g2.setFont(font);
        g2.setColor(Color.red);
        g2.drawString("TEAM C2 ARE DA BEST IN DA WURLDZ", x, y);

        try {
            Thread.sleep(100);
        } catch(Exception e) {
            e.printStackTrace();
        }

        x+= 10;

        if(x > this.getWidth()) {
            x = -100;
        }

        repaint();
    }

   /* public static void main(String[] args) {
        JFrame jf = new JFrame("East");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(700, 200);
        jf.add(new BeeAnimationTest());
        jf.setVisible(true);
    }*/


}
