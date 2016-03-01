package com.teamc2.travellingsalesbee.gui.view;

import java.awt.*;
import java.awt.Color;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PanelAnimalAnimation extends JPanel {

	String url;
	int x, y, width, height;



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
		Scene scene = initScene();
		fxPanel.setScene(scene);
	}

	private Scene initScene() {
		Pane root  =  new  Pane();
		Scene  scene  =  new  Scene(root, width, height);
		scene.setFill(javafx.scene.paint.Color.rgb(0,0,0,0));
		URL url1 = this.getClass().getResource("/assets/stylesheets/visualiser.css");
		scene.getStylesheets().add(url1.toExternalForm());

		root.setId("beePane");

		final Circle circle = createRectangle();
		final TranslateTransition transition = new TranslateTransition((Duration.seconds(0.25)), circle);
		moveCircleOnMousePress(scene, circle, transition);
		root.getChildren().add(circle);

		url = "/assets/icons/SalesBee.png";

		Image image = new Image(url);
		circle.setFill(new ImagePattern(image, 0, 0, 1, 1, true));

		/*Text  text  =  new  Text();
		text.setX(40);
		text.setY(100);
		text.setFont(new Font(25));
		text.setText("Welcome JavaFX!");
		root.getChildren().add(text);*/

		return (scene);
	}

	private Circle createRectangle() {
		final Circle circle = new Circle(50, 250, 80);
		circle.setOpacity(1);
		return circle;
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
}