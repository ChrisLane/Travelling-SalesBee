package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.algorithms.Bee;
import com.teamc2.travellingsalesbee.gui.ExperimentalStep;
import com.teamc2.travellingsalesbee.gui.NaiveStep;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import com.teamc2.travellingsalesbee.visualisation.BeeVisualiser;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class PanelSettings extends JPanel {

	private final PanelMap panelMap;
	private Map map;

	private JLabel infoLabel;
	private JButton btnPrev;
	private JButton btnNext;
	private Text text;

	private int experimentalRuns = 26; //Set to 26 by default
	private int animationSpeed = 50;
	private int stepNum = 0;

	/**
	 * Create a settings panel
	 *
	 * @param panelMap Map JPanel
	 */
	public PanelSettings(PanelMap panelMap) {
		this.panelMap = panelMap;
		map = panelMap.getMap();

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
			BufferedImage img = ImageIO.read(this.getClass().getResource("/assets/backgrounds/BrownBack150.png"));
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
		infoLabel = new JLabel("");
	}

	/**
	 * Add the settings buttons to the panel
	 */
	public void addButtons() {
		JButton btnRun = new JButton("RUN");
		btnRun.addActionListener(new runActionListener());
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JSlider experimentsSlider = new JSlider();
		experimentsSlider.setValue(experimentalRuns);
		experimentsSlider.setOpaque(false);
		JLabel lblExperimentRuns = new JLabel("Experiment Runs: ");
		JLabel lblNoOfRuns = new JLabel("" + experimentalRuns);

		experimentsSlider.addChangeListener(arg0 -> {
			experimentalRuns = experimentsSlider.getValue();
			lblNoOfRuns.setText(("" + experimentalRuns));
		});

		JSlider speedSlider = new JSlider();
		speedSlider.setValue(animationSpeed);
		speedSlider.setOpaque(false);
		JLabel lblAnimationSpeed = new JLabel("Animation Speed: ");
		JLabel lblSpeed = new JLabel("" + animationSpeed);

		speedSlider.addChangeListener(arg0 -> {
			speedSlider.setValue(speedSlider.getValue());
			animationSpeed = speedSlider.getValue();
			panelMap.getAnimation().setSpeed(animationSpeed);
			lblSpeed.setText(("" + animationSpeed));
		});

		final JFXPanel txtPaneTextWillAppear = new JFXPanel();

		// Size of parent-panel
		txtPaneTextWillAppear.setBackground(new Color(0, 0, 0, 0));
		txtPaneTextWillAppear.setOpaque(false);
		
		Platform.runLater(() -> {
			Pane root = new Pane();
			root.setId("infoboxPanel");

			Scene scene = new Scene(root);
			scene.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0));
			URL url1 = this.getClass().getResource("/assets/stylesheets/visualiser.css");
			scene.getStylesheets().add(url1.toExternalForm());

			text = new Text("\nExplanatory step-through text will appear here.");
			text.setWrappingWidth(txtPaneTextWillAppear.getWidth());
			root.getChildren().add(text);
			text.setId("infobox");
			txtPaneTextWillAppear.setScene(scene);
		});

		add(txtPaneTextWillAppear);

		btnPrev = new JButton("<-");
		btnNext = new JButton("->");
		
		btnPrev.setEnabled(false);
		btnNext.setEnabled(false);
		
		btnPrev.addActionListener(arg0 -> {
			stepNum--;
			panelMap.getPathComponent().setStepNumber(stepNum);
			Platform.runLater(() -> {
				text.setText(getDistance());
			});
			btnPrev.setEnabled((stepNum >= 0));
			btnNext.setEnabled(true);

			/*----------------------------------------------*/
			try {
				panelMap.getPanelAnimalAnimation().setStepNum(stepNum);
			} catch (IndexOutOfBoundsException e) {
				System.err.println("Exception in setting animation");
				e.printStackTrace();
			}
			/*----------------------------------------------*/
		});

		btnNext.addActionListener(arg0 -> {
			stepNum++;
			panelMap.getPathComponent().setStepNumber(stepNum);
			Platform.runLater(() -> {
				text.setText(getDistance());
			});
			btnPrev.setEnabled(true);
			//btnNext.setEnabled((stepNum != experimentalRuns));

			/*----------------------------------------------*/
			try {
				//if (stepNum < experimentalRuns) {
					panelMap.getPanelAnimalAnimation().setStepNum(stepNum);
				//}
			} catch (IndexOutOfBoundsException e) {
				System.err.println("Exception in setting animation");
				e.printStackTrace();
			}
			/*----------------------------------------------*/
		});

		LayoutSettings layoutSettings = new LayoutSettings(this, infoLabel, lblExperimentRuns, lblNoOfRuns, experimentsSlider,
				lblAnimationSpeed, lblSpeed, speedSlider, btnRun, btnPrev, btnNext, txtPaneTextWillAppear);

		setLayout(layoutSettings);
	}

	private class runActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			btnNext.setEnabled(true);
			
			map.setCostMatrix();
			Bee bee = new Bee(map, experimentalRuns);
			BeeVisualiser visualise = new BeeVisualiser();
			bee.naiveRun();
			ArrayList<NaiveStep> naiveSteps = visualise.getNaiveSteps(bee.getPath());
			panelMap.getPathComponent().setNaiveSteps(naiveSteps);


			bee.experimentalRun();
			ArrayList<ExperimentalStep> experimentalSteps = visualise.getExperimentalSteps(bee.getCellComparisons(), bee.getIntermediaryPaths(), bee.getIntermediaryPathCosts());
			panelMap.getPathComponent().setExperimentalSteps(experimentalSteps);
			panelMap.getPathComponent().setPath(bee.getPath()); // THIS DOES NOTHING

			ArrayList<ArrayList<Cell>> pathOfPaths = new ArrayList<>();
			ArrayList<Cell> hive = new ArrayList<>();
			hive.add(naiveSteps.get(0).getStart());
			pathOfPaths.add(hive);

			for (NaiveStep naiveStep : naiveSteps) {
				ArrayList<Cell> singlePoint = new ArrayList<>();
				singlePoint.add(naiveStep.getEnd());
				pathOfPaths.add(singlePoint);
			}

			for (ExperimentalStep experimentalStep : experimentalSteps) {
				ArrayList<Cell> setOfPoints = experimentalStep.getPath();
				pathOfPaths.add(setOfPoints);
			}
		
			/*----------------------------------------------*/
			panelMap.getPanelAnimalAnimation().setPathofPaths(pathOfPaths);
			//panelMap.getPanelAnimalAnimation().setPath(bee.getPath());
			/*----------------------------------------------*/
		}
	}

	private String getDistance() {
		// this will set the distance, we just need to get the proper path here
//		ArrayList<Cell> path = panelMap.getPathComponent().getBeePath();
//		Cell cell1 = path.get(stepNum-1);
//		Cell cell2 = path.get(stepNum);

		//return "Distance from step " + (stepNum-1) + " to " + stepNum + " was: " + cell1.distance(cell2) + ".";
		return "\nDistance from step " + (stepNum-1) + " to " + stepNum + " was: something.";
	}
}