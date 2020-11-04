package com.simple.todo.service;

import com.simple.todo.dto.DealResponse;
import com.simple.todo.entity.Deal;
import com.simple.todo.entity.ListDeal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

/**
 * Интерфейс предполагающий работу с делами из БД
 */
public interface DealService {

		/**
		 * Добавление нового дела
		 *
		 * @param deal - сущность Deal
		 * @return Сущность Deal со всеми параметрами из БД
		 */
		Optional<Deal> addDeal(Deal deal);

		/**
		 * Поиск лист дел по id
		 *
		 * @param id - id ListDeal
		 * @return Сущность ListDeal
		 */
		Optional<ListDeal> findListById(UUID id);

		/**
		 * Поиск всех дел в листе по id
		 *
		 * @param pageable - параметры страницы (сколько элементов на страницу, номер страницы и сортировка)
		 * @param id       - id ListDeal к которому принадлежат дела
		 * @return Вспомогательную сущность со списком Deal, пагинацией и дополнительной информацией
		 */
		DealResponse getAll(Pageable pageable, UUID id);

		/**
		 * Поиск всех дел
		 *
		 * @param spec     - параметры фильтрации
		 * @param pageable - параметры страницы (сколько элементов на страницу, номер страницы и сортировка)
		 * @return Вспомогательную сущность со списком ListDeal, пагинацией и дополнительной инфомрацией
		 */
		DealResponse getAllWithSpec(Specification<Deal> spec, Pageable pageable);

		/**
		 * Изменение сущности дела
		 *
		 * @param id          - id Deal
		 * @param name        - новое имя
		 * @param description - новое описание
		 * @param priority     - новая приоритет
		 * @param done        - новый статус готовности
		 * @return Сущность Task со всеми параметрами сохраненными в БД если удалось сохранить
		 */
		Optional<Deal> changeDeal(UUID id, String name, Optional<String> description, Optional<Integer> priority, Optional<Boolean> done);

		/**
		 * Изменение готовности дела на "готово"
		 *
		 * @param id - id сущности Deal
		 * @return Сущность Deal со всеми параметрами из БД
		 */
		Optional<Deal> setDone(UUID id);

		/**
		 * Изменение готовности дела на "не готово"
		 *
		 * @param id - id сущности Deal
		 * @return Сущность Deal со всеми параметрами из БД
		 */
		Optional<Deal> setUnDone(UUID id);

		/**
		 * Удаление сущности дела
		 *
		 * @param id - id Deal
		 * @return boolean статус успешности выполнения
		 */
		boolean deleteDeal(UUID id);

}