package com.abhaysp.tdlj;

import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ArrayList;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Month;

import com.abhaysp.tdlj.datamodel.TodoData;
import com.abhaysp.tdlj.datamodel.TodoItem;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Label;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Controller {
	private List<TodoItem> todoItems;

	@FXML private BorderPane mainBorderPane;
	@FXML private ListView<TodoItem> todoListView;
	@FXML private TextArea itemDetailsTextArea;
	@FXML private Label deadlineLabel;

	public void initialize() {
		todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
			@Override
			public void changed(ObservableValue<? extends TodoItem> observable, TodoItem oldValue, TodoItem newValue) {
				if (newValue != null) {
					TodoItem item = todoListView.getSelectionModel().getSelectedItem();
					itemDetailsTextArea.setText(item.getDetails());
					DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
					deadlineLabel.setText(df.format(item.getDeadline()));
				}
			}
		});

		/** get data from TodoListItems.txt file */
		todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
		/** let you select only one item
		  (use MULTIPLE if you want to select multiple item, say with shift) */
		todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		todoListView.getSelectionModel().selectFirst();
	}

	@FXML
	public void showNewItemDialog() {
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Add New Todo Item");
		dialog.setHeaderText("Use this dialog to create the new todo item");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/com/abhaysp/tdlj/todoitemdialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		}
		catch (IOException ie) {
			System.out.println("Can't open(load) the dialog");
			ie.printStackTrace();
			return;
		}

		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			System.out.println("OK pressed");
			DialogController dialogController = fxmlLoader.getController();
			dialogController.processResults();
			/** refresh/replace with new updated */
			TodoItem newItem = dialogController.processResults();
			todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
			todoListView.getSelectionModel().select(newItem);
		}
		else {
			System.out.println("CANCEL pressed");
		}
	}

	@FXML
	public void handleClickListView() {
		TodoItem item = todoListView.getSelectionModel().getSelectedItem();
		itemDetailsTextArea.setText(item.getDetails());
		deadlineLabel.setText(item.getDeadline().toString());
	}
}