package com.teamc2.travellingsalesbee.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
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

		VBox vBox = new VBox();
		Button testButton = new Button("Simulation");
		testButton.setId("button");
		testButton.setMinSize(150, 60);

		Button testButton2 = new Button("Documentation");
		testButton2.setId("button2");
		testButton2.setMinSize(150, 60);

		vBox.getChildren().addAll(testButton, testButton2);
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(20);

		border.setCenter(vBox);

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
