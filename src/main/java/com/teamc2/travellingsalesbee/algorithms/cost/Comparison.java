package com.teamc2.travellingsalesbee.algorithms.cost;

/**
 * A class used to instantiate a comparison between two cells
 *
 * @param <Cell1> The first cell to compare against
 * @param <Cell2> The second cell to compare against the first
 * @author Bradley Rowe (bmr455)
 */
public class Comparison<Cell1, Cell2> {
	private final Cell1 cell1;
	private final Cell2 cell2;

	/**
	 * Constructs a comparison between that stores two objects passed into it
	 *
	 * @param cell1 The first cell passed into the comparison.
	 * @param cell2 The second cell passed into the comparison.
	 */
	public Comparison(Cell1 cell1, Cell2 cell2) {
		this.cell1 = cell1;
		this.cell2 = cell2;
	}

	/**
	 * @return cell1. The first cell in a comparison
	 */
	public Cell1 getCell1() {
		return cell1;
	}

	/**
	 * @return cell2. The second cell in a comparison
	 */
	public Cell2 getCell2() {
		return cell2;
	}
}
