package com.teamc2.travellingsalesbee.gui.view.settings;

import com.teamc2.travellingsalesbee.gui.view.map.PanelMap;

import javax.swing.*;
import java.awt.*;

public class SettingsSliders {

	private final PanelSettings panelSettings;
	private JSlider noOfRunsSlider;
	private JLabel lblRunsOfType;
	private JLabel lblNoOfRuns;
	private JLabel lblAnimationSpeed;
	private JLabel lblSpeed;

	public SettingsSliders(PanelSettings panelSettings, PanelMap panelMap) {
		this.panelSettings = panelSettings;

		createRunCountSlider();
	}

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

	public void updateSliderDetails() {
		switch (panelSettings.getType()) {
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

	public JSlider getNoOfRunsSlider() {
		return noOfRunsSlider;
	}

	public JLabel getLblRunsOfType() {
		return lblRunsOfType;
	}

	public JLabel getLblNoOfRuns() {
		return lblNoOfRuns;
	}

	public JLabel getLblAnimationSpeed() {
		return lblAnimationSpeed;
	}

	public JLabel getLblSpeed() {
		return lblSpeed;
	}
}
