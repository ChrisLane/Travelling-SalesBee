package com.teamc2.travellingsalesbee.gui.elements;

import java.util.ArrayList;

public class ElementMap implements Element {

	private int width; //Width of map
	private int height; //Height of map
	private int speed; //Speed of bees
	private ElementCell[][] cells; //Will store our ElementCells

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

	public String getCellType(ElementCell cell) {
		return cell.getType();
	}

	public ArrayList<ElementCell> getCellsOfType() {
		// TODO: Implement 'getCellsOfType' method
		return null;
	}

	public ElementCell getCell(int x, int y) {
		return cells[x][y];
	}

	//SET METHODS

	/**
	 * Adds a new cell to the grid map at x, y
	 */
	public void setCell(int x, int y, int type) {
		if (type != ElementCell.EMPTY) {
			ElementCell cell = new ElementCell(x, y, type);
			cells[x][y] = cell;
		} else {
			cells[x][y] = null;
		}
	}


	public void clearCell(int x, int y) {
		setCell(x, y, ElementCell.EMPTY);
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
