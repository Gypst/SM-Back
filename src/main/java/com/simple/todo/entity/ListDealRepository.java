package com.simple.todo.entity;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ListDealRepository extends CrudRepository<ListDeal, Long>{

	List<ListDeal> findByName(String name);

	ListDeal findById(long id);
}

