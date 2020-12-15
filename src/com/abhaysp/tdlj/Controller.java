package com.abhaysp.tdlj;

import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Month;

import com.abhaysp.tdlj.datamodel.TodoData;
import com.abhaysp.tdlj.datamodel.TodoItem;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Label;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.util.Callback;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.collections.transformation.SortedList;
import javafx.collections.transformation.FilteredList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Controller {
	private List<TodoItem> todoItems;

	@FXML private BorderPane mainBorderPane;
	@FXML private ListView<TodoItem> todoListView;
	@FXML private TextArea itemDetailsTextArea;
	@FXML private Label deadlineLabel;
	@FXML private ContextMenu listContextMenu;
	@FXML private ToggleButton filterToggleButton;
	@FXML private FilteredList<TodoItem> filteredList;

	private Predicate<TodoItem> wantAllItems;
	private Predicate<TodoItem> wantTodaysItems;

	public void initialize() {
		listContextMenu = new ContextMenu();
		MenuItem deleteMenuItem = new MenuItem("Delete");
		deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TodoItem item = todoListView.getSelectionModel().getSelectedItem();
				deleteItem(item);
			}
		});
		listContextMenu.getItems().addAll(deleteMenuItem);

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

		/** get the filteredList test Predicate with all items
		  (actually, it doesn't filter anything) */
		wantAllItems = new Predicate<TodoItem>() {
			@Override
			public boolean test(TodoItem todoItem) {
				return true;
			}
		};
		/** filteredList test Predicate with TodoItem of today's deadline only  */
		wantTodaysItems = new Predicate<TodoItem>() {
			@Override
			public boolean test(TodoItem todoItem) {
				return todoItem.getDeadline().equals(LocalDate.now());
			}
		};

		filteredList = new FilteredList<TodoItem>(TodoData.getInstance().getTodoItems(), wantAllItems);

		/** make todoitem sorted according to deadline with closest deadline on top */
		SortedList<TodoItem> sortedList = new SortedList<TodoItem>(filteredList,
				new Comparator<TodoItem>() {
					@Override
					public int compare(TodoItem o1, TodoItem o2) {
						/** cause LocalDate compares just exactly like the Comparator wants with -1, 0, 1 */
						return o1.getDeadline().compareTo(o2.getDeadline());
					}
				});

		/** fill up todoListView with sortedList(sorted acc to deadline) */
		todoListView.setItems(sortedList);
		/** let you select only one item
		  (use MULTIPLE if you want to select multiple item, say with shift) */
		todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		todoListView.getSelectionModel().selectFirst();

		todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
			@Override
			public ListCell<TodoItem> call(ListView<TodoItem> param) {
				ListCell<TodoItem> cell = new ListCell<TodoItem>() {
					@Override
					protected void updateItem(TodoItem item, boolean empty) {
						/** default environment provided by the parent class */
						super.updateItem(item, empty);
						if (empty) {
							setText(null);
						}
						else {
							/** no need for toString() updateItem() is handling it */
							setText(item.getShortDescription());
							/** current date and dates before today */
							if (item.getDeadline().isBefore(LocalDate.now().plusDays(1))) {
								setTextFill(Color.RED);
							}
							else if (item.getDeadline().equals(LocalDate.now().plusDays(1))) {
								setTextFill(Color.BROWN);
							}
						}
					}
				};
				cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
					if (isNowEmpty) {
						cell.setContextMenu(null);
					}
					else {
						cell.setContextMenu(listContextMenu);
					}
				});
				return cell;
			}
		});
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
			DialogController dialogController = fxmlLoader.getController();
			/** refresh/replace with new updated */
			TodoItem newItem = dialogController.processResults();
			todoListView.getSelectionModel().select(newItem);
		}
	}

	@FXML
	public void handleKeyPressed(KeyEvent event) {
		TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			if (event.getCode().equals(KeyCode.DELETE)) {
				deleteItem(selectedItem);
			}
		}
	}

	@FXML
	public void handleClickListView() {
		TodoItem item = todoListView.getSelectionModel().getSelectedItem();
		itemDetailsTextArea.setText(item.getDetails());
		deadlineLabel.setText(item.getDeadline().toString());
	}

	@FXML
	public void deleteItem(TodoItem item) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete Todo Item");
		alert.setHeaderText("Delete item: " + item.getShortDescription());
		alert.setContentText("Are you sure?\n" +
				"Press OK to continue, or Cancel to back out");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			TodoData.getInstance().deleteTodoItem(item);
		}
	}

	@FXML
	public void handleFilterButton() {
		TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
		if (filterToggleButton.isSelected()) {
			filteredList.setPredicate(wantTodaysItems);
			if (filteredList.isEmpty()) {
				itemDetailsTextArea.clear();
				deadlineLabel.setText("");
			}
			else if (filteredList.contains(selectedItem)) {
				todoListView.getSelectionModel().select(selectedItem);
			}
			else {
				todoListView.getSelectionModel().selectFirst();
			}
		}
		else {
			filteredList.setPredicate(wantAllItems);
			todoListView.getSelectionModel().select(selectedItem);
		}
	}

	@FXML
	public void handleExit() {
		Platform.exit();
	}
}