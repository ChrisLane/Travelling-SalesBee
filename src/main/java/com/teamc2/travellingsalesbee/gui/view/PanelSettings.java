package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.algorithms.Bee;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.CellDraggable;
import com.teamc2.travellingsalesbee.visualisation.BeeVisualiser;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static com.teamc2.travellingsalesbee.gui.data.cells.CellType.FLOWER;
import static com.teamc2.travellingsalesbee.gui.data.cells.CellType.HIVE;

public class PanelSettings extends JPanel {

	private final PanelMap panelMap;
	private Map map;

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

			Text text = new Text("\nExplanatory step-through text will appear here.");
			text.setWrappingWidth(txtPaneTextWillAppear.getWidth());
			root.getChildren().add(text);
			text.setId("infobox");
			txtPaneTextWillAppear.setScene(scene);
		});

		add(txtPaneTextWillAppear);

		JButton btnPrev = new JButton("<-");
		btnPrev.addActionListener(arg0 -> {
			stepNum--;
			panelMap.getPathComponent().setStepNumber(stepNum);

			/*----------------------------------------------*/
			try{
				panelMap.getPanelAnimalAnimation().setStepNum(stepNum);
			}catch(IndexOutOfBoundsException e){
				System.err.println("Exception in setting animation");
				e.printStackTrace();
			}
			/*----------------------------------------------*/
		});

		JButton btnNext = new JButton("->");
		btnNext.addActionListener(arg0 -> {
			stepNum++;
			panelMap.getPathComponent().setStepNumber(stepNum);

			/*----------------------------------------------*/
			try{
				panelMap.getPanelAnimalAnimation().setStepNum(stepNum);
			}catch(IndexOutOfBoundsException e){
				System.err.println("Exception in setting animation");
				e.printStackTrace();
			}
			/*----------------------------------------------*/
		});

		LayoutSettings layoutSettings = new LayoutSettings(this, infoLabel, lblExperimentRuns, lblNoOfRuns, slider,
				btnRun, btnPrev, btnNext, txtPaneTextWillAppear);

		setLayout(layoutSettings);
	}

	private class runActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			map.setCostMatrix();
			Bee bee = new Bee(map, experimentalRuns);
			BeeVisualiser visualise = new BeeVisualiser();
			bee.naiveRun();
			panelMap.getPathComponent().setNaiveSteps(visualise.getNaiveSteps(bee.getPath()));
			bee.experimentalRun();
			panelMap.getPathComponent().setExperimentalSteps(visualise.getExperimentalSteps(bee.getCellComparisons(), bee.getIntermediaryPaths()));
			panelMap.getPathComponent().setPath(bee.getPath());

			/*----------------------------------------------*/
				panelMap.getPanelAnimalAnimation().setPath(bee.getPath());
			/*----------------------------------------------*/
		}
	}
}
