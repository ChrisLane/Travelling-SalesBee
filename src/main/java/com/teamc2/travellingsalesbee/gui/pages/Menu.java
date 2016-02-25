package com.teamc2.travellingsalesbee.gui.pages;

import com.teamc2.travellingsalesbee.TravellingSalesBee;
import com.teamc2.travellingsalesbee.gui.Visualiser;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * A class for generating and viewing the menu page.
 */
public class Menu extends Page {

	/**
	 * @see com.teamc2.travellingsalesbee.gui.pages.Page#Page
	 */
	public Menu(String title, int height, int width) {
		super(title, height, width);
	}

	/**
	 * @see PageInterface#bootstrap()
	 */
	public void bootstrap() {
		// create the border pane
		BorderPane border = new BorderPane();
		border.setId("main");

		// add the buttons to the pane
		VBox buttonVBox = createButtonVBox();
		border.setCenter(buttonVBox);

		// create the scene
		Scene scene = createScene(border);

		// stage-ify!
		setScene(scene);
		setMinHeight(height);
		setMinWidth(width);
		setResizable(false);
	}

	/**
	 * Create a VBox and its buttons.
	 *
	 * @return The newly created VBox.
	 */
	protected VBox createButtonVBox() {
		VBox vBox = new VBox();

		Button simButton = createButton("Run Simulation");
		simButton.setOnAction(event -> {
			Visualiser.main(TravellingSalesBee.getArgs());
			Platform.setImplicitExit(false);
		});

		Button docButton = createButton("Documentation");
		docButton.setOnAction(event -> System.out.println("<new window - documentation>"));

		Button faqButton = createButton("FAQ");
		faqButton.setOnAction(event -> System.out.println("<new window - faq>"));

		Button aboutButton = createButton("About");
		aboutButton.setOnAction(event -> {
			About page = new About("About", height, width);
			page.bootstrap();
			page.show();
		});

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
	 * @return The newly created button.
	 */
	protected Button createButton(String title) {
		Button button = new Button(title);
		button.setMinSize(150, 60);
		button.getStyleClass().add("button");

		return button;
	}
}
