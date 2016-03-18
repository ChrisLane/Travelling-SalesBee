package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.gui.view.map.PanelMap;
import com.teamc2.travellingsalesbee.gui.view.settings.PanelSettings;
import com.teamc2.travellingsalesbee.gui.view.tabs.ComponentTabs;
import com.teamc2.travellingsalesbee.gui.view.toolbox.PanelToolbox;

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
}
