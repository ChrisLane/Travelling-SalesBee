package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.algorithms.cost.CostMatrix;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import com.teamc2.travellingsalesbee.gui.data.cells.CellNode;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Ant extends NearestNeighbour {

	private final double nearProbability = 0.7;
	private double heuristicCost;
	private CostMatrix costMatrix;

	public Ant(Map map) {
		super(map);
		costMatrix = map.getCostMatrix();
		naiveRun();
		setHeuristicCost(getCost());
		System.out.println(heuristicCost);
	}

	public void pheromoneRun() {
		if (!(hive == null)) {
			ArrayList<Cell> newPath = new ArrayList<>();
			ArrayList<CellNode> flowers = map.getFlowers();

			newPath.add(hive);

			// Loop over flowers missing from path
			Cell currentCell;
			Cell next = null;

			while (!flowers.isEmpty()) {
				currentCell = newPath.get(newPath.size() - 1);

				// Find the next flower to go to
				if (flowers.size() > 1) {
					next = findNextFlower(flowers, currentCell);
				} else {
					next = flowers.get(0);
				}
				
				//Remove the next flower from the set
				//Release some pheromone
				flowers.remove(next);
				newPath.add(next);
				plantPheromone(currentCell, next);
			}
			
			newPath.add(hive);
			plantPheromone(next, hive);

			double cost = calculatePathCost(newPath);
			setPath(newPath, cost);
			
			if (cost <= heuristicCost) {
				plantAllPheromone(path);
			}
		}
	}

	private Cell findNextFlower(ArrayList<CellNode> flowers, Cell currentCell) {
		double averageCost = getAverageCost(flowers,currentCell);
		ArrayList<CellNode> nearFlowers = nearFlowers(flowers,averageCost,currentCell);
		
		double averagePheromone = getAveragePheromone(flowers,currentCell);
		ArrayList<CellNode> goodFlowers = goodFlowers(nearFlowers,averagePheromone,currentCell);
		
		if (goodFlowers.size() > 0) {
			int r = ThreadLocalRandom.current().nextInt(0, goodFlowers.size());
			return goodFlowers.get(r);
		} else {
			int r = ThreadLocalRandom.current().nextInt(0, nearFlowers.size());
			return nearFlowers.get(r);
		}
	}

	private ArrayList<CellNode> goodFlowers(ArrayList<CellNode> flowers, double averagePheromone, Cell currentCell) {
		ArrayList<CellNode> goodFlowers = new ArrayList<>();
		for (CellNode n : flowers) {
			if (costMatrix.getPheromone(currentCell,n) >= averagePheromone) {
				goodFlowers.add(n);
			}
		}
		return goodFlowers;
	}
	
	private double getAveragePheromone(ArrayList<CellNode> flowers, Cell currentCell) {
		double totalPheromone = 0;
		for (CellNode n : flowers) {
			totalPheromone += costMatrix.getPheromone(currentCell,n);
		}
		return totalPheromone/flowers.size();
	}

	private ArrayList<CellNode> nearFlowers(ArrayList<CellNode> flowers, double averageCost, Cell currentCell) {
		ArrayList<CellNode> nearFlowers = new ArrayList<>();
		for (CellNode n : flowers) {
			if (costMatrix.getCost(currentCell,n) <= averageCost) {
				nearFlowers.add(n);
			}
		}
		return nearFlowers;
	}

	private double getAverageCost(ArrayList<CellNode> flowers, Cell currentCell) {
		double totalCost = 0;
		for (CellNode n : flowers) {
			totalCost += costMatrix.getCost(currentCell,n);
		}
		return totalCost/flowers.size();
	}

	private void setHeuristicCost(double cost) {
		heuristicCost = cost;
	}

	private void plantPheromone(Cell cell1, Cell cell2) {
		map.getCostMatrix().getEntry(cell1, cell2).plantPheromone();
	}

	private void plantAllPheromone(ArrayList<Cell> path) {
		for (int i = 0; i < path.size() - 1; i++) {
			Cell cell1 = path.get(i);
			Cell cell2 = path.get(i + 1);
			plantPheromone(cell1, cell2);
		}
	}
}
