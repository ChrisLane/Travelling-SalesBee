package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TwoOptSwap extends NearestNeighbour {

	private int swaps;

	/**
	 * Constructor
	 *
	 * @param map   Map object for storing cells
	 * @param swaps Number of swaps to run
	 */
	public TwoOptSwap(Map map, int swaps) {
		super(map);
		this.swaps = swaps;
	}

	/**
	 * Runs a two-opt swap improvement check
	 */
	public void swapRun() {
		if (path.size() > 4) {
			while (swaps > 0) {
				ArrayList<Cell> testPath = new ArrayList<>();
				testPath.addAll(path);

				int flowerPos1 = 0;
				int flowerPos2 = 0;

				while (flowerPos1 >= flowerPos2) {
					flowerPos1 = ThreadLocalRandom.current().nextInt(1, testPath.size() - 2);
					flowerPos2 = ThreadLocalRandom.current().nextInt(1, testPath.size() - 2);
				}

				while (flowerPos1 < flowerPos2) {
					Cell flower1 = testPath.get(flowerPos1);
					Cell flower2 = testPath.get(flowerPos2);

					testPath.set(flowerPos1, flower2);
					testPath.set(flowerPos2, flower1);

					flowerPos1++;
					flowerPos2--;
				}

				double testCost = calculatePathCost(testPath);
				if (testCost < cost) {
					setPath(testPath, testCost);
				}

				swaps--;
			}
		}
	}

}