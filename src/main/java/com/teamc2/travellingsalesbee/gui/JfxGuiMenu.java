package com.teamc2.travellingsalesbee.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class JfxGuiMenu extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}

	public void start(Stage stage) {
		System.out.println("Working Directory = " +
				System.getProperty("user.dir"));
		BorderPane border = new BorderPane();
		border.setId("main");

		Scene scene = new Scene(border, 800, 800);
		File file = new File("target/classes/menu.css");
		try {
			URL url = file.toURI().toURL();
			scene.getStylesheets().add(url.toExternalForm());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		stage.setScene(scene);
		stage.setTitle("Travelling Salesbee");
		stage.show();
	}
}
