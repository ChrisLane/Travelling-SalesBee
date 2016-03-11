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
	private int stepNum = 0;
	private int popStepNum = 0;

	private double speed;

	private ArrayList<Cell> path;
	private ArrayList<ArrayList<Cell>> pathOfPaths;

	private Rectangle animalIcon;
	private TranslateTransition transition;

	private boolean transitionPlaying = true;
	private boolean singlePath = true; //Single path
	private boolean poPaths = false; //path of Paths

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


	}

	/**
	 *
	 * @param path Path of type: ArrayList<ArrayList<Cell>>
	 *
	 * Gets the first position from the first path in the list and sets the bee to that position
	 * If the path needs to be stepped through the entire path, set the singlePath boolean to true
	 */
	public void setPathofPaths(ArrayList<ArrayList<Cell>> path) {
		this.pathOfPaths = path;
		this.popStepNum = 0;
		this.stepNum = 0;

		Platform.runLater(() -> {
			animalIcon.setX(pathOfPaths.get(0).get(0).getX() - 25);
			animalIcon.setY(pathOfPaths.get(0).get(0).getY() - 25);
			animalIcon.setVisible(true);
			this.setVisible(true);
		});
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

			} else if (superI < superPath.size() - 1) {
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

	public void setStepNum(int step) {
		this.stepNum++;
		System.out.println(stepNum);

		if (stepNum < 0) {
			this.setVisible(false);
		} else {
			this.setVisible(true);
		}

		//If single path
		if(singlePath) {
			if (pathOfPaths.get(popStepNum).size() <= stepNum) { //When we've stepped through all of the naive path, animate the rest as entire paths and not separate steps
				singlePath = false;
				poPaths = true;
				popStepNum++;
			} else {
				Cell end = pathOfPaths.get(popStepNum).get(stepNum);
				moveFromAToB(end, animalIcon, transition);
			}
		}

		if(poPaths) {
			animatePath(pathOfPaths, popStepNum, pathOfPaths.get(stepNum), 0, animalIcon, transition);
		}
	}

	public void animateSteps(boolean bool) {
		this.singlePath = bool;
	}

	public void animatePath(boolean bool) {
		this.poPaths = bool;
	}
}