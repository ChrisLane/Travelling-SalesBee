package com.teamc2.travellingsalesbee.algorithms.cost;

import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import java.io.Serializable;

/**
 * Stores costs between two cells
 *
 * @author Todd Waugh Ambridge (txw467)
 */
public class CostEntry implements Serializable {

	private Cell first;
	private Cell second;
	private double cost;
	private double pheromone = 1;

	/**
	 * Create a new cost entry
	 *
	 * @param first  The first cell in the cost entry
	 * @param second The second cell in the cost entry
	 * @param cost   The cost between the first and second cells
	 */
	public CostEntry(Cell first, Cell second, double cost) {
		this.first = first;
		this.second = second;
		this.cost = cost;
	}

	/**
	 * Checks whether the two given cells match the cost entry
	 *
	 * @param cell1 The first cell to check
	 * @param cell2 The second cell to check
	 * @return If the cells are the same as in this cost entry
	 */
	public boolean isKey(Cell cell1, Cell cell2) {
		return ((first == cell1 && second == cell2) || (first == cell2 && second == cell1));
	}

	/**
	 * Get the cost between the first and second cell
	 *
	 * @return The cost between the first and second cell
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Set the cost between the first and second cell
	 *
	 * @param cost The cost to set between the first and second cell
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Increase the pheromone level between the first and second cell by 1
	 */
	public void plantPheromone() {
		pheromone++;
	}

	/**
	 * Get the level of pheromone between the first and second cell
	 *
	 * @return The level of pheromone between the first and second cell
	 */
	public double getPheromone() {
		return pheromone;
	}

	/**
	 * Set the pheromone level between the first and second cell
	 *
	 * @param pheromone The pheromone level to set between the first and second cell
	 */
	public void setPheromone(double pheromone) {
		this.pheromone = pheromone;
	}
}
