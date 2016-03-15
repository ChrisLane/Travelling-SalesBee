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

	public PanelMap getPanelMap() {
		return (PanelMap) getComponentByName(PanelMap.name);
	}

	public PanelSettings getPanelSettings() {
		return (PanelSettings) getComponentByName(PanelSettings.name);
	}

	public PanelToolbox getPanelToolbox() {
		return (PanelToolbox) getComponentByName(PanelToolbox.name);
	}

	public ComponentTabs getComponentTabs() {
		return (ComponentTabs) getComponentByName(ComponentTabs.name);
	}

	public ComponentTextArea getComponentTextArea() {
		return getPanelSettings().getTextArea();
	}
}
