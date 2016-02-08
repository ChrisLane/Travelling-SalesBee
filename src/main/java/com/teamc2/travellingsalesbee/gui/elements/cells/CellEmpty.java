package com.teamc2.travellingsalesbee.gui.elements.cells;

import com.sun.javafx.geom.Point2D;

@SuppressWarnings("serial")
public class CellEmpty extends Point2D implements Cell {

	public CellEmpty(int x, int y) {
		super(x, y);
	}

	public String getType() {
		return Cell.EMPTY;
	}

	public String getImage() {
		return null;
	}

}