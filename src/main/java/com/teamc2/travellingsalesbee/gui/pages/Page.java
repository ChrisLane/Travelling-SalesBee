package com.teamc2.travellingsalesbee.gui.pages;

import javafx.stage.Stage;

public abstract class Page extends Stage {
	protected int height;
	protected int width;

	public Page(String title, int height, int width)
	{
		super();
		this.height = height;
		this.width = width;
		setTitle(title);
	}
}
