package com.teamc2.travellingsalesbee.gui.view;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

import java.net.URL;

public class PanelOverlyingText extends JFXPanel {

	private int width, height;
	private Text text;
	
	private Pane root;
	private Scene scene;

	/**
	 * @param width Width of the panel
	 * @param height Height of the panel
	 */
	public PanelOverlyingText(int width, int height) {
		this.width = width;
		this.height = height;

		initScene();
	}

	/**
	 * Initialise the scene
	 */
	private void initScene() {
		Platform.runLater(() -> {
			// Make a new pane and give it an ID
			root = new Pane();
			root.setId("textPane");

			// Initialising the scene
			scene = new Scene(root, width, height);
			scene.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0)); //Set it to transparent

			// Initialising the text, settings its font and initial text and giving it a drop shadow
			text = new Text();

			setSize(12);
			setTextColor(Color.color(0, 0, 0, 1.0));
			setDropShadow(true);
			setTextXandY(0, 13);
			text.setTextAlignment(TextAlignment.CENTER);
			setText("Initial text");

			//Set the stylesheet for the scene (make textPane transparent)
			URL url = getClass().getResource("/assets/stylesheets/visualiser.css");
			scene.getStylesheets().add(url.toExternalForm());

			root.getChildren().add(text);
			setScene(scene);
		});
	}

	public void setSize(int size) {
		Platform.runLater(() -> {
			text.setFont(new Font(size));
		});
	}

	public void setText(String str) {
		Platform.runLater(() -> {
			text.setText(str);
		});
	}

	public void setTextXandY(int x, int y) {
		Platform.runLater(() -> {
			text.setX(x);
			text.setY(y);
		});
	}

	public void setTextColor(Color color) {
		Platform.runLater(() -> {
			text.setFill(color);
		});
	}

	public void setDropShadow(boolean bool) {
		Platform.runLater(() -> {
			if(bool) {
				DropShadow ds = new DropShadow();
				ds.setOffsetY(3.0f);
				ds.setColor(Color.color(1.0f, 1.0f, 1.0f));
				text.setEffect(ds);
			}
		});
	}
}
