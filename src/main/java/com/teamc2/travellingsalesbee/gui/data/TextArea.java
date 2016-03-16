package com.teamc2.travellingsalesbee.gui.data;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class TextArea {
	private JEditorPane editorPane;
	private HTMLEditorKit kit;
	private HTMLDocument doc;

	public TextArea(JEditorPane editorPane) {
		this.editorPane = editorPane;
		kit = new HTMLEditorKit();
		doc = new HTMLDocument();

		editorPane.setContentType("text/html");
		editorPane.setEditorKit(kit);
		editorPane.setDocument(doc);
	}

	public void addText(String text) {
		System.out.println(editorPane.getText());
		try {
			kit.insertHTML(doc, doc.getLength(), text, 0, 0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(editorPane.getText());
	}
}
