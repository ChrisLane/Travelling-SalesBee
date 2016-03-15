package com.teamc2.travellingsalesbee.algorithms.cost;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import java.io.*;
import java.util.ArrayList;

public class CostMatrix implements Serializable {

	private ArrayList<CostEntry> costMatrix;
	private ArrayList<Cell> cells1;
	private ArrayList<Cell> cells2;

	public CostMatrix(Map map) {
		costMatrix = new ArrayList<>();
		cells1 = new ArrayList<>();
		cells1.add(map.getHive());
		cells1.addAll(map.getFlowers());
		cells2 = cells1;
		putAll();
	}

	public void putAll() {
		for (int i = 0; i < cells1.size(); i++) {
			for (int j = i; j < cells1.size(); j++) {
				Cell cell1 = cells1.get(i);
				Cell cell2 = cells2.get(j);
				put(cell1, cell2);
			}
		}
	}

	public void put(Cell cell1, Cell cell2) {
		double cost = cell1.distance(cell2);
		put(cell1, cell2, cost);
	}

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

	public double getCost(Cell cell1, Cell cell2) {
		for (CostEntry entry : costMatrix) {
			if (entry.isKey(cell1, cell2)) {
				return entry.getCost();
			}
		}
		return Double.MAX_VALUE;
	}

	public double getPheromone(Cell cell1, Cell cell2) {
		for (CostEntry entry : costMatrix) {
			if (entry.isKey(cell1, cell2)) {
				return entry.getPheromone();
			}
		}
		return 1;
	}

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
	
	public double getMeanPheromone() {
		double totalPheromone = 0;
		int i = 0;
		for (CostEntry entry : costMatrix) {
			i++;
			totalPheromone += entry.getPheromone();
		}
		return totalPheromone/i;
	}

	public CostEntry getEntry(Cell cell1, Cell cell2) {
		for (CostEntry entry : costMatrix) {
			if (entry.isKey(cell1, cell2)) {
				return entry;
			}
		}
		return null;
	}
	
	public Cell getCell(double x, double y) {
		for (Cell c : cells1) {
			if (c.x == x && c.y == y) {
				return c;
			}
		}
		return null;
	}

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
	
}
