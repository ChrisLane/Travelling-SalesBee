package com.teamc2.travellingsalesbee.gui.view;

import javax.swing.*;
import java.awt.*;

public class GuiContainer extends JRootPane {
	public GuiContainer() {
		setName("GuiContainer");
	}

	public Component getComponentByName(String string) {
		for (Component component : getComponents()) {
			if (component.getName().equals(string)) {
				return component;
			}
		}
		return null;
	}
}
