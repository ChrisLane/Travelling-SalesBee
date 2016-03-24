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
	private int nodePos1, nodePos2;
	private double[] node1, node2;
	private ArrayList<int[]> swapLog = new ArrayList<>();
	private ArrayList<Cell> newPath = new ArrayList<>();

	/**
	 * Construct the algorithm.
	 *
	 * @param map The Map object for storing cells.
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
	protected void nextSwap() {
		if (path.size() >= 2) {
			// create a new test path
			newPath = new ArrayList<>();
			newPath.addAll(path);

			nodePos1 = 0;
			nodePos2 = 0;
			// choose two random nodes
			while (nodePos1 >= nodePos2) {
				nodePos1 = ThreadLocalRandom.current().nextInt(1, newPath.size() - 2);
				nodePos2 = ThreadLocalRandom.current().nextInt(1, newPath.size() - 2);
			}

			node1 = new double[]{path.get(nodePos1).getX(), path.get(nodePos1).getY()};
			node2 = new double[]{path.get(nodePos2).getX(), path.get(nodePos2).getY()};

			// swap the nodes
			int tmpPos1 = nodePos1;
			int tmpPos2 = nodePos2;
			while (tmpPos1 < tmpPos2) {
				Cell node1 = newPath.get(tmpPos1);
				Cell node2 = newPath.get(tmpPos2);

				newPath.set(tmpPos1, node2);
				newPath.set(tmpPos2, node1);

				tmpPos1++;
				tmpPos2--;
			}
		}
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
			int[] swap = new int[]{nodePos1, nodePos2};
			swapLog.add(swap);

			return true;
		}
		return false;
	}

	/**
	 * Reverse the last swap that was carried out.
	 */
	protected void previousSwap() {
		// get the last swap
		if (swapLog.size() != 0) {
			int[] swap = swapLog.get(swapLog.size() - 1);
			swapLog.remove(swapLog.size() - 1);

			// perform the swap reversal and recalculate
			Cell node1 = path.get(swap[1]);
			Cell node2 = path.get(swap[0]);
			path.set(swap[0], node1);
			path.set(swap[1], node2);

			setPath(path, calculatePathCost(path));
			newPath = path;
		}
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
	 * Get the step number
	 * @return The step number
	 */
	public int getStepNum() {
		return stepNum;
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
	 * Get the first node being swapped.
	 *
	 * @return The node's coordinates, with the X position in index 0, and the Y position in index 1.
	 */
	public double[] getNode1() {
		return node1;
	}

	/**
	 * Get the second node being swapped.
	 *
	 * @return The node's coordinates, with the X position in index 0, and the Y position in index 1.
	 */
	public double[] getNode2() {
		return node2;
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