package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Model for Two-Opt Swap Algorithm
 *
 * @author Neil Farrington (npf489)
 */
public class TwoOptSwap extends NearestNeighbour {

	private int stepNum;
	private ArrayList<int[]> swapLog = new ArrayList<>();

	/**
	 * Construct the algorithm.
	 *
	 * @param map Map object for storing cells.
	 */
	public TwoOptSwap(Map map) {
		super(map);
	}

	/**
	 * Perform the swap.
	 *
	 * @param stepNum The step number to step to.
	 */
	public void swap(int stepNum) {
		// if the step number we need to get to is forwards, perform the next swap
		while (stepNum > this.stepNum) {
			nextSwap();
			this.stepNum++;
		}

		// if the step number we need to get to is backwards, perform the previous swap
		while (stepNum < this.stepNum) {
			previousSwap();
			this.stepNum--;
		}

		// set the target step as the current step
		this.stepNum = stepNum;
	}

	/**
	 * Run the next swap.
	 */
	protected void nextSwap() {
		if (path.size() >= 2) {
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
			setPath(testPath, testCost);
			int[] swap = new int[]{flowerPos1, flowerPos2};
			swapLog.add(swap);
		}
	}

	/**
	 * Reverse the last swap that was carried out.
	 */
	protected void previousSwap() {
		ArrayList<Cell> path = new ArrayList<>();
		path.addAll(this.path);

		int[] swap = swapLog.get(swapLog.size() - 1);
		swapLog.remove(swapLog.size() - 1);

		Cell flower1 = path.get(swap[1]);
		Cell flower2 = path.get(swap[0]);

		path.set(swap[0], flower1);
		path.set(swap[1], flower2);

		setPath(path, getCost());
	}

	/**
	 * Set the step number without cycling to it.
	 *
	 * @param stepNum The step number to set.
	 */
	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
	}
}