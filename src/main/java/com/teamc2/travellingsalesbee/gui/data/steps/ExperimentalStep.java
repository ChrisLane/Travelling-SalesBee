package com.teamc2.travellingsalesbee.gui.data.steps;

import com.teamc2.travellingsalesbee.algorithms.cost.Comparison;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import java.util.ArrayList;
/**
 * A class to represent one part of a single experimental run
 * 
 * For visualisation purposes three Experimental steps make up a single experimental run
 * An initial BEST Experimental step
 * An INSPECTED new Experimental step
 * A Resulting ACCEPTED/REJECTED step
 * 
 * @author Bradley Rowe (bmr455)
 *
 */
public class ExperimentalStep {

	private SwapType type;
	private ArrayList<Cell> path;
	private Comparison<Cell, Cell> cellsCompared;
	private double pathCost;

	/**
	 * Construct an Experimental step (used for bee algorithm)
	 * @param cellsCompared The cells that are being swapped
	 * @param path The path produced by the step
	 * @param pathCost The cost of the path
	 * @param type The type of step it is (BEST | INSPECTED | ACCEPTED | REJECTED)
	 */
	public ExperimentalStep(Comparison<Cell, Cell> cellsCompared, ArrayList<Cell> path, double pathCost, SwapType type) {
		this.cellsCompared = cellsCompared;
		this.path = path;
		this.pathCost = pathCost;
		this.type = type;
	}
	
	/**
	 * 
	 * @return cellsCompared The cells that are being swapped in the given step
	 */
	public Comparison<Cell, Cell> getCellsCompared() {
		return cellsCompared;
	}

	/**
	 * 
	 * @return path The path of the step
	 */
	public ArrayList<Cell> getPath() {
		return path;
	}
	
	/**
	 * 
	 * @return pathCost The cost of the path of the step
	 */
	public double getPathCost() {
		return pathCost;
	}

	/**
	 * 
	 * @return type The type of the given step using SwapType (BEST | INSPECTED | ACCEPTED | REJECTED)
	 */
	public SwapType getType() {
		return type;
	}
}
