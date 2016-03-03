package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

public class CostEntry {

	private Cell first;
	private Cell second;
	private double cost;
	
	public CostEntry(Cell first, Cell second, double cost) {
		this.first = first;
		this.second = second;
		this.cost = cost;
	}
	
	public boolean isKey(Cell cell1, Cell cell2) {
		return ((first == cell1 && second == cell2) || (first == cell2 && second == cell1));
	}
	
	public double getCost() {
		return cost;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
}
