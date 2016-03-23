package com.teamc2.travellingsalesbee.gui.view.settings;

import com.teamc2.travellingsalesbee.gui.view.map.PanelMap;

import javax.swing.*;
import java.awt.*;

/**
 * A class to contain the settings panel sliders
 */
public class SettingsSliders {

	private final PanelSettings panelSettings;
	private JSlider noOfRunsSlider;
	private JLabel lblRunsOfType;
	private JLabel lblNoOfRuns;
	private JLabel lblAnimationSpeed;
	private JLabel lblSpeed;

	/**
	 * Construct a new container of settings panel sliders
	 *
	 * @param panelSettings The settings panel
	 * @param panelMap      The map panel
	 */
	public SettingsSliders(PanelSettings panelSettings, PanelMap panelMap) {
		this.panelSettings = panelSettings;

		createRunCountSlider();
	}

	/**
	 * Create a run count slider
	 */
	public void createRunCountSlider() {
		noOfRunsSlider = new JSlider();
		noOfRunsSlider.setValue(panelSettings.getNoOfRunsValue());
		noOfRunsSlider.setOpaque(false);

		lblRunsOfType = new JLabel("Experiment Runs: ");
		lblRunsOfType.setFont(new Font("Amatic Bold", Font.PLAIN, 25));
		lblRunsOfType.setForeground(Color.WHITE);

		lblNoOfRuns = new JLabel("" + panelSettings.getNoOfRunsValue());
		lblNoOfRuns.setFont(new Font("Amatic Bold", Font.PLAIN, 25));
		lblNoOfRuns.setForeground(Color.WHITE);

		noOfRunsSlider.addChangeListener(arg0 -> {
			panelSettings.setNoOfRunsValue(noOfRunsSlider.getValue());
			lblNoOfRuns.setText(("" + panelSettings.getNoOfRunsValue()));
		});
	}

	/**
	 * Update the run count slider details
	 */
	public void updateSliderDetails() {
		switch (panelSettings.getAlgorithmType()) {
			case BEE:
				noOfRunsSlider.setVisible(true);
				noOfRunsSlider.setMinimum(0);
				noOfRunsSlider.setMaximum(100);
				noOfRunsSlider.setValue(26);
				lblRunsOfType.setText("Experimental Runs:");
				break;
			case ANT:
				noOfRunsSlider.setVisible(true);
				noOfRunsSlider.setMinimum(1);
				noOfRunsSlider.setMaximum(4);
				noOfRunsSlider.setValue(4);
				lblRunsOfType.setText("No. of Ants:");
				break;
			case NEARESTNEIGHBOUR:
				noOfRunsSlider.setVisible(false);
				lblRunsOfType.setText("");
				lblNoOfRuns.setText("");
				break;
			case TWOOPT:
				noOfRunsSlider.setVisible(true);
				noOfRunsSlider.setMinimum(0);
				noOfRunsSlider.setMaximum(100);
				noOfRunsSlider.setValue(50);
				lblRunsOfType.setText("Swap Runs:");
				break;
		}
	}

	/**
	 * Get the number of runs slider
	 *
	 * @return The number of runs slider
	 */
	public JSlider getNoOfRunsSlider() {
		return noOfRunsSlider;
	}

	/**
	 * Get the runs of type label
	 *
	 * @return The runs of type label
	 */
	public JLabel getLblRunsOfType() {
		return lblRunsOfType;
	}

	/**
	 * Get the number of runs label
	 *
	 * @return The number of runs label
	 */
	public JLabel getLblNoOfRuns() {
		return lblNoOfRuns;
	}

	/**
	 * Get the animation speed label
	 *
	 * @return The animation speed label
	 */
	public JLabel getLblAnimationSpeed() {
		return lblAnimationSpeed;
	}

	/**
	 * Get the speed label
	 *
	 * @return The speed label
	 */
	public JLabel getLblSpeed() {
		return lblSpeed;
	}
}
