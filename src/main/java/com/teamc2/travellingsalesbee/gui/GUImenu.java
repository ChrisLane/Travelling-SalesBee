package com.teamc2.travellingsalesbee.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

// main GUI class that will load the menu
// will replace GUI (which is currently being used by Melvyn to test main visualiser GUI)
public class GUImenu extends JPanel
{
	private Image img;

	public void run()
	{
		System.out.println(System.getProperty("user.dir"));

		setLayout(new BorderLayout());
		try {
			img = ImageIO.read(new File("target/classes/CartoonBees.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paint(Graphics g)
	{
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("Travelling SalesBee");
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GUImenu gui = new GUImenu();
		gui.run();
		gui.repaint();

		frame.add(gui);
		frame.setVisible(true);
	}
}
