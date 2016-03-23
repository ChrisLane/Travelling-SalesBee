package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.algorithms.cost.Comparison;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Bee inspired algorithm
 *
 * @author Christopher Lane (cml476)
 * @author Todd Waugh Ambridge (txw467)
 */
public class Bee extends NearestNeighbour {

	private int experiments;
	private ArrayList<ArrayList<Cell>> intermediaryPaths = new ArrayList<>();
	private ArrayList<Comparison<Cell, Cell>> comparedCells = new ArrayList<>();
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
					flowerPos1 = ThreadLocalRandom.current().nextInt(1, testPath.size() - 1);
					flowerPos2 = ThreadLocalRandom.current().nextInt(1, testPath.size() - 1);
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
				}
				intermediaryPaths.add(path);
				intermediaryPathCosts.add(cost);

				comparedCells.add(new Comparison<>(flower1, flower2));
				experiments--;
			}
		}
	}

	/**
	 * Get all paths tested in experimental runs
	 *
	 * @return All paths tested in experimental runs
	 */
	public ArrayList<ArrayList<Cell>> getIntermediaryPaths() {
		return intermediaryPaths;
	}

	/**
	 * Get all cell comparisons used in experimental runs
	 *
	 * @return All cell comparisons used in experimental runs
	 */
	public ArrayList<Comparison<Cell, Cell>> getCellComparisons() {
		return comparedCells;
	}

	/**
	 * Get all costs for the paths tested in experimental runs
	 *
	 * @return All costs for the paths tested in experimental runs
	 */
	public ArrayList<Double> getIntermediaryPathCosts() {
		return intermediaryPathCosts;
	}

}