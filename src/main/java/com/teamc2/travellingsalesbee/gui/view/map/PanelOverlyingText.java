package com.teamc2.travellingsalesbee.gui.view.map;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;

/**
 * A class to contain overlying text control and display
 */
public class PanelOverlyingText extends JFXPanel {
	private Text text;
	private Pane root;
	private Scene scene;

	/**
	 * Construct the overlying text
	 */
	public PanelOverlyingText() {
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
			scene = new Scene(root);
			scene.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0)); //Set it to transparent

			// Initialising the text, settings its font and initial text and giving it a drop shadow
			text = new Text();

			setSize(12);
			setTextColor(Color.color(0, 0, 0, 1.0));

			setTextBold(true);
			setDropShadow(true);
			text.setTextAlignment(TextAlignment.CENTER);
			setText("Initial text");

			//Set the stylesheet for the scene (make textPane transparent)
			URL url = getClass().getResource("/assets/stylesheets/visualiser.css");
			scene.getStylesheets().add(url.toExternalForm());

			root.getChildren().add(text);
			setScene(scene);
		});
	}

	/**
	 * Set the text style to bold
	 *
	 * @param setBold Should the font be bold?
	 */
	public void setTextBold(boolean setBold) {
		if (setBold) {
			text.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		} else {
			text.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
		}

	}

	/**
	 * Set the size of the font
	 *
	 * @param size Size of the font
	 */
	public void setSize(int size) {
		Platform.runLater(() -> {
			text.setFont(new Font(size));
		});
	}

	/**
	 * Set the text to be centered
	 *
	 * @param centered Set the text to centered
	 */
	public void setCenter(boolean centered) {
		Platform.runLater(() -> {
			if (centered) {
				text.setTextAlignment(TextAlignment.CENTER);
			} else {
				text.setTextAlignment(TextAlignment.LEFT);
			}
		});
	}

	/**
	 * Set the text
	 *
	 * @param str Text to set
	 */
	public void setText(String str) {
		Platform.runLater(() -> {
			text.setText(str);
		});
	}

	/**
	 * Set the text location to (X, Y)
	 *
	 * @param x X location of text
	 * @param y Y location of text
	 */
	public void setTextXandY(int x, int y) {
		Platform.runLater(() -> {
			text.setX(x);
			text.setY(y);
		});
	}

	/**
	 * Set the text color
	 *
	 * @param color Text color
	 */
	public void setTextColor(Color color) {
		Platform.runLater(() -> {
			text.setFill(color);
		});
	}

	/**
	 * Set whether the text has a shadow
	 *
	 * @param bool If the text has a shadow or not
	 */
	public void setDropShadow(boolean bool) {
		Platform.runLater(() -> {
			if (bool) {
				DropShadow ds = new DropShadow();
				ds.setOffsetY(3.0f);
				ds.setColor(Color.color(1.0f, 1.0f, 1.0f));
				text.setEffect(ds);
			}
		});
	}

	/**
	 * Get the text object
	 *
	 * @return The text object
	 */
	public Text getTextObject() {
		return text;
	}
}
