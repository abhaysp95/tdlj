package com.abhaysp.tdlj.datamodel;

import java.time.LocalDate;

public class TodoItem {
	private String shortDescription;
	private String details;
	private LocalDate deadline;

	public TodoItem(String shortDescription, String details, LocalDate deadline) {
		this.shortDescription = shortDescription;
		this.details = details;
		this.deadline = deadline;
	}

	/** getters */
	public String getShortDescription() { return this.shortDescription; }
	public String getDetails() { return this.details; }
	public LocalDate getDeadline() { return this.deadline; }

	/** setters */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	@Override
	public String toString() {
		return shortDescription;
	}
}
