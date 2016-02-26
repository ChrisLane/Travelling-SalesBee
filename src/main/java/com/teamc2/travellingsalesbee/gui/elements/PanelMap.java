package com.teamc2.travellingsalesbee.gui.elements;

import com.teamc2.travellingsalesbee.gui.GridLine;
import com.teamc2.travellingsalesbee.gui.naiveStep;
import com.teamc2.travellingsalesbee.gui.elements.cells.Cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


public class PanelMap extends JPanel {
	private final int gridWidth;
	private final int gridHeight;

	private ArrayList<Cell> beePath = new ArrayList<>();
	private ArrayList<naiveStep> naiveSteps = new ArrayList<>();

	private int beePosX = 0;
	private int beePosY = 0;

	/**
	 * Create the map panel
	 *
	 * @param gridWidth  Width of the grid sections
	 * @param gridHeight Height of the grid sections
	 */
	public PanelMap(int gridWidth, int gridHeight) {
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;

		genGrid();
		setLayout(null);
	}

	/**
	 * Paint the grass texture onto the map and the grid
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		try {
			BufferedImage img = ImageIO.read(this.getClass().getResource("/assets/backgrounds/Grass.jpg"));
			TexturePaint paint = new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
			g2.setPaint(paint);
			g2.fill(new Rectangle(0, 0, getWidth(), getHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (beePath.size() > 100) {

			int x1, x2 = 0, y1, y2 = 0;
			int transparencyIncrement = Math.round(170 / beePath.size());
			int transparency = 0;
			int lineThicknessIncrement = Math.round(4 / beePath.size());
			int lineThickness = 5;
			for (int i = 0; i < beePath.size() - 1; i++) {

				x1 = (int) beePath.get(i).x;
				y1 = (int) beePath.get(i).y;
				x2 = (int) beePath.get(i + 1).x;
				y2 = (int) beePath.get(i + 1).y;
				Color lineColor = new Color(255, 255, 0, 75 + transparency);

				g2.setPaint(lineColor);
				g2.setStroke(new BasicStroke(lineThickness));
				g2.drawLine(x1 + 25, y1 + 25, x2 + 25, y2 + 25);
				String position = Integer.toString(i);
				g2.setPaint(Color.white);
				g2.setFont(new Font(null, Font.PLAIN, 15));
				g2.drawString(position, x1 + (50 / 12), y1 + (50 / 4));

				lineThickness = lineThickness - lineThicknessIncrement;
				transparency += transparencyIncrement;
			}
			g2.setPaint(new Color(255, 255, 0, 75 + transparency + 10));
			g2.setStroke(new BasicStroke(5));
			g2.drawLine(x2 + (50 / 2), y2 + (50 / 2), (int) beePath.get(0).x + (50 / 2), (int) beePath.get(0).y + (50 / 2));

		} else if (naiveSteps.size() > 0){
			int x1, x2 = 0, y1, y2 = 0;


			for (int i = 0; i<naiveSteps.size();i++){
				naiveStep step = naiveSteps.get(i);
				System.out.println(step.getAvailable().size());
				x1 = (int) step.getStart().x;
				y1 = (int) step.getStart().y;

				ArrayList<Cell> available = step.getAvailable();
				System.out.println("naiveSteps.size() " + naiveSteps.size());
				System.out.println("available size " + available.size());
				for (int j=0;j<available.size();j++){
					g2.setStroke(new BasicStroke(5));
					g2.setPaint(Color.red);
					x2 = (int) available.get(j).x;
					y2 = (int) available.get(j).y;
					g2.drawLine(x1 + 25, y1 + 25, x2 + 25, y2 + 25);
				}
				g2.setStroke(new BasicStroke(6));
				g2.setPaint(Color.green);
				System.out.println("Green: " + step.getStart() + "->" + step.getEnd());
				x2 = (int) step.getEnd().x;
				y2 = (int) step.getEnd().y;
				g2.drawLine(x1 + 25, y1 + 25, x2 + 25, y2 + 25);
			}
		}

		/*
		final Thread thread = new Thread() {
			//drawBees(g2);
		}


					public class MyRunnable implements Runnable {

					private int var;

					public MyRunnable(int var) {
						this.var = var;
					}

					public void run() {
						// code in the other thread, can reference "var" variable
					}
					}
				*/
	}

	private void drawBees(Graphics g2) {
		System.out.println("Drawing Bees");
		Font font = new Font("Tahoma", Font.BOLD + Font.PLAIN, 100);
		g2.setFont(font);
		g2.setColor(Color.red);
		g2.drawString("TEAM C2 ARE DA BEST IN DA WURLDZ", beePosX, beePosY);

		System.out.println("Animation is running");

		try {
			Thread.sleep(100);
		} catch(Exception e) {
			e.printStackTrace();
		}

		beePosX += 10;
		beePosY += 10;

		if(beePosX > this.getWidth()) {
			beePosX = 0;
		}

		repaint();
	}

	/**
	 * Return the height of grid sections
	 *
	 * @return Height of grid sections
	 */
	public int getGridHeight() {
		return gridHeight;
	}

	/**
	 * Return the width of grid sections
	 *
	 * @return Width of grid sections
	 */
	public int getGridWidth() {
		return gridWidth;
	}

	/**
	 * Set the path in algorithms
	 *
	 * @param path Path to set in algorithms
	 */
	public void setPath(ArrayList<Cell> path) {
		this.beePath = path;
		this.repaint();
	}

	/**
	 * Generate the grid lines that together form the grid.
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


	//Naive visualisation
	public void setNaiveSteps(ArrayList<naiveStep> steps) {
		this.naiveSteps = steps;
		this.repaint();
	}
}
