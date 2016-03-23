package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.gui.view.map.PanelMap;
import com.teamc2.travellingsalesbee.gui.view.settings.PanelSettings;
import com.teamc2.travellingsalesbee.gui.view.tabs.ComponentTabs;
import com.teamc2.travellingsalesbee.gui.view.toolbox.PanelToolbox;

import javax.swing.*;
import java.awt.*;

/**
 * Container for the GUI objects
 *
 * @author Christopher Lane (cml476)
 */
public class GuiContainer extends JRootPane {

	/**
	 * Construct the GUI container
	 */
	public GuiContainer() {
		setName("GuiContainer");
	}

	/**
	 * Get a first level child of the container from it's name
	 *
	 * @param componentName Name of the component
	 * @return The component found with the given name
	 */
	public Component getComponentByName(String componentName) {
		for (Component component : getComponents()) {
			if (component.getName().equals(componentName)) {
				return component;
			}
		}
		return null;
	}

	/**
	 * Get the PanelMap object
	 *
	 * @return The PanelMap object
	 */
	public PanelMap getPanelMap() {
		return (PanelMap) getComponentByName(PanelMap.name);
	}

	/**
	 * Get the PanelSettings object
	 *
	 * @return The PanelSettings object
	 */
	public PanelSettings getPanelSettings() {
		return (PanelSettings) getComponentByName(PanelSettings.name);
	}

	/**
	 * Get the PanelToolbox object
	 *
	 * @return The PanelToolbox object
	 */
	public PanelToolbox getPanelToolbox() {
		return (PanelToolbox) getComponentByName(PanelToolbox.name);
	}

	/**
	 * Get the ComponentTabs object
	 *
	 * @return The ComponentTabs object
	 */
	public ComponentTabs getComponentTabs() {
		return (ComponentTabs) getComponentByName(ComponentTabs.name);
	}
}
