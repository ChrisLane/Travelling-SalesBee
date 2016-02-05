package com.teamc2.travellingsalesbee.gui.elements;

import javafx.geometry.Point2D;

public class ElementCell extends Point2D implements Element {

	public static final int EMPTY 	= 0;
	public static final int HIVE 	= 1;
	public static final int FLOWER 	= 2;

	private int type = EMPTY; 		// Empty cell
	private int x; 					// x position of Cell
	private int y; 					// y position of Cell

	/**
	 * Creates a new instance of {@code Point2D}.
	 *
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 */
	public ElementCell(int x, int y, int type) {
		super(x, y);
		this.x = x;
		this.y = y;
		this.type = type;
	}

	//GET METHODS

	public String getType() {
		switch (type) {
			case EMPTY:
				return "Empty Cell";
			case HIVE:
				return "Hive";
			case FLOWER:
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

	public void setType(int type) {
		this.type = type;
	}
}
