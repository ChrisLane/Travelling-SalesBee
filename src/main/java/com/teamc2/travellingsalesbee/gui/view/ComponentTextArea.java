package com.teamc2.travellingsalesbee.gui.view;

import javax.swing.*;
import java.awt.*;
import com.teamc2.travellingsalesbee.gui.data.TextArea;


public class ComponentTextArea extends JScrollPane {
	private TextArea textArea;

	public ComponentTextArea(JEditorPane editorPane) {
		super(editorPane);
		textArea = new TextArea(editorPane);

		setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setPreferredSize(new Dimension(350, 200));
		setMinimumSize(new Dimension(10, 10));
	}

	public void setText(String text) {
		textArea.setText(text);
		repaint();
	}
}
