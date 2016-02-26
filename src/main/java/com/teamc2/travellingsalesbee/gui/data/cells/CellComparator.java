package com.teamc2.travellingsalesbee.gui.data.cells;

import java.util.Comparator;

public class CellComparator implements Comparator<Cell> {

	/**
	 * Compares and returns the order of two cells
	 *
	 * @param c1 First cell in comparison
	 * @param c2 Second cell in comparison
	 * @return Position of cell 1 compared to cell 2
	 */
	@Override
	public int compare(Cell c1, Cell c2) {
		int xComparison = Integer.compare((int) c1.x, (int) c2.x);
		if (xComparison == 0) {
			return Integer.compare((int) c1.y, (int) c2.y);
		} else {
			return xComparison;
		}
	}
}
