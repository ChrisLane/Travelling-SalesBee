package com.teamc2.travellingsalesbee.gui.elements.cells;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Cell extends Point2D.Double {

	public Cell(double x, double y) {
		super(x, y);
	}

	public abstract CellType getType();

	protected abstract BufferedImage getImage();

	public enum CellType {EMPTY, FLOWER, HIVE}
}
