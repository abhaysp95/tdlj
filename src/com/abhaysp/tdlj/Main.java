package com.abhaysp.tdlj;

import java.io.IOException;

import com.abhaysp.tdlj.datamodel.TodoData;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

	@Override
	public void init() throws Exception {
		try {
			TodoData.getInstance().loadTodoItems();
		}
		catch (IOException ie) {
			System.out.println(ie.getMessage());
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/com/abhaysp/tdlj/mainwindow.fxml"));
		primaryStage.setTitle("tdlj");
		primaryStage.setScene(new Scene(root, 900, 400));
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		try {
			// use Singleton TodoData
			TodoData.getInstance().storeTodoItems();
		}
		catch (IOException ie) {
			System.out.println(ie.getMessage());
		}
	}

	public static void main(String ...args) {
		launch(args);
	}
}