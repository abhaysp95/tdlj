package com.abhaysp.tdlj;

import java.time.LocalDate;

import com.abhaysp.tdlj.datamodel.TodoData;
import com.abhaysp.tdlj.datamodel.TodoItem;;

import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;

import javafx.fxml.FXML;

public class DialogController {
	@FXML private TextField shortDescriptionField;
	@FXML private TextArea detailsArea;
	@FXML private DatePicker deadlinePicker;

	@FXML
	public TodoItem processResults() {
		String shortDescription = shortDescriptionField.getText().trim();
		String details = detailsArea.getText().trim();
		LocalDate deadline = deadlinePicker.getValue();

		TodoItem newItem = new TodoItem(shortDescription, details, deadline);
		TodoData.getInstance().addTodoItem(newItem);
		return newItem;
	}
}
