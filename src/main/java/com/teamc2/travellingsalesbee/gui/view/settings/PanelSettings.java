package com.teamc2.travellingsalesbee.gui.view.settings;

import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;
import com.teamc2.travellingsalesbee.gui.view.layouts.LayoutSettings;
import com.teamc2.travellingsalesbee.gui.view.map.PanelMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * A class to contain settings controls for the settings panel
 *
 * @author Bradley Rowe (bmr455)
 * @author Melvyn Mathews (mxm499)
 */
public class PanelSettings extends JPanel {

	public final static String name = "PanelSettings";

	// Number of runs for a given algorithm
	//For BEE this is the number of experimental runs
	//For ANT this is the number of pheromone runs
	//For TWOOPT this is the number of swap runs
	private int noOfRunsValue = 26;

	private AlgorithmType algorithmType;
	private SettingsButtons settingsButtons;
	private SettingsSliders settingsSliders;

	/**
	 * Create a settings panel
	 *
	 * @param panelMap The PanelMap
	 */
	public PanelSettings(PanelMap panelMap) {

		setName(name);
		setBackground(Color.LIGHT_GRAY);
		setOpaque(false);

		settingsButtons = new SettingsButtons(this, panelMap);
		settingsSliders = new SettingsSliders(this, panelMap);

		setLayout();
	}

	/**
	 * Add the background to the settings panel
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		try {
			BufferedImage img = ImageIO.read(getClass().getResource("/assets/backgrounds/BrownBack150.png"));
			TexturePaint paint = new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
			g2.setPaint(paint);
			Dimension round = new Dimension(30, 30);
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), round.width, round.height);
			g2.setStroke(new BasicStroke(2));
			g2.drawRoundRect(0, 0, getWidth(), getHeight(), round.width, round.height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the layout for the settings panel
	 */
	public void setLayout() {
		LayoutSettings layoutSettings = new LayoutSettings(this, settingsSliders, settingsButtons);
		setLayout(layoutSettings);
	}

	/**
	 * Set the algorithm type
	 *
	 * @param type The algorithm type
	 */
	public void setAlgorithmType(AlgorithmType type) {
		this.algorithmType = type;
		settingsButtons.setStepNum(-1);
		settingsSliders.updateSliderDetails();
	}

	/**
	 * Get the algorithm type
	 *
	 * @return The algorithm type
	 */
	public AlgorithmType getAlgorithmType() {
		return algorithmType;
	}

	/**
	 * Get the number of runs
	 *
	 * @return The number of runs
	 */
	public int getNoOfRunsValue() {
		return noOfRunsValue;
	}

	/**
	 * Set the number of runs
	 *
	 * @param noOfRunsValue The number of values
	 */
	public void setNoOfRunsValue(int noOfRunsValue) {
		this.noOfRunsValue = noOfRunsValue;
	}
}