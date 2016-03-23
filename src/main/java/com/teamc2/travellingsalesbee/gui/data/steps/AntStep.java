package com.teamc2.travellingsalesbee.gui.data.steps;

import com.teamc2.travellingsalesbee.algorithms.cost.CostMatrix;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import java.util.ArrayList;
/**
 * A class to represent a single pheremone run from the ant algorithm
 * 
 * @author Bradley Rowe (bmr455)
 *
 */
public class AntStep {

	private ArrayList<Cell> path = new ArrayList<>();
	private CostMatrix costMatrix;

	/**
	 * Constructs a step of ant algorithm
	 * 
	 * @param path The path produced by the pheremone run
	 * @param costMatrix The cost matrix produced by the pheremone run
	 */
	public AntStep(ArrayList<Cell> path, CostMatrix costMatrix) {
		this.path = path;
		this.costMatrix = costMatrix;
	}

	/**
	 * 
	 * @return path The path stored in this step
	 */
	public ArrayList<Cell> getPath() {
		return path;
	}

	/**
	 * 
	 * @return costMatrix The cost matrix stored in this step
	 */
	public CostMatrix getCostMatrix() {
		return costMatrix;
	}
}
