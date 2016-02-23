package com.teamc2.travellingsalesbee.gui.elements.cells;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Cell extends Point2D.Double {

	/**
	 * Create a new cell
	 *
	 * @param x X position of cell
	 * @param y Y position of cell
	 */
	public Cell(double x, double y) {
		super(x, y);
	}

	/**
	 * Get the type of the cell
	 *
	 * @return Type of the cell
	 */
	public abstract CellType getType();

	/**
	 * Get the cell image
	 *
	 * @return Cell image
	 */
	protected abstract BufferedImage getImage();

	public enum CellType {EMPTY, FLOWER, HIVE}
}