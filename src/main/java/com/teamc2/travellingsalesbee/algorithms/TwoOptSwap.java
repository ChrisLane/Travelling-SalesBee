package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Model for Two-Opt Swap Algorithm
 *
 * @author Neil Farrington (npf489)
 * @author Todd Waugh Ambridge (txw467)
 */
public class TwoOptSwap extends NearestNeighbour {

	private int stepNum;
	private int runs;
	private int flowerPos1, flowerPos2;
	private double[] flower1, flower2;
	private ArrayList<int[]> swapLog = new ArrayList<>();
	private ArrayList<Cell> newPath = new ArrayList<>();

	/**
	 * Construct the algorithm.
	 *
	 * @param map Map object for storing cells.
	 */
	public TwoOptSwap(Map map, int runs) {
		super(map);
		this.runs = runs;
	}

	/**
	 * Perform the swap.
	 *
	 * @param stepNum The step number to step to.
	 */
	public void swap(int stepNum) {
		// if the step number we need to get to is forwards, perform the next swap
		// else if the step number we need to get to is backwards, perform the previous swap
		if (stepNum > this.stepNum) {
			nextSwap();
		} else if (stepNum < this.stepNum) {
			previousSwap();
		}

		// set the target step as the current step
		this.stepNum = stepNum;
	}

	/**
	 * Run the next swap.
	 */
	protected boolean nextSwap() {
		if (path.size() >= 2) {
			// create a new test path
			newPath = new ArrayList<>();
			newPath.addAll(path);

			flowerPos1 = 0;
			flowerPos2 = 0;
			// choose two random flowers
			while (flowerPos1 >= flowerPos2) {
				flowerPos1 = ThreadLocalRandom.current().nextInt(1, newPath.size() - 2);
				flowerPos2 = ThreadLocalRandom.current().nextInt(1, newPath.size() - 2);
			}

			flower1 = new double[]{path.get(flowerPos1).getX(), path.get(flowerPos1).getY()};
			flower2 = new double[]{path.get(flowerPos2).getX(), path.get(flowerPos2).getY()};

			// swap the flowers
			int tmpPos1 = flowerPos1;
			int tmpPos2 = flowerPos2;
			while (tmpPos1 < tmpPos2) {
				Cell flower1 = newPath.get(tmpPos1);
				Cell flower2 = newPath.get(tmpPos2);

				newPath.set(tmpPos1, flower2);
				newPath.set(tmpPos2, flower1);

				tmpPos1++;
				tmpPos2--;
			}
		}

		return false;
	}

	/**
	 * Check if the swap was successful, and if so, set it as the new path.
	 *
	 * @return Whether the swap was successful or not.
	 */
	public boolean swapSuccessful() {
		// if the swap was beneficial, update and return true
		double testCost = calculatePathCost(newPath);
		if (testCost < cost) {
			setPath(newPath, testCost);
			int[] swap = new int[]{flowerPos1, flowerPos2};
			swapLog.add(swap);

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Reverse the last swap that was carried out.
	 */
	protected void previousSwap() {
		// get the last swap
		int[] swap = swapLog.get(swapLog.size() - 1);
		swapLog.remove(swapLog.size() - 1);

		// perform the swap reversal and recalculate
		Cell flower1 = path.get(swap[1]);
		Cell flower2 = path.get(swap[0]);
		path.set(swap[0], flower1);
		path.set(swap[1], flower2);

		setPath(path, calculatePathCost(path));
		newPath = path;
	}

	/**
	 * Set the step number without cycling to it.
	 *
	 * @param stepNum The step number to set.
	 */
	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
	}

	/**
	 * Return the total number of experimental runs (swaps) to perform.
	 *
	 * @return Total runs.
	 */
	public int getRuns() {
		return runs;
	}

	/**
	 * Get the first flower being swapped.
	 *
	 * @return The flower's coordinates, with the X position in index 0, and the Y position in index 1.
	 */
	public double[] getFlower1() {
		return flower1;
	}

	/**
	 * Get the second flower being swapped.
	 *
	 * @return The flower's coordinates, with the X position in index 0, and the Y position in index 1.
	 */
	public double[] getFlower2() {
		return flower2;
	}

	/**
	 * Get the current path being tested.
	 *
	 * @return The current path being tested - this may be less optimal than the actual current path.
	 */
	public ArrayList<Cell> getNewPath() {
		return newPath;
	}
}