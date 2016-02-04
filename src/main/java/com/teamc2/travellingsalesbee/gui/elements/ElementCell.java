package com.teamc2.travellingsalesbee.gui.elements;

import javafx.geometry.Point2D;

public class ElementCell extends Point2D implements Element {

	private static int CELL_TYPE = 0; //0 for empty cell, 1 for hive, 2 for flower/node
	private int x; //x position of Cell
	private int y; //y position of Cell

	/**
	 * Creates a new instance of {@code Point2D}.
	 *
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 */
	public ElementCell(int x, int y, int type) {
		super(x, y);
		this.x = x; //x position of object
		this.y = y; //y position of object

		CELL_TYPE = type; //type of object (see above for types)
	}

	//GET METHODS

	public String getType() {
		switch (CELL_TYPE) {
			case 0:
				return "Empty Cell";
			case 1:
				return "Hive";
			case 2:
				return "Flower";
			default:
				return "Error, please enter 0, 1 or 2";
		}
	}

	public String getImage() {
		return "";
	}

	//SET METHODS
	public void setLoc(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
