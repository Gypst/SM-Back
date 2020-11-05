package com.simple.todo.dto;

import com.simple.todo.entity.ListDeal;
import lombok.Data;
import org.springframework.data.domain.Page;

/**
 * Класс, который используется контроллером для ответа на запросы пользователей.
 */
@Data
public class ListsResponse {

	private int done;

	private int notDone;

	Page<ListDeal> page;

	public ListsResponse(int done, int notDone, Page<ListDeal> page) {
		this.done = done;
		this.notDone = notDone;
		this.page = page;
	}
}