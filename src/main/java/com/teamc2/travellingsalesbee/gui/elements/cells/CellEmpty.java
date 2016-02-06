package com.teamc2.travellingsalesbee.gui.elements.cells;

public class CellEmpty implements Cell {
	
	private int x;
	private int y;
	
	public CellEmpty(int x, int y){
		this.x=x;
		this.y=y;
	}
	@Override
	public String getType() {
		return Cell.EMPTY;
	}

	@Override
	public String getImage() {
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
