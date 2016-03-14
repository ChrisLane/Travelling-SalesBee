package com.teamc2.travellingsalesbee.gui.view.pages;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

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

		// create the scene
		Scene scene = createScene(border);

		// stage-ify!
		setScene(scene);
		setTitle("Travelling SalesBee");
		setMinHeight(height);
		setMinWidth(width);
		setResizable(false);
	}
}
