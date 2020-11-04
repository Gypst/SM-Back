package com.simple.todo.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Сущность списка дел, которая отображает строку из таблицу в БД
 */
@Data
@Entity
@Table(name="LISTS")
public class ListDeal {

	/**
	 * ID списка
	 */
	@Id
	@Column(name = "id", nullable = false)
	@Getter
	private UUID id;

	/**
	 * Название списка
	 */
	@Column(name = "name", nullable = false)
	@Getter
	@Setter
	private String  name;

	/**
	 * Дата создания списка, поле обновляется автоматически.
	 */
	@Column(name = "creation_date", nullable = false)
	@Getter
	private final Date    dateCreation; //maybe must "final"

	/**
	 * Дата изменения списка, поле обновляется автоматически.
	 */
	@Column(name = "edition_date", nullable = false)
	@Getter
	@Setter
	private Date    dateEdition;

	@Column(name = "is_done", nullable = false)
	@Getter
	@Setter
	private boolean done;

	protected ListDeal() {
		this.id = UUID.randomUUID();
		Date date = new Date();
		this.dateCreation = date;
		this.dateEdition = date;
	}

	/**
	 * Создаёт список с уникальным ID, указанным названием и проставленными полями дат создания и редактирования
	 * @param name Название списка
	 */
	public ListDeal(String name) {
		this.id = UUID.randomUUID();
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
