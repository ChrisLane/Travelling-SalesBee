package com.teamc2.travellingsalesbee.algorithms.cost;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import java.io.*;
import java.util.ArrayList;

/**
 * Matrix storage of costs between cells
 *
 * @author Todd Waugh Ambridge (txw467)
 */
public class CostMatrix implements Serializable {

	private ArrayList<CostEntry> costMatrix;
	private ArrayList<Cell> cells1;
	private ArrayList<Cell> cells2;

	/**
	 * Create a new cost matrix
	 *
	 * @param map Map containing the cells for the matrix
	 */
	public CostMatrix(Map map) {
		costMatrix = new ArrayList<>();
		cells1 = new ArrayList<>();
		cells1.add(map.getOrigin());
		cells1.addAll(map.getNodes());
		cells2 = cells1;
		putAll();
	}

	/**
	 * Add all cells to the cost matrix
	 */
	public void putAll() {
		for (int i = 0; i < cells1.size(); i++) {
			for (int j = i; j < cells1.size(); j++) {
				Cell cell1 = cells1.get(i);
				Cell cell2 = cells2.get(j);
				put(cell1, cell2);
			}
		}
	}

	/**
	 * Add an entry for the distance between two cells
	 *
	 * @param cell1 The first cell in the entry
	 * @param cell2 The second cell in the entry
	 */
	public void put(Cell cell1, Cell cell2) {
		double cost = cell1.distance(cell2);
		put(cell1, cell2, cost);
	}

	/**
	 * Add an entry for the distance between two cells with a specific cost
	 *
	 * @param cell1 The first cell in the entry
	 * @param cell2 The second cell in the entry
	 * @param cost  The cost between the two cells
	 */
	public void put(Cell cell1, Cell cell2, double cost) {
		for (CostEntry entry : costMatrix) {
			if (entry.isKey(cell1, cell2)) {
				entry.setCost(cost);
				return;
			}
		}
		CostEntry entry = new CostEntry(cell1, cell2, cost);
		costMatrix.add(entry);
	}

	/**
	 * Get the cost between two cells
	 *
	 * @param cell1 The first cell
	 * @param cell2 The second cell
	 * @return The cost between the first and second cell
	 */
	public double getCost(Cell cell1, Cell cell2) {
		for (CostEntry entry : costMatrix) {
			if (entry.isKey(cell1, cell2)) {
				return entry.getCost();
			}
		}
		return Double.MAX_VALUE;
	}

	/**
	 * Get the pheromone level between two cells
	 *
	 * @param cell1 The first cell
	 * @param cell2 The second cell
	 * @return The pheromone level between the first and second cell
	 */
	public double getPheromone(Cell cell1, Cell cell2) {
		for (CostEntry entry : costMatrix) {
			if (entry.isKey(cell1, cell2)) {
				return entry.getPheromone();
			}
		}
		return 1;
	}

	/**
	 * Get the highest pheromone level in the matrix
	 *
	 * @return The highest pheromone level in the matrix
	 */
	public double getMaxPheromone() {
		double maxPheromone = 0;
		for (CostEntry entry : costMatrix) {
			double currentPheromone = entry.getPheromone();
			if (currentPheromone > maxPheromone) {
				maxPheromone = currentPheromone;
			}
		}
		return maxPheromone;
	}

	/**
	 * Get the mean pheromone level for the matrix
	 *
	 * @return The mean pheromone level for the matrix
	 */
	public double getMeanPheromone() {
		double totalPheromone = 0;
		int i = 0;
		for (CostEntry entry : costMatrix) {
			i++;
			totalPheromone += entry.getPheromone();
		}
		return totalPheromone / i;
	}

	/**
	 * Get the cost entry for two cells
	 *
	 * @param cell1 The first cell
	 * @param cell2 The second cell
	 * @return The cost entry for the first and second cell
	 */
	public CostEntry getEntry(Cell cell1, Cell cell2) {
		for (CostEntry entry : costMatrix) {
			if (entry.isKey(cell1, cell2)) {
				return entry;
			}
		}
		return null;
	}

	/**
	 * Get the cell with position (X, Y) from the matrix
	 *
	 * @param x The X position of the cell
	 * @param y The Y position of the cell
	 * @return The cell at position (X, Y)
	 */
	public Cell getCell(double x, double y) {
		for (Cell c : cells1) {
			if (c.x == x && c.y == y) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Copy a cost matrix object to a new cost matrix object with new instances of contained objects
	 *
	 * @returna A new cost matrix object with new instances of contained objects copied from the current cost matrix
	 */
	public CostMatrix copy() {
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream;
		try {
			objectOutputStream = new ObjectOutputStream(byteOutputStream);
			objectOutputStream.writeObject(this);
			objectOutputStream.flush();
			objectOutputStream.close();
			byteOutputStream.close();
			byte[] byteData = byteOutputStream.toByteArray();

			ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteData);
			return (CostMatrix) new ObjectInputStream(byteInputStream).readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Combines the current pheromone levels with a given cost matrix's pheromone levels
	 * Pheromone levels are set to the average of the levels in each matrix's respective entries
	 *
	 * @param updatedCloneMatrix
	 */
	public void combine(CostMatrix updatedCloneMatrix) {
		ArrayList<CostEntry> cloneList = updatedCloneMatrix.getMatrix();
		for (int i = 0; i < costMatrix.size(); i++) {
			CostEntry entry = costMatrix.get(i);
			CostEntry entry2 = cloneList.get(i);
			entry.setPheromone((entry.getPheromone() + entry2.getPheromone()) / 2);
		}
	}

	/**
	 * Get the cost matrix
	 *
	 * @return The cost matrix
	 */
	public ArrayList<CostEntry> getMatrix() {
		return costMatrix;
	}
}
