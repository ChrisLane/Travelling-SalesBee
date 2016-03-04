package com.teamc2.travellingsalesbee.visualisation;

import java.util.ArrayList;

import com.teamc2.travellingsalesbee.gui.ExperimentalStep;
import com.teamc2.travellingsalesbee.gui.NaiveStep;
import com.teamc2.travellingsalesbee.gui.SwapType;
import com.teamc2.travellingsalesbee.gui.algorithms.cost.Comparison;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

public class BeeVisualiser {
		
	
	public ArrayList<NaiveStep> getNaiveSteps(ArrayList<Cell> naiveRun) {
		ArrayList<NaiveStep> listOfSteps = new ArrayList<>();
		
		for(int i=0; i<naiveRun.size()-1;i++){
			Cell start = naiveRun.get(i);
			Cell end = naiveRun.get(i+1);
			ArrayList<Cell> naiveComparisons = new ArrayList<>();
			
			for (int j=i;j<naiveRun.size()-1;j++){
				naiveComparisons.add(naiveRun.get(j));
			}
			NaiveStep step = new NaiveStep(start,naiveComparisons,end);
			listOfSteps.add(step);
		}
		return listOfSteps;
	}
	
	public ArrayList<ExperimentalStep> getExperimentalSteps(ArrayList<Comparison<Cell,Cell>> comparedCells, ArrayList<ArrayList<Cell>> intermediaryPaths, ArrayList<Double> intermediaryPathCosts){
		ArrayList<ExperimentalStep> experimentalSteps = new ArrayList<>();
		
		for (int i=0;i<intermediaryPaths.size()-2;i+=2){
			ExperimentalStep initialStep = new ExperimentalStep(comparedCells.get(i+1), intermediaryPaths.get(i), intermediaryPathCosts.get(i), SwapType.INSPECTED);
			
			ExperimentalStep middleStep = new ExperimentalStep(comparedCells.get(i+1), intermediaryPaths.get(i+1), intermediaryPathCosts.get(i+1), SwapType.INSPECTED);
			
			SwapType typeOfResultingStep = getType(intermediaryPaths.get(i),intermediaryPaths.get(i+2));
			ExperimentalStep resultingStep = new ExperimentalStep(comparedCells.get(i+1), intermediaryPaths.get(i+2), intermediaryPathCosts.get(i+2), typeOfResultingStep);
		
			experimentalSteps.add(initialStep);
			experimentalSteps.add(middleStep);
			experimentalSteps.add(resultingStep);
		}
		
		return experimentalSteps;
	}
	
	private SwapType getType(ArrayList<Cell> path1, ArrayList<Cell> path2){
		for(int i=0;i<path1.size();i++){
			if (path1.get(i)!=path2.get(i)){
				return SwapType.ACCEPTED;
			}
		}
		System.out.println(path1==path2);
		return SwapType.REJECTED;
	}
}
