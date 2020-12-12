package com.abhaysp.tdlj;

import java.util.List;
import java.util.Observable;
import java.util.ObservableValue;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Month;
import com.abhaysp.tdlj.datamodel.TodoItem;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Label;
import javafx.fxml.FXML;

import javafx.beans.value.ChangeListener;

public class Controller {
	private List<TodoItem> todoItems;

	@FXML private ListView<TodoItem> todoListView;
	@FXML private TextArea itemDetailsTextArea;
	@FXML private Label deadlineLabel;

	public void initialize() {
		TodoItem item1 = new TodoItem("Mail birthday card",
				"Buy a 30th birthday card for John",
				LocalDate.of(2021, Month.JANUARY, 01));
		TodoItem item2 = new TodoItem("Doctor's Appointment",
				"See Doctor R.K. Sharma at Bai ka Bagh, bring paperwork",
				LocalDate.of(2020, Month.DECEMBER, 19));
		TodoItem item3 = new TodoItem("Finish design proposal for client",
				"Promised to my friend to send a email site mockup upto 22nd Feb",
				LocalDate.of(2021, Month.FEBRUARY, 22));
		TodoItem item4 = new TodoItem("Servicing of the car",
				"Plymoth RoadRunner requires servicing from last 10 days, register today",
				LocalDate.of(2020, Month.DECEMBER, 12));
		TodoItem item5 = new TodoItem("Change Rom",
				"MPM1's custom ROM is now 2 months old, change to RR upto the end of this week",
				LocalDate.of(2020, Month.DECEMBER, 13));

		todoItems = new ArrayList<TodoItem>();
		todoItems.add(item1);
		todoItems.add(item2);
		todoItems.add(item3);
		todoItems.add(item4);
		todoItems.add(item5);

		todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
			@Override
			public void changed(ObservableValue<? extends TodoItem> observable, TodoItem oldValue, TodoItem newValue) {
				if (newValue != NULL) {
					TodoItem item = todoListView.getSelectionModel().getSelectedItem();
					itemDetailsTextArea.setText(item.getDetails());
				}
			}
		})

		todoListView.getItems().setAll(todoItems);
		/** let you select only one item
		  (use MULTIPLE if you want to select multiple item, say with shift) */
		todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		todoListView.getSelectionModel().selectFirst();
	}

	@FXML
	public void handleClickListView() {
		TodoItem item = todoListView.getSelectionModel().getSelectedItem();
		//StringBuilder sb = new StringBuilder(item.getDetails());
		//sb.append("\n\n\n\n");
		//sb.append("Due: ");
		//sb.append(item.getDeadline());
		//itemDetailsTextArea.setText(sb.toString());
		itemDetailsTextArea.setText(item.getDetails());
		deadlineLabel.setText(item.getDeadline().toString());
	}
}