package com.teamc2.travellingsalesbee.gui.elements.cells;

import com.sun.javafx.geom.Point2D;

import java.awt.image.BufferedImage;

public class CellEmpty extends Point2D implements Cell {

	public CellEmpty(int x, int y) {
		super(x, y);
	}

	public CellType getType() {
		return CellType.EMPTY;
	}

	public BufferedImage getImage() {
		return null;
	}
}