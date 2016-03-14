package com.teamc2.travellingsalesbee.gui.data;

import javax.swing.*;

public class TextArea {
	private JEditorPane editorPane;

	public TextArea(JEditorPane editorPane) {
		this.editorPane = editorPane;
	}

	public void setText(String text) {
		editorPane.setText(editorPane.getText() + text);
	}
}
