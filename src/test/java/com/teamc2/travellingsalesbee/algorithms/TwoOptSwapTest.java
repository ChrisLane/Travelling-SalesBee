package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.gui.data.Map;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * A test class for the two opt swap algorithm
 */
@Test(dependsOnGroups = "NearestNeighbour")
public class TwoOptSwapTest {

	/**
	 * Test that runs returned are the same as when the object was instantiated
	 *
	 * @param map  The map to initialise the TwoOptSwap
	 * @param runs The number of runs to set
	 */
	@Test(dataProvider = "runs", dataProviderClass = Data.class)
	public void getRuns(Map map, int runs) throws Exception {
		TwoOptSwap twoOptSwap = new TwoOptSwap(map, runs);
		Assert.assertEquals(twoOptSwap.getRuns(), runs);
	}

	/**
	 * Test that the step number gets set correctly
	 *
	 * @param map     The map to initialise the TwoOptSwap
	 * @param stepNum The step number to set
	 */
	@Test(dataProvider = "stepNumber", dataProviderClass = Data.class)
	public void setStepNum(Map map, int stepNum) throws Exception {
		TwoOptSwap twoOptSwap = new TwoOptSwap(map, 10);
		twoOptSwap.setStepNum(stepNum);
		Assert.assertEquals(twoOptSwap.getStepNum(), stepNum);
	}
}