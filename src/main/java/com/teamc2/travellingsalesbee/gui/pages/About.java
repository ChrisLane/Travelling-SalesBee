package com.teamc2.travellingsalesbee.gui.pages;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * A class for generating and viewing the about page.
 */
public class About extends Page {

	/**
	 * @see com.teamc2.travellingsalesbee.gui.pages.Page#Page
	 */
	public About(String title, int height, int width) {
		super(title, height, width);
	}

	/**
	 * @see PageInterface#bootstrap()
	 */
	public void bootstrap() {
		// create the border pane
		BorderPane border = new BorderPane();
		border.setId("about");

		// create the scene
		Scene scene = createScene(border);

		// stage-ify!
		setScene(scene);
		setTitle("Travelling Salesbee");
		setMinHeight(height);
		setMinWidth(width);
		setResizable(false);
	}
}
