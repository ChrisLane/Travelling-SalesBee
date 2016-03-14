package com.teamc2.travellingsalesbee.gui.data;

import com.teamc2.travellingsalesbee.algorithms.cost.CostMatrix;
import com.teamc2.travellingsalesbee.gui.data.cells.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Map extends JPanel {

	private ArrayList<Cell> cells; //Will store our ElementCells
	private int speed; //Speed of bees
	private CellOrigin hive;
	private CostMatrix costMatrix;

	/**
	 * Create a new map
	 *
	 * @param width  Width of the map
	 * @param height Height of the map
	 */
	public Map() {
		cells = new ArrayList<>();
		setMap();
	}

	//GET METHODS

	/**
	 * Return all cells in the map
	 *
	 * @return 2D array of cells in the map
	 */
	public ArrayList<Cell> getCells() {
		return cells;
	}

	/**
	 * Return all flowers in the map
	 *
	 * @return ArrayList of flowers in the map
	 */
	public ArrayList<CellNode> getFlowers() {
		return cells.stream().filter(c -> c.getType().equals(CellType.NODE)).map(c -> (CellNode) c).collect(Collectors.toCollection(ArrayList::new));
	}

	public ArrayList<Cell> getNodes() {
		return cells.stream().filter(c -> c.getType().equals(CellType.NODE)).collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Get the cell at position (x, y)
	 *
	 * @param x X position of cell
	 * @param y Y position of cell
	 * @return Cell at (x, y)
	 */
	public Cell getCell(int x, int y) {
		for (Cell c : cells) {
			if (c.getX() == x && c.getY() == y) {
				return c;
			}
		}
		return null;
	}

	public CostMatrix getCostMatrix() {
		if (costMatrix == null) {
			setCostMatrix();
		}
		return costMatrix;
	}

	//SET METHODS

	/**
	 * Set the type of a cell at position (x, y)
	 *
	 * @param x    X position of cell
	 * @param y    Y position of cell
	 * @param type Type of cell
	 */
	public void setCell(int x, int y, CellType type) {
		cells.remove(getCell(x,y));
		switch (type) {
		case EMPTY:
			cells.add(new CellEmpty(x,y));
			break;
		case ORIGIN:
			CellOrigin hive = new CellOrigin(x,y);
			cells.add(hive);
			this.hive = hive;
			break;
		case NODE:
			cells.add(new CellNode(x,y));
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
		setCell(x, y, CellType.EMPTY);
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
	 * Fill the map will empty cells
	 */
	public void setMap() {
		cells = new ArrayList<>();
	}

	/**
	 * Return the hive for the map
	 *
	 * @return Map's hive cell
	 */
	public CellOrigin getHive() {
		return hive;
	}

	public void setCostMatrix() {
		costMatrix = new CostMatrix(this);
	}
}
