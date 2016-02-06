package com.teamc2.travellingsalesbee.gui.elements.cells;

public class CellHive implements Cell {
	
	private int x;
	private int y;
	
	public CellHive(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	@Override
	public String getType() {
		return Cell.Hive;
	}

	@Override
	public String getImage() {
		//return hive image location here
		return null;
	}
	
	@Override
	public int getX() {
		return this.x;
	}
	@Override
	public int getY() {
		return this.y;
	}
}
