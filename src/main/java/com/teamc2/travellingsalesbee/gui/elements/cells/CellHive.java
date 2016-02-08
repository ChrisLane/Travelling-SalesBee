package com.teamc2.travellingsalesbee.gui.elements.cells;

import java.awt.Point;

@SuppressWarnings("serial")
public class CellHive extends Point implements Cell {
	
	public CellHive(int x, int y){
		super(x,y);
	}
	
	public String getType() {
		return Cell.HIVE;
	}

	public String getImage() {
		//return hive image location here
		return null;
	}

}