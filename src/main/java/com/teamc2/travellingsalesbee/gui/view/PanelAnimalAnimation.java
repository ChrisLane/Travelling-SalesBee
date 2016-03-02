package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class PanelAnimalAnimation extends JPanel {

	private String url;
	private int x, y, width, height;
	private ArrayList<Cell> path;
	private int stepNum = 0;
	private Circle circle;
	private TranslateTransition transition;
	private Scene scene;

	/**
	 *
	 * @param x X position of panel
	 * @param y Y position of panel
	 * @param width Width of panel
	 * @param height Height of panel
	 */
	public PanelAnimalAnimation(int x, int y, int width, int height) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		initialize();
	}

	public Container initialize(){

		final JFXPanel fxPanel = new JFXPanel();

		this.add(fxPanel);
		this.setVisible(true);

		//Size of parent-panel
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(new Color(0,0,0,0));
		this.setOpaque(false);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initFX(fxPanel);
			}
		});

		return this;
	}

	private void initFX(JFXPanel fxPanel) {
		scene = initScene();
		fxPanel.setScene(scene);
	}

	private Scene initScene() {

		Pane  root  =  new  Pane();
		Scene  scene  =  new  Scene(root, width, height);
		scene.setFill(javafx.scene.paint.Color.rgb(0,0,0,0));
		URL url1 = this.getClass().getResource("/assets/stylesheets/visualiser.css");
		scene.getStylesheets().add(url1.toExternalForm());

		root.setId("beePane");

		circle = createRectangle();
		transition = new TranslateTransition((Duration.seconds(1)), circle);
		moveCircleOnMousePress(scene, circle, transition);
		root.getChildren().add(circle);

		url = "/assets/icons/SalesBee.png";

		Image image = new Image(url);
		circle.setFill(new ImagePattern(image, 0, 0, 1, 1, true));

		return (scene);
	}

	private Circle createRectangle() {
		final Circle circle = new Circle(0, 0, 25);
		circle.setOpacity(1);
		return circle;
	}

	private void moveFromAToB(Cell end, Circle circle, TranslateTransition transition) {

		transition.setToX(end.getX() - circle.getCenterX());
		transition.setToY(end.getY() - circle.getCenterY());

		transition.playFromStart();
	}

	private void moveCircleOnMousePress(Scene scene, final Circle circle, final TranslateTransition transition) {
		scene.setOnMousePressed(event -> {
			transition.setToX(event.getSceneX() - circle.getCenterX());
			transition.setToY(event.getSceneY() - circle.getCenterY());
			transition.playFromStart();
		});
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPath(ArrayList<Cell> path) {
		this.path = path;

		circle.setCenterX(path.get(this.stepNum).getX());
		circle.setCenterY(path.get(this.stepNum).getY());

		setStepNum(stepNum);
	}

	public void setStepNum(int step) {
		this.stepNum = step;

		System.out.println(path.get(stepNum + 1));

		moveFromAToB(path.get(stepNum + 1), circle, transition);
	}


	//public void setMoves()
}