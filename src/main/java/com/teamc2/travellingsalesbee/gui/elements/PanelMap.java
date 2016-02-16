package com.teamc2.travellingsalesbee.gui.elements;

import com.teamc2.travellingsalesbee.GridLine;
import com.teamc2.travellingsalesbee.gui.elements.cells.Cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;


public class PanelMap extends JPanel {
	private final int gridWidth;
	private final int gridHeight;
	
	private ArrayList<Cell> beePath = new ArrayList<Cell>();

	/**
	 * PanelMap. The gridmap 
	 * @param gridWidth. The width of the gridmap.
	 * @param gridHeight. The height of the gridmap.
	 */
	public PanelMap(int gridWidth, int gridHeight) {
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;

		genGrid();
		setLayout(null);
	}

	/**
	 * Method to paint the grass texture onto the back of the gridmap.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		try {			
			BufferedImage img = ImageIO.read(new File("target/classes/backgrounds/Grass.jpg"));
			TexturePaint paint = new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
			g2.setPaint(paint);
			g2.fill(new Rectangle(0, 0, getWidth(), getHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(beePath.size() > 0) {
			g2.setPaint(Color.yellow);
			g2.setStroke(new BasicStroke(5));
			g2.drawLine(0, 0, 500, 500);
		}
	}

	/**
	 * 
	 * @return gridHeight. The height of the grid.
	 */
	public int getGridHeight() {
		return gridHeight;
	}
	
	/**
	 * 
	 * @return gridWidth. The width of the grid.
	 */
	public int getGridWidth() {
		return gridWidth;
	}
	
	public void setPath(ArrayList<Cell> path) {
		this.beePath = path;
		
		this.repaint();
	}

	/**
	 * Method to create the gridlines that together form the grid.
	 */
	public void genGrid() {
		int widthCount = 0; //Keeps track of current horizontal line we're drawing
		int heightCount = 0;//Keeps track of current vertical we're drawing

		//Gets the gridWidth and gridHeight of the users screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		//Custom translucent colour
		Color lineColor = new Color(255, 255, 255, 65);

		//While the widthCount is less than the gridWidth of the users screen, draw vertical lines
		while (widthCount < screenWidth) {
			GridLine gridLine = new GridLine(widthCount, 0, widthCount, screenHeight);
			gridLine.setBackground(lineColor);
			gridLine.setBounds(widthCount, 0, 3, screenHeight);
			add(gridLine);

			widthCount += gridWidth;
		}

		//While the heightCount is less than the gridHeight of the users screen, draw horizontal lines
		while (heightCount < screenHeight) {
			GridLine gridLine = new GridLine(0, heightCount, screenWidth, heightCount);
			gridLine.setBackground(lineColor);
			gridLine.setBounds(0, heightCount, screenWidth, 3);
			add(gridLine);

			heightCount += gridHeight;
		}
	}
}
