package com.simple.todo.entity;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="LISTS")
public class ListDeal {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private long    id;
	@Column(name = "name", nullable = false)
	private String  name;
	@Column(name = "dateCreation", nullable = false)
	private Date    dateCreation;
	@Column(name = "dateEdition", nullable = false)
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
}
