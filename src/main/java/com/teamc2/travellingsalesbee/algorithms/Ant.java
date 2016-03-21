package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.algorithms.cost.CostMatrix;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import com.teamc2.travellingsalesbee.gui.data.cells.CellNode;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Ant extends NearestNeighbour {

	private double heuristicCost;
	private CostMatrix costMatrix;

	public Ant(Map map) {
		super(map);
		costMatrix = map.getCostMatrix();
		naiveRun();
		setHeuristicCost(getCost());
	}

	public void pheromoneRun() {
		if (!(origin == null)) {
			ArrayList<Cell> newPath = new ArrayList<>();
			ArrayList<CellNode> nodes = map.getNodes();

			newPath.add(origin);

			// Loop over nodes missing from path
			Cell currentCell;
			CellNode next = null;

			while (!nodes.isEmpty()) {
				currentCell = newPath.get(newPath.size() - 1);

				// Find the next node to go to
				if (nodes.size() > 1) {
					next = findNextNode(nodes, currentCell);
				} else {
					next = nodes.get(0);
				}
				
				//Remove the next node from the set
				//Release some pheromone
				nodes.remove(next);
				newPath.add(next);
				plantPheromone(currentCell, next);
			}
			
			newPath.add(origin);
			plantPheromone(next, origin);

			double cost = calculatePathCost(newPath);
			setPath(newPath, cost);
			
			if (cost <= heuristicCost) {
				plantAllPheromone(path);
			}
		}
	}

	private CellNode findNextNode(ArrayList<CellNode> nodes, Cell currentCell) {
		double averagePheromone = getAveragePheromone(nodes,currentCell);
		ArrayList<CellNode> goodNodes = goodNodes(nodes,averagePheromone,currentCell);
		
		if (goodNodes.size() > 0) {
			nodes = goodNodes;
		}
		
		double averageCost = getAverageCost(nodes,currentCell);
		nodes = nearNodes(nodes,averageCost,currentCell);
		
		int r = ThreadLocalRandom.current().nextInt(0, nodes.size());
		return nodes.get(r);
	}

	private ArrayList<CellNode> goodNodes(ArrayList<CellNode> nodes, double averagePheromone, Cell currentCell) {
		ArrayList<CellNode> goodNodes = new ArrayList<>();
		for (CellNode n : nodes) {
			if (costMatrix.getPheromone(currentCell,n) >= averagePheromone) {
				goodNodes.add(n);
			}
		}
		return goodNodes;
	}
	
	private double getAveragePheromone(ArrayList<CellNode> nodes, Cell currentCell) {
		double totalPheromone = 0;
		for (CellNode n : nodes) {
			totalPheromone += costMatrix.getPheromone(currentCell,n);
		}
		return totalPheromone/nodes.size();
	}

	private ArrayList<CellNode> nearNodes(ArrayList<CellNode> nodes, double averageCost, Cell currentCell) {
		ArrayList<CellNode> nearFlowers = new ArrayList<>();
		for (CellNode n : nodes) {
			if (costMatrix.getCost(currentCell,n) <= averageCost) {
				nearFlowers.add(n);
			}
		}
		return nearFlowers;
	}

	private double getAverageCost(ArrayList<CellNode> nodes, Cell currentCell) {
		double totalCost = 0;
		for (CellNode n : nodes) {
			totalCost += costMatrix.getCost(currentCell,n);
		}
		return totalCost/nodes.size();
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
	
	public Map getMap() {
		return map;
	}
}
