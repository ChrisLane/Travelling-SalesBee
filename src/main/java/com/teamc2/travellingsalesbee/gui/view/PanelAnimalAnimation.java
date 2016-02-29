package com.teamc2.travellingsalesbee.gui.view;

import javafx.beans.NamedArg;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import javax.swing.*;
import java.net.URL;

public class PanelAnimalAnimation extends JPanel {

	public PanelAnimalAnimation() {

	}

	private Rectangle createRectangle(int width, int height, Paint fill) {
		final Rectangle rectangle = new Rectangle(width, height, fill);
		rectangle.setOpacity(1);
		return rectangle;
	}
}