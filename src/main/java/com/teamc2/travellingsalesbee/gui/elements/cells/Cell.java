package com.teamc2.travellingsalesbee.gui.elements.cells;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Cell extends Point2D.Double {

	public enum CellType {EMPTY, FLOWER, HIVE};
	
	public Cell(double x, double y) {
		super(x, y);
	}
	
	protected abstract CellType getType();
	
	protected abstract BufferedImage getImage();
}
