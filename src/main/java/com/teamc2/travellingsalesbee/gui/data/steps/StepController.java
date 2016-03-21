package com.teamc2.travellingsalesbee.gui.data.steps;

import java.util.ArrayList;

import com.teamc2.travellingsalesbee.algorithms.cost.Comparison;
import com.teamc2.travellingsalesbee.algorithms.cost.CostMatrix;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

public class StepController {


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

	private SwapType getType(ArrayList<Cell> path1, ArrayList<Cell> path2) {
		for (int i = 0; i < path1.size(); i++) {
			if (path1.get(i) != path2.get(i)) {
				return SwapType.ACCEPTED;
			}
		}
		return SwapType.REJECTED;
	}

	public ArrayList<AntStep> getAntSteps(ArrayList<ArrayList<Cell>> setOfRuns, ArrayList<CostMatrix> setOfMatrices, CostMatrix initialMatrix) {
		ArrayList<AntStep> antSteps = new ArrayList<>();
		
		//Add an initial step so route can be printed with equal pheromones
		AntStep initialStep = new AntStep(setOfRuns.get(0),initialMatrix);
		antSteps.add(initialStep);
		
		for (int i=0;i<setOfRuns.size();i++){
			AntStep step = new AntStep(setOfRuns.get(i), setOfMatrices.get(i));
			antSteps.add(step);
		}
		return antSteps;
	}
}
