package com.teamc2.travellingsalesbee.gui.data;

import com.teamc2.travellingsalesbee.algorithms.cost.CostMatrix;
import com.teamc2.travellingsalesbee.gui.data.cells.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Map extends JPanel {

	private ArrayList<Cell> cells; //Will store our ElementCells
	private CellOrigin origin;
	private CostMatrix costMatrix;

	/**
	 * Create a new map
	 */
	public Map() {
		cells = new ArrayList<>();
		setMap();
	}

	//GET METHODS

	/**
	 * Return all nodes in the map
	 *
	 * @return ArrayList of nodes in the map
	 */
	public ArrayList<CellNode> getNodes() {
		return cells.stream().filter(c -> c.getType().equals(CellType.NODE)).map(c -> (CellNode) c).collect(Collectors.toCollection(ArrayList::new));
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

	public void setCostMatrix(CostMatrix costMatrix) {
		this.costMatrix = costMatrix;
	}

	/**
	 * Set the type of a cell at position (x, y)
	 *
	 * @param x    X position of cell
	 * @param y    Y position of cell
	 * @param type Type of cell
	 */
	public void setCell(int x, int y, CellType type) {
		cells.remove(getCell(x, y));
		switch (type) {
			case EMPTY:
				cells.add(new CellEmpty(x, y));
				break;
			case ORIGIN:
				CellOrigin origin = new CellOrigin(x, y);
				cells.add(origin);
				this.origin = origin;
				break;
			case NODE:
				cells.add(new CellNode(x, y));
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
	 * Fill the map will empty cells
	 */
	public void setMap() {
		cells = new ArrayList<>();
	}

	/**
	 * Return the origin for the map
	 *
	 * @return Map's origin cell
	 */
	public CellOrigin getOrigin() {
		return origin;
	}

	public void setCostMatrix() {
		costMatrix = new CostMatrix(this);
	}
}
