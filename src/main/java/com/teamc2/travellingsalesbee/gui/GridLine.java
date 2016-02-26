package com.teamc2.travellingsalesbee.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
/*
 * 	Class to create a single grid line to display on the gridmap
 * 	
 * 	extends JPanel so it can be added to the grid map as a child component
 */
public class GridLine extends JPanel {

	private final float x1;
	private final float y1;
	private final float x2;
	private final float y2;

	/**
	 * Create a new line
	 *
	 * @param x1. The x position of line start
	 * @param y1. The y position of line start
	 * @param x2. The x position of line end
	 * @param y2. The y position of line end
	 */
	public GridLine(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}

	/**
	 * Draw the line
	 */
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(10));
		g2.draw(new Line2D.Float(x1, y1, x2, y2));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(300, 300);
	}
}