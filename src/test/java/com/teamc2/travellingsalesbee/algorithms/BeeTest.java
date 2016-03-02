package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class BeeTest {
	private Bee bee;
	private Map map;

	@BeforeClass
	public void initialise() {
		map = new Map(10, 10);
		map.setCell(0, 0, CellType.HIVE);
		map.setCell(0, 2, CellType.FLOWER); // flower 1
		map.setCell(0, 6, CellType.FLOWER); // flower 2
		map.setCell(2, 6, CellType.FLOWER); // flower 3

		bee = new Bee(map, 20);
	}

	@DataProvider(name = "paths")
	public Object[][] path() {
		ArrayList<CellFlower> flowers = map.getFlowers();
		CellFlower flower1 = flowers.get(0);
		CellFlower flower2 = flowers.get(1);
		CellFlower flower3 = flowers.get(2);
		CellHive hive = map.getHive();

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

	/**
	 * Test that each flower is contained in the path generated
	 */
	@Test(dependsOnMethods = {"testSetPath", "testSetPathCost", "testCalculatePathCost"})
	public void testNaiveRun() throws Exception {
		// TODO: Change this to include the hive
		// Set the path to empty
		bee.setPath(new ArrayList<>(), 0);

		// Generate a new path
		bee.naiveRun();

		// Sort the path and remove hive
		ArrayList<Cell> path = bee.getPath();
		path.remove(0);
		path.remove(path.size()-1);
		path.sort(new CellComparator());

		// Sort the flowers
		ArrayList<CellFlower> flowers = map.getFlowers();
		flowers.sort(new CellComparator());

		Assert.assertEquals(path.equals(flowers), true);
	}

	@Test(dataProvider = "paths", dependsOnMethods = "testSetPathCost")
	public void testGetPathCost(ArrayList<Cell> path, double cost) throws Exception {
		bee.setPath(path, cost);
		Assert.assertEquals(bee.getPathCost(), cost);
	}

	@Test(dataProvider = "paths")
	public void testCalculatePathCost(ArrayList<Cell> path, double cost) throws Exception {
		Assert.assertEquals(bee.calculatePathCost(path), cost);
	}

	@Test(dataProvider = "paths", dependsOnMethods = {"testSetPath", "testSetPathCost", "testGetPathCost"})
	public void testExperimentalRuns(ArrayList<Cell> path, double cost) throws Exception {
		bee.setPath(path, cost);
		bee.experimentalRun();
		Assert.assertTrue(bee.getPathCost() <= cost);
	}

	@Test(dataProvider = "paths")
	public void testSetPath(ArrayList<Cell> path, double cost) throws Exception {
		bee.setPath(path, cost);
		Assert.assertEquals(bee.getPath(), path);
	}

	@Test(dataProvider = "paths")
	public void testSetPathCost(ArrayList<Cell> path, double cost) throws Exception {
		bee.setPath(path, cost);
		Assert.assertEquals(bee.getPathCost(), cost);
	}
}