package com.teamc2.travellingsalesbee.gui.data.steps;

import com.teamc2.travellingsalesbee.algorithms.cost.CostMatrix;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import java.util.ArrayList;

/**
 * A class to represent a single pheromone run from the ant algorithm
 *
 * @author Bradley Rowe (bmr455)
 */
public class AntStep {

	private ArrayList<Cell> path = new ArrayList<>();
	private CostMatrix costMatrix;

	/**
	 * Constructs a step of ant algorithm
	 *
	 * @param path       The path produced by the pheromone run
	 * @param costMatrix The cost matrix produced by the pheromone run
	 */
	public AntStep(ArrayList<Cell> path, CostMatrix costMatrix) {
		this.path = path;
		this.costMatrix = costMatrix;
	}

	/**
	 * Get the path of the step
	 *
	 * @return path The path stored in this step
	 */
	public ArrayList<Cell> getPath() {
		return path;
	}

	/**
	 * Get the cost matrix of the step
	 *
	 * @return costMatrix The cost matrix stored in this step
	 */
	public CostMatrix getCostMatrix() {
		return costMatrix;
	}
}
