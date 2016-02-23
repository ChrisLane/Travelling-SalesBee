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
	private final int width; //Width of map
	private final int height; //Height of map
	private int speed; //Speed of bees
	private final Cell[][] cells; //Will store our ElementCells
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

	/**
	 * Return all cells in the map
	 *
	 * @return 2D array of cells in the map
	 */
	public Cell[][] getCells() {
		return cells;
	}

	/**
	 * Return all flowers in the map
	 *
	 * @return ArrayList of flowers in the map
	 */
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

	/**
	 * Get the cell at position (x, y)
	 *
	 * @param x X position of cell
	 * @param y Y position of cell
	 * @return
	 */
	public Cell getCell(int x, int y) {
		return cells[x][y];
	}

	//SET METHODS

	/**
	 * Set the type of a cell at position (x, y)
	 *
	 * @param x X position of cell
	 * @param y Y position of cell
	 * @param type Type of cell
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

	/**
	 * Set a cell at position (x, y) to empty
	 *
	 * @param x X position of cell
	 * @param y Y position of cell
	 */
	public void clearCell(int x, int y) {
		setCell(x, y, EMPTY);
	}

	/**
	 * Set the speed of the map's visualisation
	 *
	 * @param speed Speed of the visualisation
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Fill the map will empty cels
	 */
	public void setMap() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				clearCell(i, j);
			}
		}
	}

	/**
	 * Return the hive for the map
	 *
	 * @return Map's hive cell
	 */
	public CellHive getHive() {
		return hive;
	}
}
