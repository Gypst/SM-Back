package com.simple.todo.classes;

import java.util.Date;

public class ListDeal {

	private Date    dateCreation;
	private Date    dateEdition;
	private String  name;
	private Deal[]  Deals;
	private long    id;

	public Date getDateCreation() {
		return dateCreation;
	}

	public Date getDateEdition() {
		return dateEdition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	//by deafult 10 deals must be returned and if > 100 else how needed (= count)
	public Deal[] getDeals(int count) {
		return Deals;
	}

	public ListDeal addDeal(String name){
		return  this;
	}
}
