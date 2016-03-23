package com.teamc2.travellingsalesbee.gui.data.steps;

import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import java.util.ArrayList;

/**
 * A class to represent a single step of the nearest neighbour algorithm (also known as naive run for bee)
 *
 * @author Bradley Rowe (bmr455)
 */
public class NaiveStep {

	private Cell end;
	private ArrayList<Cell> available = new ArrayList<>();
	private Cell start;
	private String text;

	/**
	 * Construct a naive step
	 *
	 * @param start     The start node of a step
	 * @param available The non chosen nodes from a given start point
	 * @param end       The end node of a step
	 */
	public NaiveStep(Cell start, ArrayList<Cell> available, Cell end, String text) {
		this.start = start;
		this.available = available;
		this.end = end;
		this.text = text;
	}

	/**
	 * Get the start cell of the step
	 *
	 * @return start The start cell of the step
	 */
	public Cell getStart() {
		return start;
	}

	/**
	 * Get the available (non chosen) nodes to move to from the start node
	 *
	 * @return available The available other (non chosen) nodes to move to from the start node
	 */
	public ArrayList<Cell> getAvailable() {
		return available;
	}

	/**
	 * Get the end cell of the step
	 *
	 * @return end The end cell of the step
	 */
	public Cell getEnd() {
		return end;
	}

	/**
	 * Get the text to display for the step
	 *
	 * @return text The text to display for the given step
	 */
	public String getText() {
		return text;
	}
}
