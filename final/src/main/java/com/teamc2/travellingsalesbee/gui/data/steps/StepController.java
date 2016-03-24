package com.teamc2.travellingsalesbee.gui.data.steps;

import com.teamc2.travellingsalesbee.algorithms.cost.Comparison;
import com.teamc2.travellingsalesbee.algorithms.cost.CostMatrix;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import java.util.ArrayList;

/**
 * A class for accessing different types of steps
 *
 * @author Bradley Rowe (bmr455)
 */
public class StepController {

	/**
	 * Get the steps to create a naive path
	 *
	 * @param naiveRun The naive path
	 * @return Steps in creating the naive path
	 */
	public ArrayList<NaiveStep> getNaiveSteps(ArrayList<Cell> naiveRun) {
		ArrayList<NaiveStep> listOfSteps = new ArrayList<>();

		for (int i = 0; i < naiveRun.size() - 1; i++) {
			Cell start = naiveRun.get(i);
			Cell end = naiveRun.get(i + 1);
			ArrayList<Cell> ResultingNaiveComparisons = new ArrayList<>();
			ArrayList<Cell> naiveComparisons = new ArrayList<>();

			String text = "";
			text += "Node " + i + " chosen because it has the smallest distance of " + start.distance(end) + "m";


			for (int j = i; j < naiveRun.size() - 1; j++) {
				if (j != (i)) {
					ResultingNaiveComparisons.add(naiveRun.get(j));
				}
				text += "Node " + j + " not chosen because it has the smallest distance of " + start.distance(end) + "m";
				naiveComparisons.add(naiveRun.get(j));
			}

			NaiveStep step = new NaiveStep(start, naiveComparisons, end, text);
			NaiveStep ResultingStep = new NaiveStep(start, ResultingNaiveComparisons, end, text);
			listOfSteps.add(step);
			listOfSteps.add(ResultingStep);
		}
		return listOfSteps;
	}

	/**
	 * Get the steps in doing the experimental runs
	 *
	 * @param comparedCells         Cells compared in the experimental runs
	 * @param intermediaryPaths     Tested paths in the experimental runs
	 * @param intermediaryPathCosts The test path costs in the experimental runs
	 * @return Steps in doing the experimental runs
	 */
	public ArrayList<ExperimentalStep> getExperimentalSteps(ArrayList<Comparison<Cell, Cell>> comparedCells, ArrayList<ArrayList<Cell>> intermediaryPaths, ArrayList<Double> intermediaryPathCosts) {
		ArrayList<ExperimentalStep> experimentalSteps = new ArrayList<>();

		for (int i = 0; i < intermediaryPaths.size() - 2; i += 3) {
			ExperimentalStep initialStep = new ExperimentalStep(comparedCells.get(i + 1), intermediaryPaths.get(i), intermediaryPathCosts.get(i), SwapType.BEST);

			ExperimentalStep middleStep = new ExperimentalStep(comparedCells.get(i + 1), intermediaryPaths.get(i + 1), intermediaryPathCosts.get(i + 1), SwapType.INSPECTED);

			SwapType typeOfResultingStep = getType(intermediaryPaths.get(i), intermediaryPaths.get(i + 2));
			ExperimentalStep resultingStep = new ExperimentalStep(comparedCells.get(i + 1), intermediaryPaths.get(i + 2), intermediaryPathCosts.get(i + 2), typeOfResultingStep);

			experimentalSteps.add(initialStep);
			experimentalSteps.add(middleStep);
			experimentalSteps.add(resultingStep);
		}

		return experimentalSteps;
	}

	/**
	 * Get whether the swap was accepted or rejected
	 *
	 * @param previousPath The path before the swap
	 * @param resultPath   The resulting path after the swap
	 * @return Whether the swap was accepted or rejected
	 */
	private SwapType getType(ArrayList<Cell> previousPath, ArrayList<Cell> resultPath) {
		for (int i = 0; i < previousPath.size(); i++) {
			if (previousPath.get(i) != resultPath.get(i)) {
				return SwapType.ACCEPTED;
			}
		}
		return SwapType.REJECTED;
	}

	/**
	 * Get the steps in running the ant algorithm
	 *
	 * @param setOfRuns     Best paths throughout the algorithm
	 * @param setOfMatrices Cost matrices for each run
	 * @param initialMatrix Initial cost matrix
	 * @return Steps in the ant algorithm runs
	 */
	public ArrayList<AntStep> getAntSteps(ArrayList<ArrayList<Cell>> setOfRuns, ArrayList<CostMatrix> setOfMatrices, CostMatrix initialMatrix) {
		ArrayList<AntStep> antSteps = new ArrayList<>();

		//Add an initial step so route can be printed with equal pheromones
		AntStep initialStep = new AntStep(setOfRuns.get(0), initialMatrix);
		antSteps.add(initialStep);

		for (int i = 0; i < setOfRuns.size(); i++) {
			AntStep step = new AntStep(setOfRuns.get(i), setOfMatrices.get(i));
			antSteps.add(step);
		}
		return antSteps;
	}
}
