package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.CellNode;
import com.teamc2.travellingsalesbee.gui.data.cells.CellOrigin;
import com.teamc2.travellingsalesbee.gui.data.cells.CellType;
import org.testng.Assert;
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
	 * Test that the average cost calculated matches the expected value
	 *
	 * @param map           Map to calculate the average cost from the origin to all nodes
	 * @param actualAverage The expected average cost for the map
	 */
	@Test(dataProvider = "averageCost", dataProviderClass = Data.class)
	public void getAverageCost(Map map, double actualAverage) {
		Ant ant = new Ant(map);
		ArrayList<CellNode> nodes = map.getNodes();
		CellOrigin origin = map.getOrigin();

		double averageCost = ant.getAverageCost(nodes, origin);
		Assert.assertEquals(averageCost, actualAverage);
	}

	/**
	 * Test that the average pheromone level calculated matches the expected value
	 *
	 * @param map           Map to calculate the average pheromone level from origin to all nodes
	 * @param actualAverage The expected average pheromone level for the map
	 */
	@Test(dataProvider = "averagePheromone", dataProviderClass = Data.class)
	public void getAveragePheromone(Map map, double actualAverage) {
		Ant ant = new Ant(map);
		ArrayList<CellNode> nodes = map.getNodes();
		CellOrigin origin = map.getOrigin();

		ant.pheromoneRun();
		double averagePheromone = ant.getAveragePheromone(nodes, origin);
		Assert.assertEquals(averagePheromone, actualAverage);
	}

	/**
	 * Test that the heuristic cost gets set correctly
	 *
	 * @param cost         The input cost
	 * @param expectedCost The expected output cost
	 */
	@Test(dataProvider = "cost", dataProviderClass = Data.class)
	public void setHeuristicCost(double cost, double expectedCost) {
		Map map = new Map();
		map.setCell(0, 0, CellType.ORIGIN);
		map.setCell(0, 2, CellType.NODE);
		Ant ant = new Ant(map);

		ant.setHeuristicCost(cost);
		double actualCost = ant.getHeuristicCost();
		Assert.assertEquals(actualCost, expectedCost);
	}

	/**
	 * test that the map returned from getMap is the same as the initial map without change
	 *
	 * @param map         The input map
	 * @param expectedMap The map to be checked against
	 */
	@Test(dataProvider = "map", dataProviderClass = Data.class)
	public void getMap(Map map, Map expectedMap) {
		Ant ant = new Ant(map);
		Assert.assertEquals(ant.getMap(), expectedMap);
	}
}