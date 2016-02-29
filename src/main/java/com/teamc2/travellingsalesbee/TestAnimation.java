package com.teamc2.travellingsalesbee;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Reference class for coding animations.
 *
 **/
public class TestAnimation extends Application {
	private static final Duration TRANSLATE_DURATION = Duration.seconds(0.25);

	public static void main(String[] args) { launch(args); }
	@Override public void start(Stage stage) throws Exception {

		Image image = new Image("/assets/icons/SalesBee.png");

		final Circle circle = createCircle();
		circle.setFill(new ImagePattern(image, 0, 0, 1, 1, true));
		final Group group = new Group(circle);
		final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, circle);

		final Scene scene = new Scene(group, 600, 400, Color.CORNSILK);
		moveCircleOnMousePress(scene, circle, transition);

		stage.setScene(scene);
		stage.show();
	}
	
	private Circle createCircle() {
		final Circle circle = new Circle(200, 150, 50, Color.BLUEVIOLET);
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
}