package com.teamc2.travellingsalesbee;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Reference class for coding animations.
 *
 * Original source:
 * https://gist.githubusercontent.com/jewelsea/4569878/raw/e6d3e5545069678783c171320d075e8e248c0f8c/MovementEventsDemo.java
 */
public class TestAnimation extends Application {
	private static final Duration TRANSLATE_DURATION = Duration.seconds(0.25);

	public static void main(String[] args) { launch(args); }
	@Override public void start(Stage stage) throws Exception {
		final Circle circle = createCircle();
		final Group group = new Group(circle);
		final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, circle);

		final Scene scene = new Scene(group, 600, 400, Color.CORNSILK);
		moveCircleOnMousePress(scene, circle, transition);

		stage.setScene(scene);
		stage.show();
	}
	
	private Circle createCircle() {
		final Circle circle = new Circle(200, 150, 50, Color.BLUEVIOLET);
		circle.setOpacity(0.7);
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