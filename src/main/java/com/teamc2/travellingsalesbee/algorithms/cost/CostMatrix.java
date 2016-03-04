package com.teamc2.travellingsalesbee.algorithms.cost;

import java.util.ArrayList;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

public class CostMatrix {

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
		for (int i=0; i<cells1.size(); i++) {
			for (int j=i; j<cells1.size(); j++) {
				Cell cell1 = cells1.get(i);
				Cell cell2 = cells2.get(j);
				put(cell1,cell2);
			}
		}
	}
	
	public void put(Cell cell1, Cell cell2) {
		double cost = cell1.distance(cell2);
		put(cell1,cell2,cost);
	}
	
	public void put(Cell cell1, Cell cell2, double cost) {
		for (CostEntry entry : costMatrix) {
			if (entry.isKey(cell1,cell2)) {
				entry.setCost(cost);
				return;
			}
		}
		CostEntry entry = new CostEntry(cell1, cell2, cost);
		costMatrix.add(entry);
	}
	
	public double getCost(Cell cell1, Cell cell2) {
		for (CostEntry entry : costMatrix) {
			if (entry.isKey(cell1,cell2)) {
				return entry.getCost();
			}
		}
		return Double.MAX_VALUE;
	}
	
	public double getPheremone(Cell cell1, Cell cell2) {
		for (CostEntry entry : costMatrix) {
			if (entry.isKey(cell1,cell2)) {
				return entry.getPheremone();
			}
		}
		return 1;
	}

	public CostEntry getEntry(Cell cell1, Cell cell2) {
		for (CostEntry entry : costMatrix) {
			if (entry.isKey(cell1,cell2)) {
				return entry;
			}
		}
		return null;
	}
}
