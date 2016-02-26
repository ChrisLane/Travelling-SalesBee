package com.teamc2.travellingsalesbee.algorithms;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;

import com.teamc2.travellingsalesbee.gui.naiveStep;
import com.teamc2.travellingsalesbee.gui.elements.Map;
import com.teamc2.travellingsalesbee.gui.elements.cells.Cell;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellFlower;

public class Bee extends Observable {

	private final Cell hive;
	private final Map map;
	private ArrayList<Cell> path = new ArrayList<>();
	private ArrayList<naiveStep> naiveSteps = new ArrayList<>();
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
		experimentalRun(experiments);
	}

	/**
	 * Run a naive path find.
	 * 
	 * A greedy like algorithm, the bee initially carries out a naive run where it visits
	 * the nearest non-visited neighbour until every flower has been visited, following 
	 * that it then returns to the hive
	 */
	public void naiveRun() {
		if (!(hive == null)) {
			Boolean hiveStepDone = false;
			ArrayList<Cell> newPath = new ArrayList<>();
			ArrayList<naiveStep> naiveSteps = new ArrayList<>();
			ArrayList<CellFlower> flowers = map.getFlowers();
			ArrayList<Cell> naiveComparisons = new ArrayList<Cell>();

			newPath.add(hive);
			// Loop over flowers missing from path
			
			
			while (!flowers.isEmpty()) {
				double bestDistance = Double.MAX_VALUE;
				CellFlower closest = null;
				Cell start = newPath.get(newPath.size()-1);
				
		
				// Find the closest flower to the previous
				for (CellFlower flower : flowers) {
					double distance = flower.distance(newPath.get(newPath.size() - 1));
					if (distance < bestDistance) {
						closest = flower;
						bestDistance = distance;
					}
				}
				if (!hiveStepDone){
					ArrayList<Cell> allFlowers = map.getNodes();
					allFlowers.remove(closest);
					naiveStep step = new naiveStep(start,allFlowers,closest);
					naiveSteps.add(step);
					hiveStepDone=true;
				}else{

					for (CellFlower flower : flowers){
						System.out.print(start.x + " " + start.y + " " +naiveSteps.size());
						naiveComparisons.add((Cell)flower);
					}
					
					Cell dest = (Cell)closest;
					naiveStep step = new naiveStep(start, naiveComparisons,closest);
					naiveSteps.add(step);
				}
				newPath.add(closest);
				flowers.remove(closest);
			}

			double cost = calculatePathCost(newPath);
			setPath(newPath, cost);
			setNaiveSteps(naiveSteps);
		}
	}

	/**
	 * Runs an experimental path improvement check
	 * @param experiments Number of experimental runs
	 */
	private void experimentalRun(int experiments) {
		if (path.isEmpty()) {
			naiveRun();
		} else if (path.size() > 3) {
			while (experiments > 0) {
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
					System.out.println("PATH CHANGED!");
					setPath(testPath, testCost);
				} else {
					System.out.println("ERROR");
				}
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
	 * Run experimental tests on the path, a given number of times
	 *
	 * @param experiments Number of experimental runs
	 */
	public void experimentalRuns(int experiments) {
		while (experiments > 0) {
			experimentalRun(experiments);
			experiments--;
		}
	}

	public void setNaiveSteps(ArrayList<naiveStep> naiveSteps){
		this.naiveSteps = naiveSteps;
	}
	
	public ArrayList<naiveStep> getNaiveSteps() {
		return naiveSteps;
	}
	
	/**
	 * Set the current path
	 *
	 * @param path. The path to be set
	 * @param cost. The total cost of the path being set
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
	 * @return path. The Current path.
	 */
	public ArrayList<Cell> getPath() {
		return path;
	}
}