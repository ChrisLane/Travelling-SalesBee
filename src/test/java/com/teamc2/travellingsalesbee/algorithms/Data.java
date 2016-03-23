package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import com.teamc2.travellingsalesbee.gui.data.cells.CellNode;
import com.teamc2.travellingsalesbee.gui.data.cells.CellOrigin;
import com.teamc2.travellingsalesbee.gui.data.cells.CellType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;

/**
 * A class to contain all data providers
 */
public class Data {
	private static Map map;

	/**
	 * Setup data for the tests
	 */
	@BeforeClass
	public void initialise() {
		map = new Map();
		map.setCell(0, 0, CellType.ORIGIN);
		map.setCell(0, 2, CellType.NODE); // flower 1
		map.setCell(0, 6, CellType.NODE); // flower 2
		map.setCell(2, 6, CellType.NODE); // flower 3
	}

	/**
	 * Get the map containing nodes for the data providers
	 *
	 * @return The data provider's map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Paths and costs to be used in tests
	 *
	 * @return Array of paths and their costs to be used in tests
	 */
	@DataProvider(name = "paths")
	public static Object[][] path() {
		ArrayList<CellNode> flowers = map.getNodes();
		CellNode flower1 = flowers.get(0);
		CellNode flower2 = flowers.get(1);
		CellNode flower3 = flowers.get(2);
		CellOrigin hive = map.getOrigin();

		double path1Cost = 14.32455532033676;
		ArrayList<Cell> path1 = new ArrayList<>();
		path1.add(hive);
		path1.add(flower1); // cost 2
		path1.add(flower2); // cost 4
		path1.add(flower3); // cost 2
		path1.add(hive); // cost 6.32455532033676

		double path2Cost = 14.32455532033676;
		ArrayList<Cell> path2 = new ArrayList<>();
		path2.add(hive);
		path2.add(flower3); // cost 6.324555397033691
		path2.add(flower2); // cost 2
		path2.add(flower1); // cost 4
		path2.add(hive); // cost 2

		double path3Cost = 20.79669127533634;
		ArrayList<Cell> path3 = new ArrayList<>();
		path3.add(hive);
		path3.add(flower2); // cost 6
		path3.add(flower1); // cost 4
		path3.add(flower3); // cost 4.4721360206604
		path3.add(hive); // cost 6.32455532033676

		double path4Cost = 20.79669127533634;
		ArrayList<Cell> path4 = new ArrayList<>();
		path4.add(hive);
		path4.add(flower3); // cost 6.32455532033676
		path4.add(flower1); // cost 4.4721360206604
		path4.add(flower2); // cost 4
		path4.add(hive); // cost 6

		return new Object[][]{{path1, path1Cost}, {path2, path2Cost}, {path3, path3Cost}, {path4, path4Cost}};
	}
}
