package com.teamc2.travellingsalesbee.gui.elements;

import com.teamc2.travellingsalesbee.GridLine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PanelMap extends JPanel {
	private int width;
	private int height;

	public PanelMap(int width, int height) {
		this.width = width;
		this.height = height;

		genGrid();
		setLayout(null);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			Graphics2D g2 = (Graphics2D) g;
			BufferedImage img = ImageIO.read(new File("target/classes/backgrounds/Grass.jpg"));
			TexturePaint paint = new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
			g2.setPaint(paint);
			g2.fill(new Rectangle(0, 0, getWidth(), getHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void genGrid() {
		int widthCount = 0; //Keeps track of current horizontal line we're drawing
		int heightCount = 0;//Keeps track of current vertical we're drawing

		//Gets the width and height of the users screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		//Custom translucent colour
		Color lineColor = new Color(255, 255, 255, 65);

		//While the widthCount is less than the width of the users screen, draw horizontal lines
		while (widthCount < screenWidth) {
			GridLine gridLine = new GridLine(widthCount, 0, widthCount, Integer.MAX_VALUE);
			gridLine.setBackground(lineColor);
			gridLine.setBounds(widthCount, 0, 3, Integer.MAX_VALUE);
			add(gridLine);

			widthCount += width;
		}

		//While the heightCount is less than the height of the users screen, draw vertical lines
		while (heightCount < screenHeight) {
			GridLine gridLine = new GridLine(0, heightCount, Integer.MAX_VALUE, heightCount);
			gridLine.setBackground(lineColor);
			gridLine.setBounds(0, heightCount, Integer.MAX_VALUE, 3);
			add(gridLine);

			heightCount += height;
		}
	}
}
