package com.teamc2.travellingsalesbee.gui.elements;

import com.teamc2.travellingsalesbee.gui.elements.cells.Cell;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellEmpty;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellFlower;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellHive;

import java.util.ArrayList;

public class Map {

	private int width; //Width of map
	private int height; //Height of map
	private int speed; //Speed of bees
	private Cell[][] cells; //Will store our ElementCells

	private static final String EMPTY = Cell.EMPTY;
	private static final String HIVE = Cell.HIVE;
	private static final String FLOWER = Cell.FLOWER;

	/**
	 * Create a new map
	 *
	 * @param width
	 * @param height
	 */
	public Map(int width, int height) {
		this.width = width;
		this.height = height;

		cells = new Cell[width][height];
		setMap();
	}

	//GET METHODS

	public String getCellType(Cell cell) {
		return cell.getType();
	}

	public ArrayList<Cell> getCellsOfType(String type) {
		ArrayList<Cell> cellsOfType = new ArrayList<Cell>();
		for (int i=0;i<this.width;i++){
			for (int j=0;j<this.height;j++){
				if (getCellType(cells[i][j]).equals(type)){
					cellsOfType.add(cells[i][j]);
				}
			}
		}
		return cellsOfType;
	}

	public ArrayList<CellFlower> getFlowers() {
		ArrayList<CellFlower> flowers = new ArrayList<>();
		for (int i=0;i<this.width;i++){
			for (int j=0;j<this.height;j++){
				if (cells[i][j].getType().equals(Cell.FLOWER)){
					flowers.add((CellFlower) cells[i][j]);
				}
			}
		}
		return flowers;
	}

	public Cell getCell(int x, int y) {
		return cells[x][y];
	}

	//SET METHODS

	/**
	 * Adds a new cell to the grid map at x, y
	 */
	public void setCell(int x, int y, String type) {
		switch (type) {
			case EMPTY:
				cells[x][y] = new CellEmpty(x,y);
				break;
			case HIVE:
				cells[x][y] = new CellHive(x,y);
				break;
			case FLOWER:
				cells[x][y] = new CellFlower(x,y);
				break;
		}
	}


	public void clearCell(int x, int y) {
		setCell(x, y, EMPTY);
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
	public void setMap() {
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				clearCell(i,j);
			}
		}
	}
}
