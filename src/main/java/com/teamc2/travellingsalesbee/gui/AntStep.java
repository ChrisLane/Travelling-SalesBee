package com.teamc2.travellingsalesbee.gui;

import java.util.ArrayList;

import com.teamc2.travellingsalesbee.algorithms.cost.CostMatrix;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

public class AntStep {

	private ArrayList<Cell> path = new ArrayList<>();
	private CostMatrix costMatrix;

	public AntStep(ArrayList<Cell> path, CostMatrix costMatrix)  {
		this.path = path;
		this.costMatrix  = costMatrix;
	}
	
	public ArrayList<Cell> getPath() {
		return path;
	}
	
	public CostMatrix getCostMatrix(){
		return costMatrix;
	}

}
