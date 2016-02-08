package com.teamc2.travellingsalesbee.gui.elements.cells;

import com.sun.javafx.geom.Point2D;

@SuppressWarnings("serial")
public class CellFlower extends Point2D implements Cell {

	public CellFlower(int x, int y) {
		super(x, y);
	}

	public String getType() {
		return Cell.FLOWER;
	}

	public String getImage() {
		//return flower image location here
		return null;
	}

}