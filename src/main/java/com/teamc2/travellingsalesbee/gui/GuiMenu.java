package com.teamc2.travellingsalesbee.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// main GUI class that will load the menu
// will replace GUI (which is currently being used by Melvyn to test main visualiser GUI)
public class GuiMenu extends JPanel {
	private BufferedImage image;

	public static void main(String[] args) throws InterruptedException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		JFrame frame = new JFrame("Travelling SalesBee");
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		GuiMenu gui = new GuiMenu();
		gui.run();

		frame.add(gui);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void run() {
		try {
			image = ImageIO.read(new File("target/classes/CartoonBees.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}
}
