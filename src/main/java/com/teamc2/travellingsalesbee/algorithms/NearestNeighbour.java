package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import com.teamc2.travellingsalesbee.gui.data.cells.CellNode;

import java.util.ArrayList;

/**
 * 
 * @author Todd Waugh Ambridge (txw467)
 *
 */
public class NearestNeighbour {

	protected final Map map;
	protected final Cell origin;
	protected ArrayList<Cell> path = new ArrayList<>();
	protected double cost = Double.MAX_VALUE;

	/**
	 * Constructor
	 *
	 * @param map The Map object for storing cells
	 */
	public NearestNeighbour(Map map) {
		this.map = map;
		origin = map.getOrigin();
	}

	/**
	 * A method to run a naive path i.e. greedy best first search.
	 * <p>
	 * A greedy like algorithm, initially visits the nearest non-visited neighbour 
	 * until every node has been visited, following that it then returns to the origin
	 */
	public void naiveRun() {
		if (!(origin == null)) {
			ArrayList<Cell> newPath = new ArrayList<>();
			ArrayList<CellNode> nodes = map.getNodes();

			newPath.add(origin);

			// Loop over nodes missing from path
			Cell currentCell;
			CellNode closest;

			while (!nodes.isEmpty()) {
				double bestDistance = Double.MAX_VALUE;
				closest = null;

				// Find the closest node to the previous
				for (CellNode node : nodes) {
					currentCell = newPath.get(newPath.size() - 1);
					double distance = map.getCostMatrix().getCost(currentCell, node);
					if (distance < bestDistance) {
						closest = node;
						bestDistance = distance;
					}
				}
				//Remove the node that is closest from the set
				nodes.remove(closest);
				newPath.add(closest);
			}

			newPath.add(origin);

			double cost = calculatePathCost(newPath);
			setPath(newPath, cost);
		}
	}

	/**
	 * A method for retrieving the path of the current path
	 *
	 * @return Cost of the current path
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Calculate the cost for a given path
	 *
	 * @param path The path to calculate the cost for
	 * @return Cost of the path
	 */
	public double calculatePathCost(ArrayList<Cell> path) {
		double cost = 0;
		for (int i = 0; i < path.size() - 1; i++) {
			Cell pos1 = path.get(i);
			Cell pos2 = path.get(i + 1);

			cost += map.getCostMatrix().getCost(pos1, pos2);
		}
		return cost;
	}

	/**
	 * Set the current path
	 *
	 * @param path The path to be set
	 * @param cost The total cost of the path being set
	 */
	public void setPath(ArrayList<Cell> path, double cost) {
		this.path = path;
		this.cost = cost;
	}

	/**
	 * Return the current path
	 *
	 * @return path The Current path
	 */
	public ArrayList<Cell> getPath() {
		return path;
	}
}