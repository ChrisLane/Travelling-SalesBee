package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class PanelAnimalAnimation extends JPanel {

	private String url;
	private int width;
	private int height;
	private ArrayList<Cell> path;
	private int stepNum = 0;
	private Rectangle beeIcon;
	private TranslateTransition transition;

	/**
	 * Create a new animal animation panel
	 *
	 * @param width  Width of panel
	 * @param height Height of panel
	 */
	public PanelAnimalAnimation(int width, int height) {

		this.width = width;
		this.height = height;

		initialize();
	}

	public void initialize() {

		final JFXPanel fxPanel = new JFXPanel();

		this.add(fxPanel);
		this.setVisible(true);

		// Size of parent-panel
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(new Color(0, 0, 0, 0));
		this.setOpaque(false);

		Platform.runLater(() -> initFX(fxPanel));
	}

	private void initFX(JFXPanel fxPanel) {
		Scene scene = initScene();
		fxPanel.setScene(scene);
	}

	private Scene initScene() {
		Pane root = new Pane();
		root.setId("beePane");

		Scene scene = new Scene(root, width, height);
		scene.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0));
		URL url1 = this.getClass().getResource("/assets/stylesheets/visualiser.css");
		scene.getStylesheets().add(url1.toExternalForm());

		beeIcon = createRectangle();
		transition = new TranslateTransition(Duration.seconds(1), beeIcon);
		root.getChildren().add(beeIcon);
		beeIcon.toFront();

		url = "/assets/icons/SalesBee.png";

		Image image = new Image(url);
		beeIcon.setFill(new ImagePattern(image, 0, 0, 1, 1, true));

		return scene;
	}

	private Rectangle createRectangle() {
		final Rectangle rectangle = new Rectangle(-20, -20, 45, 45);
		rectangle.setOpacity(1);

		return rectangle;
	}

	private void moveFromAToB(Cell end, Rectangle circle, TranslateTransition transition) {
		transition.setToX(end.getX() - circle.getX());
		transition.setToY(end.getY() - circle.getY());
		transition.playFromStart();
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPath(ArrayList<Cell> path) {
		this.path = path;

		Platform.runLater(() -> {
			beeIcon.setX(path.get(stepNum).getX());
			beeIcon.setY(path.get(stepNum).getY());
		});
	}

	public void setStepNum(int step) {
		this.stepNum = step;

		moveFromAToB(path.get(stepNum), beeIcon, transition);
	}


	//public void setMoves()
}