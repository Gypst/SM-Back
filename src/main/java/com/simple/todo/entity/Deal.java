package com.simple.todo.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Deal entity that displays a table in a database
 */
@Entity
@Table(name = "DEALS")
public class Deal {

	/**
	 * ID of the deal in database
	 *
	 * @param id New value for each deal.
	 * @return The current id of this deal
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) // это не нужно идентификатор в контрукторе выдавать
	@Column(name = "id", nullable = false)
	@Getter
	private UUID    id;

	/**
	 * Name of the deal.
	 * -- SETTER --
	 * Changes the name of this deal.
	 *
	 * @param name The new value.
	 */
	@Column(name = "name", nullable = false)
	@Getter @Setter
	private String  name;

	/**
	 * Description of the person.
	 * -- SETTER --
	 * Changes the description of this deal.
	 *
	 * @param name The new value.
	 */
	@Column(name = "description")
	@Getter @Setter
	private String  description;

	/**
	 * Priority of the person. It's enum value from 1 to 5 points.
	 * -- SETTER --
	 * Changes the priority of this deal.
	 *
	 * @param name The new value.
	 */
	@Column(name = "priority")
	@Getter @Setter
	private short   priority; //from 1 to 5 // TODO: приоритер сделать перечислением

	/**
	 * Priority of the person. It's may be done(true) or undone(false).
	 * -- SETTER --
	 * Changes the completeness of this deal.
	 *
	 * @param name The new value.
	 */
	@Column(name = "isDone")
	@Getter @Setter
	private boolean isDone;

	/**
	 * Date of creation of the deal. It's automatically added date when was created
	 */
	@Column(name = "dateCreation", nullable = false)
	@Getter
	private Date    dateCreation;

	//TODO: It's automatically added date when was edited?
	/**
	 * Date of edition of the deal
	 * -- SETTER --
	 * Changes the date of edition for this deal.
	 *
	 * @param name The new value.
	 */
	@Column(name = "dateEdition", nullable = false)
	@Getter @Setter
	private Date    dateEdition;

	/**
	 * ID of the list which is foreign key.
	 * -- SETTER --
	 * Changes the foreign key of this deal.
	 *
	 * @param name The new value.
	 */
	@Column(name = "List_id")
	@Getter @Setter
	private long List_id;

	protected Deal() {
		this.isDone = false;
		Date date = new Date();
		this.dateCreation = date;
		this.dateEdition = date;
	}

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

}
