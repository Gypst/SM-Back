package com.simple.todo.classes;

import java.util.Date;

public class Deal {

	private Date    dateCreation;
	private Date    dateEdition;
	private String  name;
	private String  description;
	private boolean isDone;
	private short   priority; //from 1 to 5
	private long    id;


	public Date getDateCreation() {
		return dateCreation;
	}

	public Date getDateEdition() {
		return dateEdition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone() {
		isDone = true;
	}

	public  void  setUnDone(){
		isDone = false;
	}

	public short getPriority() {
		return priority;
	}

	public void setPriority(short priority) {
		this.priority = priority;
	}

	public long getId() {
		return id;
	}
}
