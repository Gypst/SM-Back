package com.simple.todo.repository;

import java.util.List;
import java.util.UUID;

import com.simple.todo.entity.ListDeal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListDealRepository extends CrudRepository<ListDeal, UUID>{

	List<ListDeal> findByName(String name);

	ListDeal findById(long id);
}

