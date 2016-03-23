package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.CellNode;
import com.teamc2.travellingsalesbee.gui.data.cells.CellOrigin;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * A test class for the ant colony algorithm
 *
 * @author Christopher Lane (cml476)
 */
@Test(dependsOnGroups = "NearestNeighbour")
public class AntTest {

	/**
	 * Setup the environment for testing
	 */
	@BeforeClass
	public void initialise() {
		Data data = new Data();
	}

	@Test(dataProvider = "averageCost", dataProviderClass = Data.class)
	public void getAverageCost(Map map, double actualAverage) {
		Ant ant = new Ant(map);
		ArrayList<CellNode> nodes = map.getNodes();
		CellOrigin origin = map.getOrigin();

		double averageCost = ant.getAverageCost(nodes, origin);
		Assert.assertEquals(averageCost, actualAverage);
	}

	@Test(dataProvider = "averagePheromone", dataProviderClass = Data.class)
	public void getAveragePheromone(Map map, double actualAverage) {
		Ant ant = new Ant(map);
		ArrayList<CellNode> nodes = map.getNodes();
		CellOrigin origin = map.getOrigin();

		ant.pheromoneRun();
		double averagePheromone = ant.getAveragePheromone(nodes, origin);
		Assert.assertEquals(averagePheromone, actualAverage);
	}
}