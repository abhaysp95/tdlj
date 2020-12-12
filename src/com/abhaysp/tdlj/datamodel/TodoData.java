package com.abhaysp.tdlj.datamodel;

import java.util.Iterator;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;

public class TodoData {
	private static TodoData instance = new TodoData();
	private static String fileName = "TodoListItems.txt";

	private List<TodoItem> todoItems;
	private DateTimeFormatter formatter;

	private TodoData() {
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	}

	public static TodoData getInstance() {
		return instance;
	}

	public List<TodoItem> getTodoItems() {
		return todoItems;
	}

	/*public void setTodoItems(List<TodoItem> todoItems) {
		this.todoItems = todoItems;
	}*/

	public void addTodoItem(TodoItem item) {
		todoItems.add(item);
	}

	public void loadTodoItems() throws IOException {
		/* setAll requires observableArrayList */
		todoItems = FXCollections.observableArrayList();
		Path path = Paths.get(fileName);
		BufferedReader br = Files.newBufferedReader(path);

		String input;

		try {
			br.readLine();  // to not read the warning above
			br.readLine();
			while ((input = br.readLine()) != null) {
				String[] itemPieces = input.split("\t");
				//for (String items: itemPieces) {
					//System.out.println("item: " + items);
				//}
				String shortDescription = itemPieces[0];
				String details = itemPieces[1];
				String dateString = itemPieces[2];

				LocalDate date = LocalDate.parse(dateString, formatter);
				TodoItem todoItem = new TodoItem(shortDescription, details, date);
				todoItems.add(todoItem);
			}
		}
		finally {
			if (br != null) {
				br.close();
			}
		}
	}

	public void storeTodoItems() throws IOException {
		Path path = Paths.get(fileName);
		BufferedWriter bw = Files.newBufferedWriter(path);
		try {
			Iterator<TodoItem> iterator = todoItems.iterator();
			bw.write("Note: Formatting this file manually is not recommended.\n\n");
			while (iterator.hasNext()) {
				TodoItem item = iterator.next();
				bw.write(String.format("%s\t%s\t%s",
							item.getShortDescription(),
							item.getDetails(),
							item.getDeadline().format(formatter)));
				bw.newLine();
			}
		}
		finally {
			if (bw != null) {
				bw.close();
			}
		}
	}
}
