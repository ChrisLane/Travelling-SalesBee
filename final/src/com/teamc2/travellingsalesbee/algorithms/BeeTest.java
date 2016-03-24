package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * A test class for the bee algorithm
 *
 * @author Christopher Lane (cml476)
 */
@Test(dependsOnGroups = "NearestNeighbour")
public class BeeTest {
	private Bee bee;

	/**
	 * Setup the environment for testing
	 */
	@BeforeClass
	public void initialise() {
		Data data = new Data();
		Map map = data.getMap();
		bee = new Bee(map, 20);
	}

	/**
	 * Test that experimental run doesn't return a path of larger cost
	 *
	 * @param path Initial path
	 * @param cost Initial cost
	 */
	@Test(dataProvider = "paths", dataProviderClass = Data.class)
	public void testExperimentalRuns(ArrayList<Cell> path, double cost) throws Exception {
		bee.setPath(path, cost);
		bee.experimentalRun();
		Assert.assertTrue(bee.getCost() <= cost);
	}
}