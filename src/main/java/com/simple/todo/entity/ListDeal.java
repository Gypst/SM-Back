package com.simple.todo.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * List entity that displays a table in a database
 */
@Entity
@Table(name="LISTS")
public class ListDeal {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) // это не нужно UUID выдекавать в конструкторе
	@Column(name = "id", nullable = false)
	@Getter
	private UUID id;

	@Column(name = "name", nullable = false)
	@Getter @Setter
	private String  name;

	@Column(name = "dateCreation", nullable = false)
	@Getter
	private Date    dateCreation;

	@Column(name = "dateEdition", nullable = false)
	@Getter @Setter
	private Date    dateEdition;

	protected ListDeal() {
		Date date = new Date();
		this.dateCreation = date;
		this.dateEdition = date;
	}

	public ListDeal(String name) {
		this.name = name;
		Date date = new Date();
		this.dateCreation = date;
		this.dateEdition = date;
	}

	@Override
	public String toString() {
		return String.format(
				"ListDeal[id=%d, name='%s', dateCreation='%s', dateEdition='%s']",
				id, name, dateCreation, dateEdition);
	}

}
