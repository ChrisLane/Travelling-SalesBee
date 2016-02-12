package com.teamc2.travellingsalesbee.gui.elements.cells;

public class CellEmpty extends Cell {

	public CellEmpty(double x, double y) {
		super(x, y);
	}

	public CellType getType() {
		return CellType.EMPTY;
	}
}