package com.teamc2.travellingsalesbee.gui.data.cells;

import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;

import java.awt.*;

/**
 * A class for cells of no type
 *
 * @author Christopher Lane (cml476)
 */
public class CellEmpty extends Cell {

	/**
	 * Create a new empty cell
	 */
	public CellEmpty() {
		//
	}

	/**
	 * Create a new empty cell
	 *
	 * @param x X position of the cell
	 * @param y Y position of the cell
	 */
	public CellEmpty(double x, double y) {
		super(x, y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CellType getType() {
		return CellType.EMPTY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image getImage(AlgorithmType type) {
		return null;
	}

}