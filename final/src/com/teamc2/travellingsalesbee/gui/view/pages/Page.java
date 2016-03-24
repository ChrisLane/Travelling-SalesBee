package com.teamc2.travellingsalesbee.gui.view.pages;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Base page class.
 *
 * @author Neil Farrington (npf489)
 */
public abstract class Page extends Stage implements PageInterface {
	protected final int height;
	protected final int width;

	/**
	 * Creates a page as a stage, with a given window title, window height, and window width.
	 *
	 * @param title  The window title.
	 * @param height The window height.
	 * @param width  The window width.
	 */
	public Page(String title, int height, int width) {
		super();
		this.height = height;
		this.width = width;
		setTitle(title);
	}

	/**
	 * Create the scene for the stage.
	 *
	 * @param borderPane The BorderPane to use in the scene.
	 * @return The newly created scene.
	 */
	protected Scene createScene(BorderPane borderPane) {
		Scene scene = new Scene(borderPane, width, height);
		URL url = getClass().getResource("/assets/stylesheets/menu.css");
		scene.getStylesheets().add(url.toExternalForm());

		return scene;
	}
}
