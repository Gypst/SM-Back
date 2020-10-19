package com.simple.todo.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Deal {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long    id;
	private String  name;
	private String  description;
	private short   priority; //from 1 to 5
	private boolean isDone;
	private Date    dateCreation;
	private Date    dateEdition;

	protected Deal() {}

	public Deal(String name, String description, short priority) {
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.isDone = false;
		Date date = new Date();
		this.dateCreation = date;
		this.dateEdition = date;
	}

	@Override
	public String toString() {
		return String.format(
				"Deal[id=%d, name='%s', description='%s']",
				id, name, description);
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public Date getDateEdition() {
		return dateEdition;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isDone() {
		return isDone;
	}

	public short getPriority() {
		return priority;
	}

	public long getId() {
		return id;
	}

	//Нужен ли в сущности сеттер? В примере не было сеттеров https://spring.io/guides/gs/accessing-data-jpa/
	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDone() {
		isDone = true;
	}

	public  void  setUnDone(){
		isDone = false;
	}

	public void setPriority(short priority) {
		this.priority = priority;
	}

}
