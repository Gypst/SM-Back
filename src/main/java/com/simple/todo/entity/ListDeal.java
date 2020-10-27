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

	/**
	 * ID of the list in database
	 *
	 * @param id New value for each list.
	 * @return The current id of this list
	 */
	@Id
	@Column(name = "id", nullable = false)
	@Getter
	private UUID id;

	/**
	 * Name of the list.
	 * -- SETTER --
	 * Changes the name of this list.
	 *
	 * @param name The new value.
	 */
	@Column(name = "name", nullable = false)
	@Getter @Setter
	private String  name;

	/**
	 * Date of creation of the list. It's automatically added date when was created
	 */
	@Column(name = "dateCreation", nullable = false)
	@Getter
	private final Date    dateCreation; //maybe must "final"

	/**
	 * Date of edition of the list
	 * -- SETTER --
	 * Changes the date of edition for this list.
	 *
	 * @param name The new value.
	 */
	@Column(name = "dateEdition", nullable = false)
	@Getter @Setter
	private Date    dateEdition; //TODO: automatically add date/ Dell setter

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
