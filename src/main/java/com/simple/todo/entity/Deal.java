package com.simple.todo.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.*;

@Entity
@Table(name = "DEALS")
public class Deal {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private long    id;
	@Column(name = "name", nullable = false)
	private String  name;
	@Column(name = "description")
	private String  description;
	@Column(name = "priority")
	private short   priority; //from 1 to 5
	@Column(name = "isDone")
	private boolean isDone;
	@Column(name = "dateCreation", nullable = false)
	private Date    dateCreation;
	@Column(name = "dateEdition", nullable = false)
	private Date    dateEdition;
	@Column(name = "List_id")
	private long List_id;

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
