package com.teamc2.travellingsalesbee.gui;

import java.util.ArrayList;

import com.teamc2.travellingsalesbee.gui.elements.cells.Cell;

public class NaiveStep {

	private Cell end;
	private ArrayList<Cell> available = new ArrayList<Cell>();
	private Cell start;

	public NaiveStep(Cell start, ArrayList<Cell> available, Cell cell){
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
