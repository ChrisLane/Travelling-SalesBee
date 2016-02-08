package com.teamc2.travellingsalesbee.gui.elements.cells;

import com.sun.javafx.geom.Point2D;

import java.awt.image.BufferedImage;

public class CellHive extends Point2D implements Cell {

	public CellHive(int x, int y) {
		super(x, y);
	}

	public CellType getType() {
		return CellType.HIVE;
	}

	public BufferedImage getImage() {
		return null;
	}
}