package com.teamc2.travellingsalesbee.gui.elements.cells;

import java.awt.Point;

@SuppressWarnings("serial")
public class CellFlower extends Point implements Cell {
	
	public CellFlower(int x, int y){
		super(x,y);
	}
	
	public String getType() {
		return Cell.FLOWER;
	}

	public String getImage() {
		//return flower image location here
		return null;
	}

}