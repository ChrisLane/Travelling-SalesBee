package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class PanelAnimalAnimation extends JPanel {

	private String url;
	private int width;
	private int height;
	private double speed;
	private ArrayList<Cell> path;
	private ArrayList<ArrayList<Cell>> pathOfPaths;
	private int stepNum = 0;
	private Rectangle animalIcon;
	private TranslateTransition transition;
	private boolean transitionPlaying = true;

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

		animalIcon = createRectangle();
		animalIcon.setVisible(false);
		transition = new TranslateTransition(Duration.seconds(1), animalIcon);
		root.getChildren().add(animalIcon);

		//This should be set, but for now we'll leave it hard-coded in
		url = "/assets/icons/SalesBee.png";

		Image image = new Image(url);
		animalIcon.setFill(new ImagePattern(image, 0, 0, 1, 1, true));

		return scene;
	}

	private Rectangle createRectangle() {
		final Rectangle rectangle = new Rectangle(-20, -20, 45, 45);
		rectangle.setOpacity(1);

		return rectangle;
	}

	private void moveFromAToB(Cell end, Rectangle animal, TranslateTransition transition) {

		transitionPlaying = true;

		transition.setToX(end.getX() - animal.getX() - 25);
		transition.setToY(end.getY() - animal.getY() - 25);
		transition.playFromStart();

		transition.setOnFinished(AE -> {
			transitionPlaying = false;
			System.out.println("STOPPED PLAYING");
		});
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPath(ArrayList<Cell> path) {
		this.path = path;

		Platform.runLater(() -> {
			animalIcon.setX(path.get(stepNum).getX() - 25);
			animalIcon.setY(path.get(stepNum).getY() - 25);
			animalIcon.setVisible(true);
			naiveRun = true;
		});
	}

	public void setPathofPaths(ArrayList<ArrayList<Cell>> path) {
		this.pathOfPaths = path;
		//setStepNum(0);

		System.out.println("pathOfPaths size: " + pathOfPaths.size());

		//Platform.runLater(() -> {
			animalIcon.setX(path.get(0).get(0).getX() - 25);
			animalIcon.setY(path.get(0).get(0).getY() - 25);
			animalIcon.setVisible(true);
		//});

		// 1. Animate (path.get(step))
		/*try {
			animatePath(path, 0, path.get(0), 0, animalIcon, transition);
		} catch (Exception e) {
			System.out.println("No available moves");
			e.printStackTrace();
		}*/
	}

	/**
	 *
	 * @param superPath Path of all paths ArrayList<ArrayList<Cell>>
	 * @param superI superPath position (gets the current path we're working with)
	 * @param path Current path to animate
	 * @param i Path position
	 * @param animal The 'animal' to move
	 * @param transition The transition object that handles the animation itself
	 */
	private void animatePath(ArrayList<ArrayList<Cell>> superPath, final int superI, ArrayList<Cell> path, int i, Rectangle animal, TranslateTransition transition) {

		//If there are no more paths, do nothing
		if (superI >= superPath.size()) {
			System.out.println("Ran out of moves");
		} else if (i >= path.size()) {
			//If icrement is great than size of the path, go to the next path
			//animatePath(superPath, (superI + 1), superPath.get(superI + 1), 0, animal, transition);
			System.out.println("path.size() = " + path.size());
			System.out.println("STOP");
		} else {

			final int acc = i + 1;

			//Get end cell
			if (acc < path.size()) {

				//Get the next point to move to
				Cell end = path.get(acc);

				//Set transition position to move to
				transition.setToX(end.getX() - animal.getX() - 25);
				transition.setToY(end.getY() - animal.getY() - 25);

				//Play transition
				transition.playFromStart();

			} else {
				animatePath(superPath, (superI + 1), superPath.get(superI + 1), 0, animal, transition);
			}

			//when animation is finished, increment through path
			transition.setOnFinished(AE -> {
				System.out.println("Stopped playing");
				animatePath(superPath, superI, path, acc, animal, transition);
			});

		}
	}

	public void setSpeed(double speed) {
		this.speed = speed;

		//THIS MAKES THE ANIMATION DISGUSTING, LET'S DO SOMETHING DIFFERENT PLZ
		//transition.setRate(speed);
	}

	private boolean naiveRun = false;

	public void setStepNum(int step) {
		this.stepNum = step;
		System.out.println(stepNum);

		if (stepNum < 0) {
			this.setVisible(false);
		} else {
			this.setVisible(true);
		}

		if(naiveRun) {
			Cell end = path.get(stepNum);
			moveFromAToB(end, animalIcon, transition);
		}

		/*try {
			ArrayList<Cell> path = path1fPaths.get(stepNum);
			animalIcon.setX(path.get(0).getX() - 25);
			animalIcon.setY(path.get(0).getY() - 25);
			System.out.println("Set animation");
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		//animatePath(pathOfPaths, stepNum, pathOfPaths.get(stepNum), 0, animalIcon, transition);
	}

}