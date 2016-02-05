package com.teamc2.travellingsalesbee.gui;

import com.teamc2.travellingsalesbee.gui.elements.ElementBeeTable;
import com.teamc2.travellingsalesbee.gui.elements.ElementMap;
import com.teamc2.travellingsalesbee.gui.elements.ElementSettings;
import com.teamc2.travellingsalesbee.gui.elements.ElementToolbox;

public class GUI {
	public GUI() {
		initialise();
	}

	public void initialise() {
		ElementMap map = new ElementMap(10, 10);
		ElementSettings settings = new ElementSettings(map);
		ElementBeeTable beeTable = new ElementBeeTable(map);
		ElementToolbox toolbox = new ElementToolbox();
	}
}
