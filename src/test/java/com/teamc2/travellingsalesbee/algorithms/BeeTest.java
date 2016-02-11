package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.gui.elements.Map;
import com.teamc2.travellingsalesbee.gui.elements.cells.Cell;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellFlower;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellHive;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class BeeTest {
	private Bee bee;
	private Map map;
	private ArrayList<Cell> path;

	@BeforeClass
	public void initialise() {
		map = new Map(10, 10);
		map.setCell(0, 0, Cell.CellType.HIVE);
		map.setCell(0, 2, Cell.CellType.FLOWER);
		map.setCell(0, 6, Cell.CellType.FLOWER);
		map.setCell(2, 6, Cell.CellType.FLOWER);

		bee = new Bee(map, map.getHive(), 5);
	}

	@DataProvider (name = "paths")
	public Object[][] path() {
		ArrayList<CellFlower> flowers = map.getFlowers();
		CellFlower flower1 = flowers.get(0);
		CellFlower flower2 = flowers.get(1);
		CellFlower flower3 = flowers.get(2);
		CellHive hive = map.getHive();

		int path1Cost = 8;
		ArrayList<Cell> path1 = new ArrayList<>();
		path1.add(hive);
		path1.add(flower1);
		path1.add(flower2);
		path1.add(flower3);

		int path2Cost = 12;
		ArrayList<Cell> path2 = new ArrayList<>();
		path2.add(hive);
		path2.add(flower3);
		path2.add(flower2);
		path2.add(flower1);

		int path3Cost = 14;
		ArrayList<Cell> path3 = new ArrayList<>();
		path3.add(hive);
		path3.add(flower2);
		path3.add(flower1);
		path3.add(flower3);

		return new Object[][] {{path1, path1Cost}, {path2, path2Cost}, {path3, path3Cost}};
	}

	@Test
	public void testNaiveRun() throws Exception {

	}

	@Test
	public void testGetPathCost() throws Exception {

	}

	@Test (dataProvider = "paths")
	public void testCalculatePathCost(ArrayList<Cell> path, int cost) throws Exception {
		Assert.assertEquals(bee.calculatePathCost(path), cost);
	}

	@Test
	public void testExperimentalRuns() throws Exception {

	}


	@Test (dataProvider = "paths")
	public void testSetPath(ArrayList<Cell> path, int cost) throws Exception {
		bee.setPath(path, cost);
		Assert.assertEquals(bee.getPath(), path);
		Assert.assertEquals(bee.getPathCost(), cost);
	}
}