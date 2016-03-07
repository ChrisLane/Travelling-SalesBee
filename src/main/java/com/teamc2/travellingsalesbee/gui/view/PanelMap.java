package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import com.teamc2.travellingsalesbee.gui.data.cells.CellDraggable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class PanelMap extends JPanel {
	private final int cellWidth;
	private final int cellHeight;
	private ComponentPath componentPath;
	private Dimension panelSize = null;
	private PanelAnimalAnimation panelAnimation;
	private Map map;
	private AlgorithmType type;

	/**
	 * Create the map panel
	 *
	 * @param cellWidth  Width of the grid sections
	 * @param cellHeight Height of the grid sections
	 */
	public PanelMap(int cellWidth, int cellHeight) {
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;

		// Create the map we're visualising
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		map = new Map(screenWidth, screenHeight);


		componentPath = new ComponentPath();
		add(componentPath);

		ComponentGrid componentGrid = new ComponentGrid(cellWidth, cellHeight);
		add(componentGrid);

		//Initialise and set bounds
		panelAnimation = new PanelAnimalAnimation(789, 446);
		panelAnimation.setBounds(this.getX(), this.getY(), 789, 446);

		this.add(panelAnimation); //Add to panel map

		setComponentZOrder(panelAnimation, 0);

		//Pass the bee path to the animation class
		ArrayList<Cell> path = this.getPathComponent().getBeePath();
		if (path.size() > 0) {
			panelAnimation.setPath(path);
		}

		setLayout(null);
	}

	/**
	 * Paint the grass texture onto the map and the grid
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		//Allowing for the correct background to be printed
		try {
			BufferedImage img = null;
			switch (this.type) {
				case BEE:
					img = ImageIO.read(this.getClass().getResource("/assets/backgrounds/Grass.jpg"));
					break;
				case ANT:
					img = ImageIO.read(this.getClass().getResource("/assets/backgrounds/Dirt.jpg"));
					break;
				case NEARESTNEIGHBOUR:
					img = ImageIO.read(this.getClass().getResource("/assets/backgrounds/Parchment.jpg"));
					break;
				case TWOOPT:
					img = ImageIO.read(this.getClass().getResource("/assets/backgrounds/Parchment.jpg"));
					break;
			}
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

	public ComponentPath getPathComponent() {
		return componentPath;
	}

	public Dimension getSize() {
		return panelSize;
	}

	public void setSize(Dimension panelSize) {
		this.panelSize = panelSize;
	}

	public PanelAnimalAnimation getPanelAnimalAnimation() {
		return this.panelAnimation;
	}

	public Map getMap() {
		return map;
	}

	/**
	 * @param type The type of algorithm currently being viewed to adjust the cell images on
	 *             the panelMap accordingly
	 */
	public void setAlgorithmType(AlgorithmType type) {
		this.type = type;

		for (Component c : this.getComponents()) {
			if (c instanceof CellDraggable) {
				((CellDraggable) c).setAlgorithmType(type);
				((CellDraggable) c).setImage(((CellDraggable) c).getType());
			}
		}
	}

}
