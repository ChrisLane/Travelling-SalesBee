package com.teamc2.travellingsalesbee.gui.view.pages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.InputStream;

/**
 * A class for generating and viewing the FAQ page.
 *
 * @author Neil Farrington (npf489)
 */
public class Faq extends Page {

	/**
	 * Creates a page as a stage, with a given window title, window height, and window width.
	 *
	 * @param title  The window title.
	 * @param height The window height.
	 * @param width  The window width.
	 * @see Page#Page(String, int, int)
	 */
	public Faq(String title, int height, int width) {
		super(title, height, width);
	}

	/**
	 * {@inheritDoc}
	 */
	public void bootstrap() {
		// create the border pane
		BorderPane border = new BorderPane();
		border.setId("faq");

		ScrollPane scroll = new ScrollPane();
		scroll.setMaxWidth(width / 2.5);
		scroll.setMaxHeight(height / 2.5);
		scroll.setId("scroll");
		border.setCenter(scroll);

		// add the text to the pane
		Text text = createTextBox();
		text.setWrappingWidth(width / 2.6);
		scroll.setContent(text);
		BorderPane.setAlignment(text, Pos.TOP_CENTER);
		BorderPane.setMargin(text, new Insets(150, 10, 0, 10));

		// create the scene
		Scene scene = createScene(border);

		// stage-ify!
		setScene(scene);
		setMinHeight(height);
		setMinWidth(width);
		setResizable(false);
	}

	/**
	 * Create a Text object with the About page text.
	 *
	 * @return The text box with loaded content.
	 */
	protected Text createTextBox() {
		Text text = new Text();
		InputStream is = getClass().getClassLoader().getResourceAsStream("assets/text/faq.txt");

		try (java.util.Scanner s = new java.util.Scanner(is)) {
			text.setText(s.useDelimiter("\\A").hasNext() ? s.next() : "");
			return text;
		}
	}
}
