package com.simple.todo.dto;

import com.simple.todo.entity.Deal;
import com.simple.todo.entity.ListDeal;
import org.springframework.data.domain.Page;

public class DealResponse {

	private int done;

	private int notDone;

	Page<Deal> page;

	public DealResponse(int done, int notDone, Page<Deal> page) {
		this.done = done;
		this.notDone = notDone;
		this.page = page;
	}
}