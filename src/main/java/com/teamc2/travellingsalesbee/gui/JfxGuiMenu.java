package com.teamc2.travellingsalesbee.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class JfxGuiMenu extends Application {
	protected final int appWidth = 700;
	protected final int appHeight = 768;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) {
		// create the border pane
		BorderPane border = new BorderPane();
		border.setId("main");

		// add the buttons to the pane
		VBox buttonVBox = createButtonVBox();
		border.setCenter(buttonVBox);

		// create the scene
		Scene scene = createScene(border);

		// stage-ify!
		stage.setScene(scene);
		stage.setTitle("Travelling Salesbee");
		stage.setMinHeight(appHeight);
		stage.setMaxHeight(appHeight);
		stage.setMinWidth(appWidth);
		stage.setMaxWidth(appWidth);
		stage.show();
	}

	protected Scene createScene(BorderPane border)
	{
		Scene scene = new Scene(border, appWidth, appHeight);
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

	protected VBox createButtonVBox()
	{
		VBox vBox = new VBox();

		Button simButton = createButton("Run Simulation");
		Button docButton = createButton("Documentation");
		Button faqButton = createButton("FAQ");
		Button aboutButton = createButton("About");

		vBox.getChildren().addAll(simButton, docButton, faqButton, aboutButton);
		vBox.setAlignment(Pos.TOP_CENTER);
		vBox.setPadding(new Insets(150, 0, 0, 0));
		vBox.setSpacing(30);

		return vBox;
	}

	protected Button createButton(String title)
	{
		Button button = new Button(title);
		button.setMinSize(150, 60);
		button.getStyleClass().add("button");

		return button;
	}
}
