package com.teamc2.travellingsalesbee.gui;

import java.util.ArrayList;

import com.teamc2.travellingsalesbee.gui.elements.cells.Cell;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellFlower;

public class naiveStep {

	private Cell end;
	private ArrayList<Cell> available = new ArrayList<Cell>();
	private Cell start;

	public naiveStep(Cell start, ArrayList<Cell> available,Cell cell){
		this.start=start;
		this.available=available;
		this.end=cell;
	}
	
	public Cell getStart(){
		return start;
	}
	
	public ArrayList<Cell> getAvailable(){
		return this.available;
	}
	
	public Cell getEnd(){
		return end;
	}
	
}
