package com.teamc2.travellingsalesbee.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class GridLine extends JPanel {

	private final float x1;
	private final float y1;
	private final float x2;
	private final float y2;

	/**
	 * Create a new line
	 *
	 * @param x1 X of position 1
	 * @param y1 Y of position 1
	 * @param x2 X of position 2
	 * @param y2 Y of position 2
	 */
	public GridLine(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}

	/**
	 * {@inheritDoc}
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