package com.teamc2.travellingsalesbee.gui.elements.cells;

public class CellEmpty implements Cell {
	@Override
	public String getType() {
		return Cell.EMPTY;
	}

	@Override
	public String getImage() {
		return null;
	}
}
