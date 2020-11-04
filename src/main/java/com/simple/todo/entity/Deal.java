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
	@Getter
	@Setter
	private String  name;

	/**
	 * Description of the person.
	 * -- SETTER --
	 * Changes the description of this deal.
	 *
	 * @param name The new value.
	 */
	@Column(name = "description")
	@Getter
	@Setter
	private String  description;

	/**
	 * Priority of the person. It's enum value from 1 to 5 points.
	 * -- SETTER --
	 * Changes the priority of this deal.
	 *
	 * @param name The new value.
	 */
	@Column(name = "priority")
	@Getter
	@Setter
	private int priority; //from 1 to 5

	/**
	 * Priority of the person. It's may be done(true) or undone(false).
	 * -- SETTER --
	 * Changes the completeness of this deal.
	 *
	 * @param name The new value.
	 */
	@Column(name = "is_done")
	@Getter
	@Setter
	private boolean isDone;

	/**
	 * Date of creation of the deal. It's automatically added date when was created
	 */
	@Column(name = "creation_date", nullable = false)
	@Getter
	private final Date    dateCreation; //maybe must "final"

	/**
	 * Date of edition of the deal
	 * -- SETTER --
	 * Changes the date of edition for this deal.
	 *
	 * @param name The new value.
	 */
	@Column(name = "edition_date", nullable = false)
	@Getter
	@Setter
	private Date    dateEdition; //TODO: automatically add date/ Dell setter. Modify dateEdition in all other setters.

	/**
	 * ID of the list which is foreign key.
	 * -- SETTER --
	 * Changes the foreign key of this deal.
	 *
	 * @param name The new value.
	 */
	@Column(name = "list_id")
	@Getter
	@Setter
	private UUID list_id;

	protected Deal() {
		this.isDone = false;
		Date date = new Date();
		this.dateCreation = date;
		this.dateEdition = date;
	}

	public Deal(String name, UUID id, String description, int priority) {
		this.name = name;
		this.id = id;
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

	public static String checkPriority(int priority){
		if (priority > 0 && priority <= 5){
			return "ok";
		}
		return "fail";
	}

	public static String checkDescription(String description) {
		int descriptionLength = description.length();
		if (descriptionLength > 500) return "Bad length of parameter description";
		return "ok";
	}

}
