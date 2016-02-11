package com.teamc2.travellingsalesbee.gui.elements.cells;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Cell extends Point2D.Double {

	enum CellType {EMPTY, FLOWER, HIVE};
	
	public Cell(double x, double y) {
		super(x, y);
	}
	
	public BufferedImage getImage() {
		return null;
	}
}
