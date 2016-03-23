package com.teamc2.travellingsalesbee.gui.data;

import com.teamc2.travellingsalesbee.algorithms.cost.CostMatrix;
import com.teamc2.travellingsalesbee.gui.data.cells.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * The map containing nodes and a cost matrix
 *
 * @author Chris Lane (cml476)
 * @author Todd Waugh Ambridge (txw467)
 */
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

	/**
	 * Get all nodes in the map
	 *
	 * @return All nodes in the map
	 */
	public ArrayList<CellNode> getNodes() {
		return cells.stream().filter(c -> c.getType().equals(CellType.NODE)).map(c -> (CellNode) c).collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Get the cell at position (x, y)
	 *
	 * @param x X position of cell
	 * @param y Y position of cell
	 * @return Cell at position (x, y)
	 */
	public Cell getCell(int x, int y) {
		for (Cell c : cells) {
			if (c.getX() == x && c.getY() == y) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Get the cost matrix
	 *
	 * @return The cost matrix
	 */
	public CostMatrix getCostMatrix() {
		if (costMatrix == null) {
			setCostMatrix();
		}
		return costMatrix;
	}

	/**
	 * Set the cost matrix
	 *
	 * @param costMatrix Set the cost matrix
	 */
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
	 * Set the map to empty
	 */
	public void setMap() {
		cells = new ArrayList<>();
	}

	/**
	 * Return the origin cell
	 *
	 * @return Map's origin cell
	 */
	public CellOrigin getOrigin() {
		return origin;
	}

	/**
	 * Set a new cost matrix
	 */
	public void setCostMatrix() {
		costMatrix = new CostMatrix(this);
	}
}
