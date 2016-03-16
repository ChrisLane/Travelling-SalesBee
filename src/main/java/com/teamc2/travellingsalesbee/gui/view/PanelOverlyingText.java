package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;

public class PanelOverlyingText extends JFXPanel {

	private int width, height;
	private Text text;

	private Scene scene;

	/**
	 * @param width Width of the panel
	 * @param height Height of the panel
	 */
	public PanelOverlyingText(int width, int height) {
		this.width = width;
		this.height = height;

		this.initScene();
	}

	/**
	 * Initialise the scene
	 */
	private void initScene() {
		Platform.runLater(() -> {
			// Make a new pane and give it an ID
			Pane root = new Pane();
			root.setOpacity(0);
			root.setId("textPane");

			// Initialising the scene
			scene = new Scene(root, width, height);
			scene.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0)); //Set it to transparent

			// Initialising the text, settings its font and initial text and giving it a drop shadow
			text = new Text();
			setSize(80);
			setTextColor(Color.color(1.0, 1.0, 1.0, 1.0));
			setDropShadow(true);
			setTextXandY(0, 95);
			this.setText("Initial text");

			//Set the stylesheet for the scene (make textPane transparent)
			URL url = this.getClass().getResource("/assets/stylesheets/visualiser.css");
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
				ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

				text.setEffect(ds);
			}
		});
	}
}
