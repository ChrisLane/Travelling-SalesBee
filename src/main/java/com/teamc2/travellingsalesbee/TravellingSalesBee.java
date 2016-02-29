package com.teamc2.travellingsalesbee;

import com.teamc2.travellingsalesbee.gui.view.pages.Menu;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Creates the main GUI menu for navigation.
 */
public class TravellingSalesBee extends Application {
	/**
	 * Program arguments.
	 */
	private static String[] args;

	/**
	 * Fixed width of the window/application.
	 */
	private final int appWidth = 700;

	/**
	 * Fixed height of the window/application.
	 */
	private final int appHeight = 768;

	/**
	 * Main method.
	 *
	 * @param args Program arguments.
	 */
	public static void main(String[] args) {
		TravellingSalesBee.args = args;
		launch(args);
	}

	/**
	 * Get the program arguments.
	 *
	 * @return The program arguments.
	 */
	public static String[] getArgs() {
		return args;
	}

	/**
	 * <p>Start the program, showing the main menu.</p>
	 * {@inheritDoc}
	 */
	@Override
	public void start(Stage stage) {
		Menu page = new Menu("Travelling SalesBee", appHeight, appWidth);
		page.bootstrap();
		page.show();
	}
}
