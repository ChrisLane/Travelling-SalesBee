package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.gui.data.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * A test class for the ant colony algorithm
 *
 * @author Christopher Lane (cml476)
 */
@Test(dependsOnGroups = "NearestNeighbour")
public class AntTest {
	private Ant ant;

	/**
	 * Setup the environment for testing
	 */
	@BeforeClass
	public void initialise() {
		Data data = new Data();
		Map map = data.getMap();
		ant = new Ant(map);
	}

	@Test
	public double getAverageCost() {
		return 0;
	}
}