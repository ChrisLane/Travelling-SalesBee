package com.teamc2.travellingsalesbee.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class JfxGuiMenu extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}

	public void start(Stage stage)
	{
		BorderPane border = new BorderPane();
		border.setId("main");
		border.setPrefHeight(500);

		Button testButton = new Button("Test Button");
		testButton.setId("button");
		border.setCenter(testButton);

		Scene scene = new Scene(border, 800, 800);
		File file = new File("target/classes/stylesheets/menu.css");
		try {
			URL url = file.toURI().toURL();
			scene.getStylesheets().add(url.toExternalForm());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		stage.setScene(scene);
		stage.setTitle("Travelling Salesbee");
		stage.show();
	}
}
