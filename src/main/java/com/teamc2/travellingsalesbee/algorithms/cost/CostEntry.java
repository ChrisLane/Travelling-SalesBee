package com.teamc2.travellingsalesbee.algorithms.cost;

import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

public class CostEntry {

	private Cell first;
	private Cell second;
	private double cost;
	private double pheromone = 1;

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

	public void plantPheromone(double pheromone) {
		setPheromone(this.pheromone * pheromone);
	}

	public double getPheromone() {
		return pheromone;
	}

	public void setPheromone(double pheromone) {
		this.pheromone = pheromone;
	}

}
