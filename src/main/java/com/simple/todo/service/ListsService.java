package com.simple.todo.service;

import com.simple.todo.dto.ListsResponse;
import com.simple.todo.entity.ListDeal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

/**
 * Сервис для работы с сущностью List
 */
public interface ListsService {

	/**
	 * Добавление нового List
	 *
	 * @param list - сущность List
	 * @return Сущность List со всеми параметрами сохраненными в БД
	 */
	ListDeal addList(ListDeal list);

	ListsResponse getAll(Pageable pageable);

	ListsResponse getAllWithSpec(Specification<ListDeal> spec, Pageable pageable);

	Optional<ListDeal> changeList(UUID id, String name);

	boolean deleteList(UUID id);

	void checkIfListShouldBeDone(ListDeal list, UUID excludeCheckDealId);

	void saveList(ListDeal list);

}
