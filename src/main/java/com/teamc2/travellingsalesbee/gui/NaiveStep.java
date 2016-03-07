package com.teamc2.travellingsalesbee.gui;

import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import java.util.ArrayList;

public class NaiveStep {

	private Cell end;
	private ArrayList<Cell> available = new ArrayList<>();
	private Cell start;

	/**
	 * @param start     The start node of a step
	 * @param available The non chosen nodes from a given start point
	 * @param end       The end node of a step
	 */
	public NaiveStep(Cell start, ArrayList<Cell> available, Cell end) {
		this.start = start;
		this.available = available;
		this.end = end;
	}

	/**
	 * @return start. The start of a step
	 */
	public Cell getStart() {
		return start;
	}

	/**
	 * @return available. The available (non chosen) nodes to move to from the start node in the step
	 */
	public ArrayList<Cell> getAvailable() {
		return available;
	}

	/**
	 * @return end. The end node of a step
	 */
	public Cell getEnd() {
		return end;
	}

}
