package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.gui.elements.Map;
import com.teamc2.travellingsalesbee.gui.elements.cells.Cell;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellFlower;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;

public class Bee extends Observable {

	private final Cell hive;
	private final Map map;
	private ArrayList<Cell> path = new ArrayList<>();
	private double cost = Double.MAX_VALUE;

	/**
	 * Constructor
	 *
	 * @param map         Map object for storing cells
	 * @param experiments Number of experiments to run
	 */
	public Bee(Map map, int experiments) {
		this.map = map;
		hive = map.getHive();

		naiveRun();
		//System.out.println("Naive Run: " + this.cost);
		experimentalRuns(experiments);
		//System.out.println("Post-Experimental Run: " + this.cost);
	}

	/**
	 * Run a naive path find.
	 */
	public void naiveRun() {
		if (!(hive == null)) {
			ArrayList<Cell> newPath = new ArrayList<>();
			ArrayList<CellFlower> flowers = map.getFlowers();

			newPath.add(hive);

			// Loop over flowers missing from path
			while (!flowers.isEmpty()) {
				double bestDistance = Double.MAX_VALUE;
				CellFlower closest = null;

				// Find the closest flower to the previous
				for (CellFlower flower : flowers) {
					double distance = flower.distance(newPath.get(newPath.size() - 1));
					if (distance < bestDistance) {
						closest = flower;
						bestDistance = distance;
					}
				}
				newPath.add(closest);
				flowers.remove(closest);
			}

			double cost = calculatePathCost(newPath);
			setPath(newPath, cost);
		}
	}

	/**
	 * Runs an experimental path improvement check
	 */
	private void experimentalRun() {
		if (path.isEmpty()) {
			naiveRun();
		}
		if (path.size() > 3) {
			ArrayList<Cell> testPath = path;

			int flowerPos1 = 0;
			int flowerPos2 = 0;

			while (flowerPos1 == flowerPos2) {
				flowerPos1 = ThreadLocalRandom.current().nextInt(1, testPath.size());
				flowerPos2 = ThreadLocalRandom.current().nextInt(1, testPath.size());
			}

			Cell flower1 = testPath.get(flowerPos1);
			Cell flower2 = testPath.get(flowerPos2);

			testPath.set(flowerPos1, flower2);
			testPath.set(flowerPos2, flower1);

			// TODO: Call a method to visualise the flower1 and flower2 swap

			double testCost = calculatePathCost(testPath);
			if (testCost < cost) {
				setPath(testPath, testCost);
			}
		}
	}

	/**
	 * A method for retrieving the path of the current path
	 *
	 * @return Cost of the current path
	 */
	public double getPathCost() {
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
		for (int i = 0; i < path.size(); i++) {
			if (i + 1 < path.size()) {
				Cell pos1 = path.get(i);
				Cell pos2 = path.get(i + 1);

				cost += pos1.distance(pos2);
			}
		}
		return cost;
	}

	/**
	 * Run experimental tests on the path, a given number of times
	 *
	 * @param experiments Number of experimental runs
	 */
	public void experimentalRuns(int experiments) {
		while (experiments > 0) {
			experimentalRun();
			experiments--;
		}
	}

	/**
	 * Set the current path
	 *
	 * @param path Path to be set
	 * @param cost Cost of the path being set
	 */
	public void setPath(ArrayList<Cell> path, double cost) {
		this.path = path;
		this.cost = cost;

		setChanged();
		notifyObservers(path);
	}

	/**
	 * Return the current path
	 *
	 * @return Current path
	 */
	public ArrayList<Cell> getPath() {
		return path;
	}
}