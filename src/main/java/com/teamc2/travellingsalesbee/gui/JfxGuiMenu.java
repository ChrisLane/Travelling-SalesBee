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

/**
 * Creates the main GUI menu for navigation.
 */
public class JfxGuiMenu extends Application {
	/**
	 * Fixed width of the window/application.
	 */
	protected final int appWidth = 700;

	/**
	 * Fixed height of the window/application.
	 */
	protected final int appHeight = 768;

	/**
	 * Main method.
	 *
	 * @param args Program arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
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

	/**
	 * Create the scene to be used by the application.
	 *
	 * @param borderPane The BorderPane to initialise the Scene with.
	 * @return 			 The created scene.
	 */
	protected Scene createScene(BorderPane borderPane)
	{
		Scene scene = new Scene(borderPane, appWidth, appHeight);
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

	/**
	 * Create a VBox and its buttons.
	 *
	 * @return The newly created VBox.
	 */
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

	/**
	 * Create a button for use in the application.
	 *
	 * @param title The button text.
	 * @return      The newly created button.
	 */
	protected Button createButton(String title)
	{
		Button button = new Button(title);
		button.setMinSize(150, 60);
		button.getStyleClass().add("button");

		return button;
	}
}
