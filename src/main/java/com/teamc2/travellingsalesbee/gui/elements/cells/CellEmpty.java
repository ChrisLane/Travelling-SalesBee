package com.teamc2.travellingsalesbee.gui.elements.cells;

import java.awt.image.BufferedImage;

public class CellEmpty extends Cell {

	public CellEmpty(double x, double y) {
		super(x, y);
	}

	public CellType getType() {
		return CellType.EMPTY;
	}

	public BufferedImage getImage() {
		return null;
	}
}