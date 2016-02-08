package com.teamc2.travellingsalesbee.gui;

import com.teamc2.travellingsalesbee.gui.elements.BeeTable;
import com.teamc2.travellingsalesbee.gui.elements.Map;
import com.teamc2.travellingsalesbee.gui.elements.Settings;
import com.teamc2.travellingsalesbee.gui.elements.Toolbox;

public class Gui {
	public Gui() {
		initialise();
	}

	public void initialise() {
		Map map = new Map(10, 10);
		Settings settings = new Settings(map);
		BeeTable beeTable = new BeeTable(map);
		Toolbox toolbox = new Toolbox();
	}
}
