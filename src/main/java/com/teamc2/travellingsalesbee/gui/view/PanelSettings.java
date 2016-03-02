package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.algorithms.Bee;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import com.teamc2.travellingsalesbee.gui.data.cells.CellDraggable;
import com.teamc2.travellingsalesbee.visualisation.BeeVisualiser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static com.teamc2.travellingsalesbee.gui.data.cells.CellType.FLOWER;
import static com.teamc2.travellingsalesbee.gui.data.cells.CellType.HIVE;

public class PanelSettings extends JPanel {

	private final PanelMap panelMap;

	private JLabel infoLabel;

	private int experimentalRuns = 26; //Set to 26 by default
	private int stepNum = 0;

	/**
	 * Create a settings panel
	 *
	 * @param panelMap Map JPanel
	 */
	public PanelSettings(PanelMap panelMap) {
		this.panelMap = panelMap;

		setBackground(Color.LIGHT_GRAY);
		addSettingsInfo();
		addButtons();
	}

	/**
	 * Add the background to the settings panel
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		try {
			BufferedImage img = ImageIO.read(this.getClass().getResource("/assets/backgrounds/GreyBack150.png"));
			TexturePaint paint = new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
			g2.setPaint(paint);
			g2.fill(new Rectangle(0, 0, getWidth(), getHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add information on how to use the settings panel
	 */
	public void addSettingsInfo() {
		infoLabel = new JLabel("Settings box, we also need to choose a background image for this");
	}

	/**
	 * Add the settings buttons to the panel
	 */
	public void addButtons() {
		JButton btnRun = new JButton("RUN");
		btnRun.addActionListener(new runActionListener());
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JSlider slider = new JSlider();
		slider.setValue(experimentalRuns);
		JLabel lblExperimentRuns = new JLabel("Experiment Runs: ");
		JLabel lblNoOfRuns = new JLabel("" + experimentalRuns);

		slider.addChangeListener(arg0 -> {
			slider.setValue(slider.getValue());
			experimentalRuns = slider.getValue();
			lblNoOfRuns.setText(("" + experimentalRuns));
		});

		JTextPane txtPaneTextWillAppear = new JTextPane();
		txtPaneTextWillAppear.setText("Text will appear here as you step through the algorithm, explaining how it works at each step");

		JButton btnPrev = new JButton("<-");
		btnPrev.addActionListener(arg0 -> {
			stepNum--;
			panelMap.getPathComponent().setStepNumber(stepNum);

			/*----------------------------------------------*/
			panelMap.getPanelAnimalAnimation().setStepNum(stepNum);
			/*----------------------------------------------*/
		});

		JButton btnNext = new JButton("->");
		btnNext.addActionListener(arg0 -> {
			stepNum++;
			panelMap.getPathComponent().setStepNumber(stepNum);

			/*----------------------------------------------*/
				panelMap.getPanelAnimalAnimation().setStepNum(stepNum);
			/*----------------------------------------------*/
		});

		LayoutSettings layoutSettings = new LayoutSettings(this, infoLabel, lblExperimentRuns, lblNoOfRuns, slider,
				btnRun, btnPrev, btnNext, txtPaneTextWillAppear);

		setLayout(layoutSettings);
	}

	private class runActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {

			//Get parent width and height
			int panelWidth = panelMap.getWidth();
			int panelHeight = panelMap.getHeight();

			//Gets the amount of cells respective to the width and height of the map
			Map map = new Map(panelWidth, panelHeight); //Initialising Map with cellsX and cellsY as width and height of map

			//Add all cells to the map
			for (Component c : panelMap.getComponents()) {
				if (c instanceof CellDraggable) {
					if (c.isEnabled() && ((CellDraggable) c).getType().equals(FLOWER)) {
						map.setCell(c.getX(), c.getY(), FLOWER); //Add flower positions to map
					} else if (c.isEnabled() && ((CellDraggable) c).getType().equals(HIVE)) {
						map.setCell(c.getX(), c.getY(), HIVE); //Add hive position to map
					}
				}
			}

			Bee bee = new Bee(map, experimentalRuns);
			BeeVisualiser visualise = new BeeVisualiser();
			bee.naiveRun();
			panelMap.getPathComponent().setNaiveSteps(visualise.getNaiveSteps(bee.getPath()));
			bee.experimentalRun();
			panelMap.getPathComponent().setExperimentalSteps(visualise.getExperimentalSteps(bee.getCellComparisons(), bee.getIntemediaryPaths()));
			panelMap.getPathComponent().setPath(bee.getPath());

			/*----------------------------------------------*/
				panelMap.getPanelAnimalAnimation().setPath(bee.getPath());
			/*----------------------------------------------*/
		}
	}
}
