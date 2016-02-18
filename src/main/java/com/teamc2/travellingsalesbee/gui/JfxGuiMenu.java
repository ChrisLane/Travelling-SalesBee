package com.teamc2.travellingsalesbee.gui;

import com.teamc2.travellingsalesbee.gui.pages.Menu;
import javafx.application.Application;
import javafx.stage.Stage;

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
	 * Program arguments.
	 */
	protected static String[] args;

	/**
	 * Main method.
	 *
	 * @param args Program arguments.
	 */
	public static void main(String[] args) {
		JfxGuiMenu.args = args;
		launch(args);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(Stage stage) {
		Menu page = new Menu("Travelling Salesbee", appHeight, appWidth);
		page.bootstrap();
		page.show();
	}

	public static String[] getArgs() {
		return args;
	}
}
