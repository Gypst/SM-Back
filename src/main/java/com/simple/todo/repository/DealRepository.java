package com.simple.todo.repository;

import java.util.List;
import java.util.UUID;

import com.simple.todo.entity.Deal;
import com.simple.todo.entity.ListDeal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository extends JpaRepository<Deal, UUID>, JpaSpecificationExecutor<Deal> {

	void deleteByList_id(UUID id);

	List<Deal> findAllByList_id(UUID id);

	Page<Deal> findAllByList_id(Pageable pageable, UUID id);
}
