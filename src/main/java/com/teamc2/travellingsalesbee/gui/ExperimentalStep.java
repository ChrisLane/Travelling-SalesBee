package com.teamc2.travellingsalesbee.gui;

import com.teamc2.travellingsalesbee.algorithms.cost.Comparison;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import java.util.ArrayList;

public class ExperimentalStep {

	private SwapType type;
	private ArrayList<Cell> path;
	private Comparison<Cell, Cell> cellsCompared;
	private double pathCost;

	public ExperimentalStep(Comparison<Cell, Cell> cellsCompared, ArrayList<Cell> path, double pathCost, SwapType type) {
		this.cellsCompared = cellsCompared;
		this.path = path;
		this.pathCost = pathCost;
		this.type = type;
	}

	public Comparison<Cell, Cell> getCellsCompared() {
		return cellsCompared;
	}

	public ArrayList<Cell> getPath() {
		return path;
	}

	public double getPathCost() {
		return pathCost;
	}

	public SwapType getType() {
		return type;
	}


}
