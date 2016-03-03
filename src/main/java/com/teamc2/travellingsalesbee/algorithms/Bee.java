package com.teamc2.travellingsalesbee.algorithms;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

public class Bee extends NearestNeighbour {

	private int experiments;
	private ArrayList<ArrayList<Cell>> intermediaryPaths = new ArrayList<>();
	private ArrayList<Comparison<Cell,Cell>> comparedCells = new ArrayList<>();

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
				intermediaryPaths.add(path);

				int flowerPos1 = 0;
				int flowerPos2 = 0;

				while (flowerPos1 == flowerPos2) {
					flowerPos1 = ThreadLocalRandom.current().nextInt(1, testPath.size() - 2);
					flowerPos2 = ThreadLocalRandom.current().nextInt(1, testPath.size() - 2);
				}

				System.out.println(testPath.get(testPath.size()-3));
				Cell flower1 = testPath.get(flowerPos1);
				Cell flower2 = testPath.get(flowerPos2);

				testPath.set(flowerPos1, flower2);
				testPath.set(flowerPos2, flower1);
				System.out.println(testPath.get(testPath.size()-3));
				intermediaryPaths.add(testPath);

				double testCost = calculatePathCost(testPath);
				if (testCost < cost) {
					setPath(testPath, testCost);
					System.out.println("Improved");
					intermediaryPaths.add(testPath);
				} else {
					System.out.println("Not improved");
					intermediaryPaths.add(path);
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

}