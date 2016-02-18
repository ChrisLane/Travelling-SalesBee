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
	 * @param gridWidth The width of the gridmap.
	 * @param gridHeight The height of the gridmap.
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
			
			int x1, x2=0, y1, y2=0;
			int transparencyIncrement = Math.round(170/beePath.size());
			int transparency = 0;
			
			for(int i = 0; i < beePath.size()-1; i++) {
				
				x1 = (int) beePath.get(i).x;
				y1 = (int) beePath.get(i).y;
				x2 = (int) beePath.get(i+1).x;
				y2 = (int) beePath.get(i+1).y;
				Color lineColor = new Color(255, 255,0,75 + transparency);
				
				g2.setPaint(lineColor);
				g2.setStroke(new BasicStroke(5));
				g2.drawLine(x1+25, y1+25, x2+25, y2+25);
				String position = Integer.toString(i);
				g2.setPaint(Color.white);
				g2.setFont(new Font(null, Font.PLAIN, 15));
				g2.drawString(position, x1+(50/12), y1+(50/4));
				
				transparency += transparencyIncrement;
			}
			g2.setPaint(new Color(255, 255,0,75 + transparency+10));
			g2.setStroke(new BasicStroke(5));
			g2.drawLine(x2+(50/2), y2+(50/2), (int)beePath.get(0).x+(50/2), (int)beePath.get(0).y+(50/2));
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
