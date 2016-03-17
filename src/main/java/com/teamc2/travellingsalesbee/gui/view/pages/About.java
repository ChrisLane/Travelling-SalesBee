package com.teamc2.travellingsalesbee.gui.view.pages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A class for generating and viewing the about page.
 *
 * @author Neil Farrington (npf489)
 */
public class About extends Page {

	/**
	 * Creates a page as a stage, with a given window title, window height, and window width.
	 *
	 * @param title  The window title.
	 * @param height The window height.
	 * @param width  The window width.
	 * @see Page#Page(String, int, int)
	 */
	public About(String title, int height, int width) {
		super(title, height, width);
	}

	/**
	 * {@inheritDoc}
	 */
	public void bootstrap() {
		// create the border pane
		BorderPane border = new BorderPane();
		border.setId("about");

		// add the buttons to the pane
		Text text = createTextBox();
		border.setCenter(text);
		BorderPane.setAlignment(text, Pos.TOP_CENTER);
		BorderPane.setMargin(text, new Insets(150,10,0,10));

		// create the scene
		Scene scene = createScene(border);

		// stage-ify!
		setScene(scene);
		setTitle("Travelling SalesBee");
		setMinHeight(height);
		setMinWidth(width);
		setResizable(false);
	}

	/**
	 * Create a Text object with the About page text.
	 *
	 * @return The text box with loaded content.
	 */
	protected Text createTextBox() {
		Text text = new Text();

		// obtain the text content
		String textContent;
		try {
			URL filePath = getClass().getClassLoader().getResource("pages/about.txt");
			if (filePath != null) {
				byte[] byteContent = Files.readAllBytes(Paths.get(filePath.getFile()));
				textContent = new String(byteContent);
			} else {
				throw new IOException();
			}
		} catch (IOException e) {
			textContent = "Failed to obtain text for the current page.\nApologies for the inconvenience.";
		}

		// set the text content and return
		text.setText(textContent);

		return text;
	}
}
