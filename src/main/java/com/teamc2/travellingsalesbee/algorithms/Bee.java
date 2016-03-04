package com.teamc2.travellingsalesbee.algorithms;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.teamc2.travellingsalesbee.gui.algorithms.cost.Comparison;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

public class Bee extends NearestNeighbour {

	private int experiments;
	private ArrayList<ArrayList<Cell>> intermediaryPaths = new ArrayList<>();
	private ArrayList<Comparison<Cell,Cell>> comparedCells = new ArrayList<>();
	private ArrayList<Double> intermediaryPathCosts = new ArrayList<>();

	/**
	 * Constructor
	 *
	 * @param map         Map object for storing cells
	 * @param experiments Number of experiments to run
	 */
	public Bee(Map map, int experiments) {
		super(map);
		this.experiments = experiments;
	}

	/**
	 * Runs an experimental path improvement check
	 */
	public void experimentalRun() {
		if (path.size() > 4) {
			while (experiments > 0) {
				ArrayList<Cell> testPath = new ArrayList<>();
				testPath.addAll(path);

				int flowerPos1 = 0;
				int flowerPos2 = 0;

				while (flowerPos1 == flowerPos2) {
					flowerPos1 = ThreadLocalRandom.current().nextInt(1, testPath.size() - 2);
					flowerPos2 = ThreadLocalRandom.current().nextInt(1, testPath.size() - 2);
				}

				Cell flower1 = testPath.get(flowerPos1);
				Cell flower2 = testPath.get(flowerPos2);

				intermediaryPaths.add(path);
				comparedCells.add(new Comparison<>(flower1, flower2));
				intermediaryPathCosts.add(calculatePathCost(path));
				
				testPath.set(flowerPos1, flower2);
				testPath.set(flowerPos2, flower1);
				
				intermediaryPaths.add(testPath);
				comparedCells.add(new Comparison<>(flower1, flower2));
				intermediaryPathCosts.add(calculatePathCost(testPath));

				double testCost = calculatePathCost(testPath);
				if (testCost < cost) {
					setPath(testPath, testCost);
					intermediaryPaths.add(testPath);
					intermediaryPathCosts.add(calculatePathCost(path));
				} else {
					intermediaryPaths.add(path);
					intermediaryPathCosts.add(calculatePathCost(path));

				}

				comparedCells.add(new Comparison<>(flower1, flower2));
				experiments--;
			}
		}
	}
	
	public ArrayList<ArrayList<Cell>> getIntermediaryPaths(){
		intermediaryPaths.forEach(System.out::println);
		return intermediaryPaths;
	}
	
	public ArrayList<Comparison<Cell,Cell>> getCellComparisons(){
		return comparedCells;
	}
	
	public ArrayList<Double> getIntermediaryPathCosts(){
		return intermediaryPathCosts ;
	}

}