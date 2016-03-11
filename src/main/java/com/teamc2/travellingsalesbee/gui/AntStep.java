package com.teamc2.travellingsalesbee.gui;

import java.util.ArrayList;

import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

public class AntStep {

	private ArrayList<Cell> path = new ArrayList<>();

	public AntStep(ArrayList<Cell> path) {
		this.path = path;
	}
	
	public ArrayList<Cell> getPath() {
		return path;
	}

}
