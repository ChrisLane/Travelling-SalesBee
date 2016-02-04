package com.teamc2.travellingsalesbee.gui.elements;

import java.util.ArrayList;

public class ElementMap implements Element {

	private int width; //Width of map
	private int height; //Height of map
	private int speed; //Speed of bees
	private ArrayList<ElementCell> cells; //Will store our ElementCells

	/**
	 * Create a new map
	 *
	 * @param width
	 * @param height
	 */
	public ElementMap(int width, int height) {
		this.width = width;
		this.height = height;
	}

	//GET METHODS

	public getCellType(ElementCell cell) {
		return cell.getType();
	}

	public ArrayList<ElementCell> getCellsOfType() {
		// TODO: Implement 'getCellsOfType' method
		return null;
	}

	//SET METHODS

	/**
	 * Adds a new cell to the grid map at x, y
	 */
	public void newCell(int x, int y) {
		ElementCell nCell = new ElementCell();
		cells.add(nCell);
		// TODO: Implement 'addCell' method
	}

	/**
	 * Sets the speed for the bees to move at
	 */
	public void setSpeed(int speed) {
		// TODO: Implement 'setSpeed' method
	}

	/**
	 * Reset the current map
	 */
	public void resetMap() {
		// TODO: Implement 'resetMap' method
	}
}
