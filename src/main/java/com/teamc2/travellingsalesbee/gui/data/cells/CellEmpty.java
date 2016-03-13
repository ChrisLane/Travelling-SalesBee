package com.teamc2.travellingsalesbee.gui.data.cells;

import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;

import java.awt.*;

public class CellEmpty extends Cell {

	/**
	 * Create a new empty cell
	 */
	public CellEmpty() {
	}

	/**
	 * Create a new empty cell
	 *
	 * @param x X position of cell
	 * @param y Y position of cell
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