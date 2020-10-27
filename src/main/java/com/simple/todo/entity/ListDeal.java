package com.simple.todo.entity;

import org.hibernate.annotations.Table;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Java-doc
 */
@Entity
//@Table(name="list")
public class ListDeal {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) // это не нужно UUID выдекавать в конструкторе
	private long    id; // тип идентификатора UUID
	private String  name;
	private Date    dateCreation;
	private Date    dateEdition;

	protected ListDeal() {}

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


	// TODO: методы герреты и сеттеры успещно зхаменяются на анотации @Getter/@Setter lomboka
	public Date getDateCreation() {
		return dateCreation;
	}

	public Date getDateEdition() {
		return dateEdition;
	}

	public String getName() {
		return name;
	}

	//Нужен ли в сущности сеттер? В примере не было сеттеров https://spring.io/guides/gs/accessing-data-jpa/
	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	//by default 10 deals must be returned and if > 100 else how needed (= count)
//	public Deal[] getDeals(int count) {
//		return Deals;
//	}

	//Вопрос аналогичен вопросу с сеттером https://spring.io/guides/gs/accessing-data-jpa/
	public ListDeal addDeal(String name){
		return  this;
	}
	// TODO: не понял зачем этот метод
}
