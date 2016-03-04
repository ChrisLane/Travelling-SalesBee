package com.teamc2.travellingsalesbee.gui.data.cost;

public class Comparison<Cell1, Cell2> {
	private final Cell1 cell1;
	private final Cell2 cell2;
	
	/**
	 * 
	 * @param cell1. The first cell of the comparison.
	 * @param cell2. The second cell of the comparison.
	 */
	public Comparison(Cell1 cell1, Cell2 cell2){
		this.cell1 = cell1;
		this.cell2 = cell2;
	}
	
	/**
	 * 
	 * @return cell1. The first cell to compare the second one against
	 */
	public Cell1 getCell1(){
		return cell1;
	}
	
	/**
	 * 
	 * @return cell2. The second cell to compare the first one against
	 */
	public Cell2 getCell2(){
		return cell2;
	}
}
