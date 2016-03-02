package com.teamc2.travellingsalesbee.algorithms;

public class Comparison<Cell1, Cell2> {
	private final Cell1 cell1;
	private final Cell2 cell2;
	
	public Comparison(Cell1 cell1, Cell2 cell2){
		this.cell1 = cell1;
		this.cell2 = cell2;
	}
	
	public Cell1 getCell1(){
		return cell1;
	}
	
	public Cell2 getCell2(){
		return cell2;
	}
}
