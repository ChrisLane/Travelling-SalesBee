package com.teamc2.travellingsalesbee.gui.elements.cells;

public class CellFlower implements Cell {
	
	private int x;
	private int y;
	
	public CellFlower(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	@Override
	public String getType() {
		return Cell.FLOWER;
	}

	@Override
	public String getImage() {
		//return flower image location here
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
