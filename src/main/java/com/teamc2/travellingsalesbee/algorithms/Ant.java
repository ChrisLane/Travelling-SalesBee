package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.algorithms.cost.CostMatrix;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import com.teamc2.travellingsalesbee.gui.data.cells.CellNode;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Ant Colony algorithm
 *
 * @author Todd Waugh Ambridge (txw467)
 */
public class Ant extends NearestNeighbour {

	private double heuristicCost;
	private CostMatrix costMatrix;

	/**
	 * Create a new ant TSP algorithm
	 *
	 * @param map Map containing nodes to be used in the algorithm
	 */
	public Ant(Map map) {
		super(map);
		costMatrix = map.getCostMatrix();
		naiveRun();
		setHeuristicCost(getCost());
	}

	/**
	 * Update the pheromones in the path
	 */
	public void pheromoneRun() {
		if (origin != null) {
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

			if ((cost <= heuristicCost) && (path.size() > 3)) {
				plantAllPheromone(path);
			}
		}
	}

	/**
	 * Find next node to visit
	 *
	 * @param nodes       Possible nodes to visit next
	 * @param currentCell Initial position to look for moves from
	 * @return The next node to visit
	 */
	private CellNode findNextNode(ArrayList<CellNode> nodes, Cell currentCell) {
		double averagePheromone = getAveragePheromone(nodes, currentCell);
		ArrayList<CellNode> goodNodes = goodNodes(nodes, averagePheromone, currentCell);

		if (goodNodes.size() > 0) {
			nodes = goodNodes;
		}

		double averageCost = getAverageCost(nodes, currentCell);
		nodes = nearNodes(nodes, averageCost, currentCell);

		int r = ThreadLocalRandom.current().nextInt(0, nodes.size());
		return nodes.get(r);
	}

	/**
	 * Get all nodes that are above or equal to the average pheromone level
	 * from the current cell and therefore, good to choose next
	 *
	 * @param nodes            Nodes to check
	 * @param averagePheromone Average pheromone level
	 * @param currentCell      Cell to check pheromone levels from
	 * @return Nodes that have a pheromone level from the current cell higher or equal to the average pheromone level
	 */
	private ArrayList<CellNode> goodNodes(ArrayList<CellNode> nodes, double averagePheromone, Cell currentCell) {
		ArrayList<CellNode> goodNodes = new ArrayList<>();
		for (CellNode n : nodes) {
			if (costMatrix.getPheromone(currentCell, n) >= averagePheromone) {
				goodNodes.add(n);
			}
		}
		return goodNodes;
	}

	/**
	 * Get the average pheromone level from the current node to all others
	 *
	 * @param nodes       Nodes to check pheromone levels to
	 * @param currentCell Cell to check pheromone levels from
	 * @return The average pheromone level from the current node to all others
	 */
	public double getAveragePheromone(ArrayList<CellNode> nodes, Cell currentCell) {
		double totalPheromone = 0;
		for (CellNode n : nodes) {
			totalPheromone += costMatrix.getPheromone(currentCell, n);
		}
		return totalPheromone / nodes.size();
	}

	/**
	 * Get all nodes that are less than or equal to the average distance from the
	 * current cell to given nodes
	 *
	 * @param nodes       Nodes to check the distance to
	 * @param averageCost Average cost to nodes
	 * @param currentCell Cell to check distances from
	 * @return Nodes that are less than or equal to the average distance from the current cell to given nodes
	 */
	private ArrayList<CellNode> nearNodes(ArrayList<CellNode> nodes, double averageCost, Cell currentCell) {
		ArrayList<CellNode> nearFlowers = new ArrayList<>();
		for (CellNode n : nodes) {
			if (costMatrix.getCost(currentCell, n) <= averageCost) {
				nearFlowers.add(n);
			}
		}
		return nearFlowers;
	}

	/**
	 * Get the average cost from a given cell to a list of other nodes
	 *
	 * @param nodes       Nodes to check the distance to
	 * @param currentCell Cell to check the distance from
	 * @return The average cost from a given cell to a list of other nodes
	 */
	public double getAverageCost(ArrayList<CellNode> nodes, Cell currentCell) {
		double totalCost = 0;
		for (CellNode n : nodes) {
			totalCost += costMatrix.getCost(currentCell, n);
		}
		return totalCost / nodes.size();
	}

	/**
	 * Set the heuristic cost
	 *
	 * @param cost Cost to set the heuristic cost to
	 */
	private void setHeuristicCost(double cost) {
		heuristicCost = cost;
	}

	/**
	 * Increase the pheromone level between two cells by 1
	 *
	 * @param cell1 The first cell in the pheromone link
	 * @param cell2 The second cell in the pheromone link
	 */
	private void plantPheromone(Cell cell1, Cell cell2) {
		map.getCostMatrix().getEntry(cell1, cell2).plantPheromone();
	}

	/**
	 * Increase the pheromone levels for a whole path by 1
	 *
	 * @param path The path to increase the pheromone level of
	 */
	private void plantAllPheromone(ArrayList<Cell> path) {
		for (int i = 0; i < path.size() - 1; i++) {
			Cell cell1 = path.get(i);
			Cell cell2 = path.get(i + 1);
			plantPheromone(cell1, cell2);
		}
	}

	/**
	 * Get the map that the ant algorithm is using
	 *
	 * @return The map that the ant algorithm is using
	 */
	public Map getMap() {
		return map;
	}
}
