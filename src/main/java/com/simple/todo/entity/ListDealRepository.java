package com.simple.todo.entity;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *  TODO: java-doc на русском
 */
// TODO: все репозитории вынести в отдельные пакеты
public interface ListDealRepository extends CrudRepository<ListDeal, Long>{

	List<ListDeal> findByName(String name);

	ListDeal findById(long id);
}

