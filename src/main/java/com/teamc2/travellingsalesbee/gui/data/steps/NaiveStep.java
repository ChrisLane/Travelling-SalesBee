package com.teamc2.travellingsalesbee.gui.data.steps;

import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import java.util.ArrayList;
/**
 * A class to represent a single step of the nearest neighbour algorithm (also known as naive run for bee)
 * 
 * @author Bradley Rowe (bmr455)
 *
 */
public class NaiveStep {

	private Cell end;
	private ArrayList<Cell> available = new ArrayList<>();
	private Cell start;
	private String text;

	/**
	 * @param start The start node of a step
	 * @param available The non chosen nodes from a given start point
	 * @param end The end node of a step
	 */
	public NaiveStep(Cell start, ArrayList<Cell> available, Cell end, String text) {
		this.start = start;
		this.available = available;
		this.end = end;
		this.text = text;
	}

	/**
	 * @return start The start cell of a step
	 */
	public Cell getStart() {
		return start;
	}

	/**
	 * @return available The available other (non chosen) nodes to move to from the start node in the step
	 */
	public ArrayList<Cell> getAvailable() {
		return available;
	}

	/**
	 * @return end The end cell of a step
	 */
	public Cell getEnd() {
		return end;
	}

	/**
	 * 
	 * @return text The text to display for a given step
	 */
	public String getText() {
		return text;
	}
}
