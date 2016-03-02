package com.teamc2.travellingsalesbee.algorithms;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

public class Bee {

	private final Map map;
	private int experiments;

	private ArrayList<Cell> path = new ArrayList<>();
	private ArrayList<ArrayList<Cell>> intermediaryPaths = new ArrayList<>();
	private ArrayList<Comparison<Cell, Cell>> comparedCells = new ArrayList<>();
	private double cost = Double.MAX_VALUE;

	/**
	 * Constructor
	 *
	 * @param map         Map object for storing cells
	 * @param experiments Number of experiments to run
	 */
	public Bee(Map map, int experiments) {
		this.map = map;
		this.experiments = experiments;
	}

	/**
	 * Run a naive path find.
	 * 
	 * A greedy like algorithm, the bee initially carries out a naive run where it visits
	 * the nearest non-visited neighbour until every flower has been visited, following 
	 * that it then returns to the hive
	 */
	public void naiveRun() {
		NearestNeighbour nearest = new NearestNeighbour(map);
		nearest.run();
		setPath(nearest.getPath(),nearest.getPathCost());
	}

	/**
	 * Runs an experimental path improvement check
	 */
	public void experimentalRun() {
		if (path.size() > 4) {
			while (experiments > 0) {
				ArrayList<Cell> testPath = path;

				int flowerPos1 = 0;
				int flowerPos2 = 0;

				while (flowerPos1 == flowerPos2) {
					flowerPos1 = ThreadLocalRandom.current().nextInt(1, testPath.size() - 2);
					flowerPos2 = ThreadLocalRandom.current().nextInt(1, testPath.size() - 2);
				}
				System.out.println(testPath.get(testPath.size()-1));
				Cell flower1 = testPath.get(flowerPos1);
				Cell flower2 = testPath.get(flowerPos2);

				testPath.set(flowerPos1, flower2);
				testPath.set(flowerPos2, flower1);

				// TODO: Call a method to visualise the flower1 and flower2 swap

				double testCost = calculatePathCost(testPath);
				if (testCost < cost) {
					setPath(testPath, testCost);
					System.out.println("Improved");
					intermediaryPaths.add(testPath);
				} else {
					System.out.println("Not improved");
					intermediaryPaths.add(this.path);
				}
				comparedCells.add(new Comparison<>(flower1, flower2));
				
				experiments--;
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
		for (int i = 0; i < path.size()-1; i++) {
			Cell pos1 = path.get(i);
			Cell pos2 = path.get(i + 1);

			cost += pos1.distance(pos2);
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
	 * @return path. The Current path.
	 */
	public ArrayList<Cell> getPath() {
		return path;
	}
	
	public ArrayList<ArrayList<Cell>> getIntermediaryPaths(){
		intermediaryPaths.forEach(System.out::println);
		return intermediaryPaths;
	}
	
	public ArrayList<Comparison<Cell,Cell>> getCellComparisons(){
		return comparedCells;
	}

}