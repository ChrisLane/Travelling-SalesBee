package com.teamc2.travellingsalesbee.gui.view;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;

public class PanelOverlayingText extends JFXPanel {

	private int width, height;
	private Text text;

	private Scene scene;

	/**
	 * @param width Width of the panel
	 * @param height Height of the panel
	 */
	public PanelOverlayingText(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Initialise the scene
	 */
	private void initScene() {
		// Make a new pane and give it an ID
		Pane root = new Pane();
		root.setId("textPane");

		// Initialising the scene
		scene = new Scene(root, width, height);
		scene.setFill(javafx.scene.paint.Color.rgb(0,0,0,0)); //Set it to transparent
		URL url1 = this.getClass().getResource("/assets/stylesheets/visualiser.css");
		scene.getStylesheets().add(url1.toExternalForm());

		// Initialising the text, settings its font and initial text
		text = new Text();
		text.setFont(new Font(20));
		this.setText("Initial text");
	}

	public void setText(String str) {
		this.text.setText(str);
	}
}
