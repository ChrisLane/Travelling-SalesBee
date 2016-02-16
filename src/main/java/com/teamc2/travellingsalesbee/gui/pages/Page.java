package com.teamc2.travellingsalesbee.gui.pages;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class Page extends Stage {
	protected final int height;
	protected final int width;

	public Page(String title, int height, int width) {
		super();
		this.height = height;
		this.width = width;
		setTitle(title);
	}

	protected Scene createScene(BorderPane borderPane) {
		Scene scene = new Scene(borderPane, width, height);
		File file = new File("target/classes/stylesheets/menu.css");
		try {
			URL url = file.toURI().toURL();
			scene.getStylesheets().add(url.toExternalForm());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return scene;
	}
}
