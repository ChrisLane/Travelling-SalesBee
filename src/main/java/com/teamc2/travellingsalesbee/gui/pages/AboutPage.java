package com.teamc2.travellingsalesbee.gui.pages;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AboutPage extends Page {
	protected int height;
	protected int width;

	public AboutPage(int height, int width)
	{
		super();
		this.height = height;
		this.width = width;
		setTitle("About");
	}

	public void bootstrap()
	{
		// create the border pane
		BorderPane border = new BorderPane();
		border.setId("about");

		// create the scene
		Scene scene = createScene(border);

		// stage-ify!
		setScene(scene);
		setTitle("Travelling Salesbee");
		setMinHeight(height);
		setMaxHeight(height);
		setMinWidth(width);
		setMaxWidth(width);
	}

	protected Scene createScene(BorderPane borderPane)
	{
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
