package com.teamc2.travellingsalesbee.visualisation;

import java.util.ArrayList;

import com.teamc2.travellingsalesbee.gui.NaiveStep;
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
	
}
