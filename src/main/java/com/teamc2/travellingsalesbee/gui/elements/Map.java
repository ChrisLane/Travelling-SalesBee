package com.teamc2.travellingsalesbee.gui.elements;

import com.teamc2.travellingsalesbee.gui.elements.cells.Cell;
import com.teamc2.travellingsalesbee.gui.elements.cells.Cell.CellType;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellEmpty;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellFlower;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellHive;

import javax.swing.*;
import java.util.ArrayList;

public class Map extends JPanel {

	private final CellType EMPTY = CellType.EMPTY;
	private final CellType HIVE = CellType.HIVE;
	private final CellType FLOWER = CellType.FLOWER;
	private int width; //Width of map
	private int height; //Height of map
	private int speed; //Speed of bees
	private Cell[][] cells; //Will store our ElementCells
	private CellHive hive;

	/**
	 * Create a new map
	 *
	 * @param width Width of the map
	 * @param height Height of the map
	 */
	public Map(int width, int height) {
		this.width = width;
		this.height = height;

		cells = new Cell[width][height];
		setMap();
	}

	//GET METHODS

	public Cell[][] getCells() {
		return cells;
	}

	public ArrayList<CellFlower> getFlowers() {
		ArrayList<CellFlower> flowers = new ArrayList<>();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (cells[i][j].getType().equals(CellType.FLOWER)) {
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
	public void setCell(int x, int y, CellType type) {
		switch (type) {
			case EMPTY:
				cells[x][y] = new CellEmpty(x, y);
				break;
			case HIVE:
				CellHive hive = new CellHive(x, y);
				cells[x][y] = hive;
				this.hive = hive;
				break;
			case FLOWER:
				cells[x][y] = new CellFlower(x, y);
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
		this.speed = speed;
	}

	/**
	 * Reset the current map
	 */
	public void setMap() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				clearCell(i, j);
			}
		}
	}

	public CellHive getHive() {
		return hive;
	}
}
