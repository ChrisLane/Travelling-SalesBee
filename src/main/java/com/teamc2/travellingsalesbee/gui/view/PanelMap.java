package com.teamc2.travellingsalesbee.gui.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PanelMap extends JPanel {
	private final int cellWidth;
	private final int cellHeight;
	private ComponentPath componentPath;

	/**
	 * Create the map panel
	 *
	 * @param cellWidth  Width of the grid sections
	 * @param cellHeight Height of the grid sections
	 */
	public PanelMap(int cellWidth, int cellHeight) {
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;

		componentPath = new ComponentPath();
		add(componentPath);

		genGrid();
		setLayout(null);

		//this.add(panelAnimation);

		System.out.println("this.getWidth: " + this.getWidth() + ", this.getHeight: " + this.getHeight());
		PanelAnimalAnimation panelAnimation = new PanelAnimalAnimation(0, 0, 500, 500);
		panelAnimation.setBounds(0, 0, 500, 500);
		add(panelAnimation);
		setComponentZOrder(panelAnimation, 0);

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
	}

	/**
	 * Return the height of grid sections
	 *
	 * @return Height of grid sections
	 */
	public int getCellHeight() {
		return cellHeight;
	}

	/**
	 * Return the width of grid sections
	 *
	 * @return Width of grid sections
	 */
	public int getCellWidth() {
		return cellWidth;
	}


	/**
	 * Generate the grid lines that together form the grid.
	 */
	public void genGrid() {
		ComponentGrid componentGrid = new ComponentGrid(cellWidth, cellHeight);
		add(componentGrid);
	}

	public ComponentPath getPathComponent() {
		return componentPath;
	}
}
