package com.teamc2.travellingsalesbee.gui.view.map;

import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.CellDraggable;
import com.teamc2.travellingsalesbee.gui.data.cells.CellType;
import com.teamc2.travellingsalesbee.gui.view.GuiContainer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PanelMap extends JPanel {

	public final static String name = "PanelMap";
	private final int cellWidth;
	private final int cellHeight;
	private ComponentPath componentPath;
	private PanelAnimalAnimation panelAnimation;
	private PanelOverlyingText panelOverlyingText;
	private Map map;
	private AlgorithmType type;
	private int screenWidth;
	private int screenHeight;

	/**
	 * Create the map panel
	 *
	 * @param cellWidth  Width of the grid sections
	 * @param cellHeight Height of the grid sections
	 */
	public PanelMap(int cellWidth, int cellHeight) {

		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;

		setName(name);

		// Create the map we're visualising
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.width;
		screenHeight = screenSize.height;
		map = new Map();


		ComponentGrid componentGrid = new ComponentGrid(cellWidth, cellHeight);
		add(componentGrid);

		componentPath = new ComponentPath(AlgorithmType.BEE);
		add(componentPath);
		
		
		//Initialise and set bounds
		panelAnimation = new PanelAnimalAnimation(screenWidth, screenHeight);
		panelAnimation.setBounds(getX(), getY(), screenWidth, screenHeight);
		add(panelAnimation); //Add to panel map

		panelOverlyingText = new PanelOverlyingText(screenWidth, screenHeight);
		panelOverlyingText.setBounds(getX(), getY(), screenWidth, screenHeight);
		add(panelOverlyingText);

		setComponentZOrder(panelOverlyingText,2);
		setComponentZOrder(panelAnimation, 1);
		setLayout(null);
		
	}

	/**
	 * Paint the grass texture onto the map and the grid
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		Point2D center = new Point2D.Float(getWidth()/2, 0);
		float radius = getWidth()/2;
		Point2D focus = new Point2D.Float((getWidth()/2), 1000);
		float[] dist = {0.0f, 0.3f, 1.0f};
		Color[] colors = {new Color(71, 35, 35), Color.WHITE, new Color(71, 35, 35)};
		RadialGradientPaint p = new RadialGradientPaint(center, radius, focus, dist, colors, MultipleGradientPaint.CycleMethod.REFLECT);

		// Set the location of the panelOverlyingText text. We take into account the tab and toolbox panel widths.
		GuiContainer guiContainer = (GuiContainer) getRootPane();
		int tabsWidth = guiContainer.getComponentTabs().getWidth();
		int toolboxWidth = guiContainer.getPanelToolbox().getWidth();
		panelOverlyingText.setTextXandY((getWidth() / 2) - tabsWidth - toolboxWidth, 13);

		//GradientPaint redtowhite = new GradientPaint(0, 50, new Color(71, 35, 35), 0, 0, new Color(134, 93, 93));
		//this.setBackground(new Color(71, 35, 35));

		g2.setPaint(p);
		g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 0, 0));
		g2.setPaint(Color.black);

		//Allowing for the correct background to be printed
		try {

			BufferedImage img = null;

			TexturePaint paint;

			switch (type) {
			case BEE:
				img = ImageIO.read(getClass().getResource("/assets/backgrounds/Grass.jpg"));
				break;
			case ANT:
				img = ImageIO.read(getClass().getResource("/assets/backgrounds/Dirt.jpg"));
				break;
			case NEARESTNEIGHBOUR:
				img = ImageIO.read(getClass().getResource("/assets/backgrounds/Parchment.jpg"));
				break;
			case TWOOPT:
				img = ImageIO.read(getClass().getResource("/assets/backgrounds/Parchment.jpg"));
				break;
			}

			paint = new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
			g2.setPaint(paint);
			g2.fill(new Rectangle(0, 50, getWidth(), getHeight()));
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

	public PanelAnimalAnimation getPanelAnimalAnimation() {
		return panelAnimation;
	}

	public PanelOverlyingText getPanelOverlyingText() { return panelOverlyingText; }

	public Map getMap() {
		return map;
	}

	/**
	 * @param type The type of algorithm currently being viewed to adjust the cell images on
	 *             the panelMap accordingly
	 */
	public void setAlgorithmType(AlgorithmType type) {
		this.type = type;

		for (Component c : getComponents()) {
			if (c instanceof CellDraggable) {
				((CellDraggable) c).setAlgorithmType(type);
				((CellDraggable) c).setImage(((CellDraggable) c).getType());
			}
		}
	}

	public PanelAnimalAnimation getAnimation() {
		return panelAnimation;
	}

	public AlgorithmType getAlgorithmType(){
		return type;
	}

	public void cellFull(int x, int y) {

		for (Component c : getComponents()) {
			if (c instanceof CellDraggable) {
				if (c.isEnabled() && c.getBounds().x == x && c.getBounds().y == y) {
					remove(c);
					c.setEnabled(false);
				}
			}
		}

	}

	/**
	 * Set the hive for the panelMap
	 */
	public void deleteOldHive() {
		for (Component c : getComponents()) {
			if (c instanceof CellDraggable) {
				if (c.isEnabled() && ((CellDraggable) c).getType().equals(CellType.ORIGIN)) {
					remove(c);
					c.setEnabled(false);
				}
			}
		}
	}

	public void clear() {
		getPathComponent().setStepNum(-1);
		//getPanelAnimalAnimation().setStepNum(-1);
		for (Component c : getComponents()) {
			if (c instanceof CellDraggable) {
				int x = c.getX();
				int y = c.getY();
				remove(c);
				c.setEnabled(false);
				map.setCell(x,y,CellType.EMPTY);
			}
		}
	}
}
