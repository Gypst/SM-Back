package com.simple.todo.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Сущность дела, которая отражает строку из таблицы в базе данных
 */
@Entity
@Table(name = "DEALS")
public class Deal {

	/**
	 * ID дела в БД
	 */
	@Id
	@Column(name = "id", nullable = false)
	@Getter
	private UUID    id;

	/**
	 * Название дела
	 */
	@Column(name = "name", nullable = false)
	@Getter
	@Setter
	private String  name;

	/**
	 * Описание дела
	 */
	@Column(name = "description")
	@Getter
	@Setter
	private String  description;

	/**
	 * Приоритет дела (от 1 до 5)
	 */
	@Column(name = "priority")
	@Getter
	@Setter
	private int priority; //from 1 to 5

	/**
	 * Готовность дела (true/false)
	 */
	@Column(name = "is_done")
	@Getter
	@Setter
	private boolean isDone;

	/**
	 * Дата создания дела. Автоматически заполняемое поле
	 */
	@Column(name = "creation_date", nullable = false)
	@Getter
	private final Date    dateCreation; //maybe must "final"

	/**
	 * Дата изменения дела. Автоматически заполняемое поле
	 */
	@Column(name = "edition_date", nullable = false)
	@Getter
	@Setter
	private Date    dateEdition; //TODO: automatically add date/ Dell setter. Modify dateEdition in all other setters.

	/**
	 * ID списка, в котором находится дело
	 */
	@Column(name = "listId")
	@Getter
	@Setter
	private UUID listId;

	protected Deal() {
		this.id = UUID.randomUUID();
		this.isDone = false;
		Date date = new Date();
		this.dateCreation = date;
		this.dateEdition = date;
	}

	public Deal(String name, String description, int priority) {
		this.name = name;
		this.id = UUID.randomUUID();
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

	/**
	 * Проверяет приоритет, который должен быть от 1 до 5.
	 * @param priority Приоритет дела
	 * @return "ok"/"fail"
	 */
	public static String checkPriority(int priority){
		if (priority > 0 && priority <= 5){
			return "ok";
		}
		return "fail";
	}

	/**
	 * Проверяет длинну дела (не больше 500)
	 * @param description Строка описания дела
	 * @return "ok"/"Bad length of parameter description"
	 */
	public static String checkDescription(String description) {
		int descriptionLength = description.length();
		if (descriptionLength >= 500) return "Bad length of parameter description";
		return "ok";
	}

}
