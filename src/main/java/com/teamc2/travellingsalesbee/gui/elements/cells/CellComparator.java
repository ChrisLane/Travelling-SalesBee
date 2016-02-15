package com.teamc2.travellingsalesbee.gui.elements.cells;

import java.util.Comparator;

public class CellComparator implements Comparator<Cell> {
	@Override
	public int compare(Cell o1, Cell o2) {
		int xComparison = Integer.compare((int) o1.x, (int) o2.x);
		if (xComparison == 0) {
			return Integer.compare((int) o1.y, (int) o2.y);
		} else {
			return xComparison;
		}
	}
}
