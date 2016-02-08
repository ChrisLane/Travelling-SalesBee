package com.teamc2.travellingsalesbee;

import com.teamc2.travellingsalesbee.gui.elements.Map;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellFlower;

import java.util.ArrayList;

public class CostMatrix {

	private Map map;
	private double[][] costMatrix;

	/**
	 * Method to create object of CostMatrix
	 *
	 * @param map The map for which to create a cost matrix on.
	 */
	public CostMatrix(Map map) {
		this.map = map;
		setCostMatrix();
	}


	/**
	 * Method to put the path costs of the given grid map into a matrix
	 */
	private void setCostMatrix() {
		ArrayList<CellFlower> flowers = map.getFlowers();
		int totalFlowers = flowers.size();
		double[][] costMatrix = new double[totalFlowers][totalFlowers];

		//Loop to cycle through each of the flower nodes and calculate distance to each of
		//the other flower nodes
		//	Distance is calculated using Euclidean distance and stored as a double for more accuracy
		for (int i = 0; i < totalFlowers; i++) {
			for (int j = 0; j < totalFlowers; j++) {
				if (i == j) {
					costMatrix[i][j] = 0;
				} else {
					costMatrix[i][j] = flowers.get(i).distance(flowers.get(j));
				}
			}
		}

		this.costMatrix = costMatrix;
	}

	/**
	 * Method to return the costMatrix of a given map
	 *
	 * @return costMatrix. The costMatrix of the instantiated map.
	 */
	public double[][] getCostMatrix() {
		return costMatrix;
	}
}
