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

/**
 * Репозиторий дел, наследуемый от JpaRepository & JpaSpecificationExecutor.
 * Используется для взаимодействия с БД.
 */
@Repository
public interface DealRepository extends JpaRepository<Deal, UUID>, JpaSpecificationExecutor<Deal> {

	void deleteByListId(UUID id);

	List<Deal> findAllByListId(UUID id);

	Page<Deal> findAllByListId(Pageable pageable, UUID id);
}
