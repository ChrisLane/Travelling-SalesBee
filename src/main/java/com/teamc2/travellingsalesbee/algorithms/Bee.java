package com.teamc2.travellingsalesbee.algorithms;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;

import com.teamc2.travellingsalesbee.gui.NaiveStep;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import com.teamc2.travellingsalesbee.gui.data.cells.CellFlower;

public class Bee extends Observable {

	private final Cell hive;
	private final Map map;
	private ArrayList<Cell> path = new ArrayList<>();
	private ArrayList<NaiveStep> naiveSteps = new ArrayList<>();
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
			ArrayList<NaiveStep> naiveSteps = new ArrayList<>();
			ArrayList<CellFlower> flowers = map.getFlowers();
			ArrayList<Cell> naiveComparisons = new ArrayList<Cell>();

			newPath.add(hive);
			// Loop over flowers missing from path
			CellFlower closest = null;
			
			while (!flowers.isEmpty()) {
				double bestDistance = Double.MAX_VALUE;
				closest = null;
				Cell start = newPath.get(newPath.size()-1);
				
		
				// Find the closest flower to the previous
				for (CellFlower flower : flowers) {
					double distance = flower.distance(newPath.get(newPath.size() - 1));
					if (distance < bestDistance) {
						closest = flower;
						bestDistance = distance;
					}
				}
				//Remove the flower that is closest from the set
				flowers.remove(closest);
				
				/**************************************************************/
				/**	Formulating of naive steps for the step through process	**/
				/************************************************************/
				
				//Check to see if the first step has been created
					//If not then create initial step
					//Else create the intemediate steps in the naive visualisation
				if (!hiveStepDone){
					ArrayList<Cell> allFlowers = map.getNodes();
					allFlowers.remove(closest);
					NaiveStep step = new NaiveStep(start,allFlowers,closest);
					naiveSteps.add(step);
					hiveStepDone=true;
				}else{

					for (CellFlower flower : flowers){
						System.out.print(start.x + " " + start.y + " " +naiveSteps.size());
						if (!newPath.contains(flower)){
							naiveComparisons.add(flower);
						}
					}
					//A naive step is compromised of
						//the start node which is the previous visited flower
						//naiveComparisons which is the nodes it checks but doesn't choose
						//the closest node which is the node it has chosen to visit next
					NaiveStep step = new NaiveStep(start, naiveComparisons,closest);
					
					//Add the step to an array of naiveSteps
					naiveSteps.add(step);
				}
				newPath.add(closest);
			}
			
			//Create the return step from the last node to the hive
				//start node is the last flower node
				//Empty available moves as only one move available
				//hive is the end node as it thus creates the TSM cycle
			ArrayList<Cell> empty = new ArrayList<>();
			Cell hive = newPath.get(0);
			NaiveStep step = new NaiveStep(closest,empty,hive);
			naiveSteps.add(step);
			
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
	
	/**
	 * Set the naive steps.
	 * 
	 * @param naiveSteps The steps involved in the naive run
	 */
	public void setNaiveSteps(ArrayList<NaiveStep> naiveSteps){
		this.naiveSteps = naiveSteps;
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
	
	/**
	 * Return the naive steps.
	 * 
	 * @return naiveSteps. The steps involved in the naive run
	 */
	public ArrayList<NaiveStep> getNaiveSteps() {
		return naiveSteps;
	}
}