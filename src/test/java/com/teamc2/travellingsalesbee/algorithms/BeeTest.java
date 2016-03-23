package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import com.teamc2.travellingsalesbee.gui.data.cells.CellComparator;
import com.teamc2.travellingsalesbee.gui.data.cells.CellNode;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * A test class for the bee algorithm
 *
 * @author Christopher Lane (cml476)
 */
public class BeeTest {
	private Bee bee;
	private Map map;

	@BeforeClass
	public void initialise() {
		Data data = new Data();
		map = data.getMap();
		bee = new Bee(map, 20);
	}

	/**
	 * Test that every node is contained in the path generated
	 */
	@Test(dependsOnMethods = {"testSetPath", "testSetPathCost", "testCalculatePathCost"})
	public void testNaiveRun() throws Exception {
		// TODO: Change this to include the origin
		// Set the path to empty
		bee.setPath(new ArrayList<>(), 0);

		// Generate a new path
		bee.naiveRun();

		// Sort the path and remove origin
		ArrayList<Cell> path = bee.getPath();
		// Remove start (origin)
		path.remove(0);
		// Remove finish (origin)
		path.remove(path.size() - 1);
		path.sort(new CellComparator());

		// Sort the flowers
		ArrayList<CellNode> flowers = map.getNodes();
		flowers.sort(new CellComparator());

		Assert.assertEquals(path.equals(flowers), true);
	}

	/**
	 * Test that costs are being calculated correctly
	 *
	 * @param path Input paths
	 * @param cost Costs to be matched
	 */
	@Test(dataProvider = "paths", dataProviderClass = Data.class)
	public void testCalculatePathCost(ArrayList<Cell> path, double cost) throws Exception {
		Assert.assertEquals(bee.calculatePathCost(path), cost);
	}

	/**
	 * Test that experimental doesn't return a path of larger cost
	 *
	 * @param path Initial path
	 * @param cost Initial cost
	 */
	@Test(dataProvider = "paths", dataProviderClass = Data.class, dependsOnMethods = {"testSetPath", "testSetPathCost"})
	public void testExperimentalRuns(ArrayList<Cell> path, double cost) throws Exception {
		bee.setPath(path, cost);
		bee.experimentalRun();
		Assert.assertTrue(bee.getCost() <= cost);
	}

	/**
	 * Test that the stored path is the same as the path we set
	 *
	 * @param path Path to set
	 * @param cost Path cost to set
	 */
	@Test(dataProvider = "paths", dataProviderClass = Data.class)
	public void testSetPath(ArrayList<Cell> path, double cost) throws Exception {
		bee.setPath(path, cost);
		Assert.assertEquals(bee.getPath(), path);
	}

	/**
	 * Test that the stored path cost is the same as the cost we set
	 *
	 * @param path Path to set
	 * @param cost Path cost to set
	 */
	@Test(dataProvider = "paths", dataProviderClass = Data.class)
	public void testSetPathCost(ArrayList<Cell> path, double cost) throws Exception {
		bee.setPath(path, cost);
		Assert.assertEquals(bee.getCost(), cost);
	}
}