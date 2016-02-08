package com.teamc2.travellingsalesbee.gui.elements.cells;

import java.awt.Point;

@SuppressWarnings("serial")
public class CellEmpty extends Point implements Cell {
	
	public CellEmpty(int x, int y){
		super(x,y);
	}
	
	public String getType() {
		return Cell.EMPTY;
	}

	public String getImage() {
		return null;
	}

}