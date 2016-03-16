package com.teamc2.travellingsalesbee.gui.view;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
			URL url1 = this.getClass().getResource("/assets/stylesheets/visualiser.css");
			scene.getStylesheets().add(url1.toExternalForm());

			// Initialising the text, settings its font and initial text
			text = new Text();
			setSize(80);
			text.setId("panelText");
			setTextXandY(0, 95);
			this.setText("Initial text");

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
}
