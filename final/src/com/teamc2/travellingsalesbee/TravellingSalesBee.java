package com.teamc2.travellingsalesbee;

import com.teamc2.travellingsalesbee.gui.view.pages.Menu;
import com.teamc2.travellingsalesbee.gui.view.pages.Page;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Creates the main GUI menu for navigation.
 *
 * @author Neil Farrington (npf489)
 */
public class TravellingSalesBee extends Application {
	/**
	 * The main menu stage.
	 */
	public static Page mainMenu;

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
	 * Start the program, showing the main menu.
	 * {@inheritDoc}
	 */
	@Override
	public void start(Stage stage) {
		TravellingSalesBee.mainMenu = new Menu("Travelling SalesBee", appHeight, appWidth);
		TravellingSalesBee.mainMenu.bootstrap();
		TravellingSalesBee.mainMenu.show();
	}
}
