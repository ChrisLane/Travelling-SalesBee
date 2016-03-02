package com.teamc2.travellingsalesbee.gui;

import java.util.ArrayList;

import com.teamc2.travellingsalesbee.algorithms.Comparison;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

public class ExperimentalStep {

	private SwapType type;
	private ArrayList<Cell> path;
	private Comparison<Cell, Cell> cellsCompared;

	public ExperimentalStep(Comparison<Cell,Cell> cellsCompared, ArrayList<Cell> path, SwapType type){
		this.cellsCompared = cellsCompared;
		this.path = path;
		this.type = type;
	}
	
	public Comparison<Cell, Cell> getCellsCompared(){
		return this.cellsCompared;
	}
	
	public ArrayList<Cell> getPath(){
		return this.path;
	}
	
	public SwapType getType(){
		return this.type;
	}
	
	
}
