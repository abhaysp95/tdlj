package com.abhaysp.tdlj;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/com/abhaysp/tdlj/mainwindow.fxml"));
		primaryStage.setTitle("tdlj");
		primaryStage.setScene(new Scene(root, 900, 400));
		primaryStage.show();
	}
	public static void main(String ...args) {
		launch(args);
	}
}