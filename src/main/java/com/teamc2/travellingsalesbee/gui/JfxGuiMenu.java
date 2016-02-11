package com.teamc2.travellingsalesbee.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
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

		Button simButton = new Button("Run Simulation");
		simButton.setMinSize(150, 60);
		simButton.getStyleClass().add("button");

		Button docButton = new Button("Documentation");
		docButton.setMinSize(150, 60);
		docButton.getStyleClass().add("button");

		Button faqButton = new Button("FAQ");
		faqButton.setMinSize(150, 60);
		faqButton.getStyleClass().add("button");

		Button aboutButton = new Button("About");
		aboutButton.setMinSize(150, 60);
		aboutButton.getStyleClass().add("button");

		vBox.getChildren().addAll(simButton, docButton, faqButton, aboutButton);
		vBox.setAlignment(Pos.TOP_CENTER);
		vBox.setPadding(new Insets(150, 0, 0, 0));
		vBox.setSpacing(30);

		border.setCenter(vBox);

		Scene scene = new Scene(border, 700, 768);
		File file = new File("target/classes/stylesheets/menu.css");
		try {
			URL url = file.toURI().toURL();
			scene.getStylesheets().add(url.toExternalForm());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		stage.setScene(scene);
		stage.setTitle("Travelling Salesbee");
		stage.setMinHeight(768);
		stage.setMaxHeight(768);
		stage.setMinWidth(700);
		stage.setMaxWidth(700);
		stage.show();
	}
}
