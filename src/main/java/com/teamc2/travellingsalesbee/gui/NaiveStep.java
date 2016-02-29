package com.teamc2.travellingsalesbee.gui;

import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import java.util.ArrayList;

public class NaiveStep {

	private Cell end;
	private ArrayList<Cell> available = new ArrayList<>();
	private Cell start;

	public NaiveStep(Cell start, ArrayList<Cell> available, Cell cell) {
		this.start = start;
		this.available = available;
		end = cell;
	}

	public Cell getStart() {
		return start;
	}

	public ArrayList<Cell> getAvailable() {
		return available;
	}

	public Cell getEnd() {
		return end;
	}

}
