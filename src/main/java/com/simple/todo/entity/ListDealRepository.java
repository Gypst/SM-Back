package com.simple.todo.entity;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Java-doc
 */
public interface ListDealRepository extends CrudRepository<ListDeal, Long>{ // TODO: нужно вынести все репозитории в
    // отдельный пакет (каталог)

	List<ListDeal> findByName(String name);

	ListDeal findById(long id);
}

