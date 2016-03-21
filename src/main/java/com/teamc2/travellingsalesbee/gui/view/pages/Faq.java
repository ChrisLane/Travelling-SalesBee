package com.teamc2.travellingsalesbee.gui.view.pages;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * A class for generating and viewing the FAQ page.
 *
 * @author Neil Farrington (npf489)
 */
public class Faq extends Page {

	/**
	 * Creates a page as a stage, with a given window title, window height, and window width.
	 *
	 * @param title  The window title.
	 * @param height The window height.
	 * @param width  The window width.
	 * @see Page#Page(String, int, int)
	 */
	public Faq(String title, int height, int width) {
		super(title, height, width);
	}

	/**
	 * {@inheritDoc}
	 */
	public void bootstrap() {
		// create the border pane
		BorderPane border = new BorderPane();
		border.setId("faq");

		// create the scene
		Scene scene = createScene(border);

		// stage-ify!
		setScene(scene);
		setMinHeight(height);
		setMinWidth(width);
		setResizable(false);
	}
}
