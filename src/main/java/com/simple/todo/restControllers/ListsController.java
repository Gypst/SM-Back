package com.simple.todo.restControllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.simple.todo.dto.ApiResponse;
import com.simple.todo.dto.ListsResponse;
import com.simple.todo.entity.Database;
import com.simple.todo.entity.ListDeal;
import com.simple.todo.exceptions.NotFoundException;
import com.simple.todo.service.ListsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RestController для списка дел, который работает с БД через сервис ListsService.
 */
@RestController
@RequestMapping("list")
public class ListsController {

	private final ListsService listsService;

	@Autowired
	ListsController(ListsService listsService){this.listsService = listsService;}

	/**
	 * Добавление нового списка дел
	 *
	 * @param list - сущность списка
	 * @return ResponseEntity со статусом
	 */
	@PostMapping(value = "/", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> addList(@RequestBody ListDeal list) {
		String objName = list.getName();
		if (objName.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(false, "Empty name"), HttpStatus.NOT_ACCEPTABLE);
		}

		ListDeal newList = new ListDeal(list.getName());

		try {
			return new ResponseEntity<>(listsService.addList(newList), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Получение списков List с пагинацией.
	 * Ключи объекта:
	 * requestPage - запрашиваемая страница
	 * numberOfElements - кол-во элементов на странице
	 * SortParameter - по какому полю сортируем
	 * SortType - по возростанию (ascending) или убыванию (descending)
	 *
	 * @param obj - объекст с разными параметрами
	 * @return ResponseEntity со статусом
	 */
	@GetMapping(value = "/", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> getLists(@RequestBody ObjectNode obj) {
		//ListSpecificationsBuilder builder = new ListSpecificationsBuilder();
		int requestPage = 0;
		int numberOfElements = 10;
		String SortParameter = "dateCreation";
		String SortType = "ascending";
		Pageable pageable;

		if (obj.has("requestPage")) {
			requestPage = obj.get("requestPage").asInt();
			if (requestPage < 0 || requestPage > 100_000) {
				return new ResponseEntity<>(new ApiResponse(false,
						"Parameter requestPage can be only 0-100000"), HttpStatus.NOT_ACCEPTABLE);
			}
		}

		if (obj.has("numberOfElements")) {
			numberOfElements = obj.get("numberOfElements").asInt();
			if (numberOfElements < 1 || numberOfElements > 100) {
				numberOfElements = 10;
			}
		}

		if (obj.has("SortParameter")) {
			SortParameter = obj.get("SortParameter").asText();
			if (!(  SortParameter.equals("id") ||
					SortParameter.equals("name") ||
					SortParameter.equals("dateCreation") ||
					SortParameter.equals("dateEdition") ||
					SortParameter.equals("done"))) {

				return new ResponseEntity<>(
						new ApiResponse(false, "Bad SortParameter, " +
								"it can be only id|name|dateCreation|dateEdition|done"), HttpStatus.NOT_ACCEPTABLE);
			}
		}

		if (obj.has("SortType")) {
			SortType = obj.get("SortType").asText();
			if (!("ascending".equals(SortType) || "descending".equals(SortType))) {
				return new ResponseEntity<>(new ApiResponse(false,
						"Bad SortType, it can be only ascending|descending"), HttpStatus.NOT_ACCEPTABLE);
			}
		}

		if (SortType.equals("ascending")) {
			pageable = PageRequest.of(requestPage, numberOfElements, Sort.by(SortParameter).ascending());
		} else {
			pageable = PageRequest.of(requestPage, numberOfElements, Sort.by(SortParameter).descending());
		}
		//TODO: фильтрация запроса
//		if (obj.has("filter")) {
//			String filter = obj.get("filter").asText();
//
//			Pattern pattern =
//					Pattern.compile("([A-Za-z0-9_а-яА-Я]{2,})(" + SearchOperation.SIMPLE_OPERATION_SET + ")(\\*?)([A-Za-z0-9_а-яА-Я:\\-.+\\s]+?)(\\*?),", Pattern.UNICODE_CHARACTER_CLASS);
//			Matcher matcher = pattern.matcher(filter + ",");
//			while (matcher.find()) {
//				builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
//			}
//
//			Specification<List> spec = builder.build();
//
//			return new ResponseEntity<>(listService.getAllWithSpec(spec, pageable), HttpStatus.OK);
//		}

		try {
			return new ResponseEntity<>(listsService.getAll(pageable), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Изменения параметров сущности дела
	 *
	 * @param obj - объекст с разными параметрами
	 * @return ResponseEntity со статусом
	 */
	@PutMapping(value = "/", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> changeList(@RequestBody ObjectNode obj) {
		if (!obj.has("id") || !obj.has("name")) {
			return new ResponseEntity<>(new ApiResponse(false, "Parameter id or name not provided"),
																				HttpStatus.NOT_ACCEPTABLE);
		}

		String objId = obj.get("id").asText();
		if (objId.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(false, "Empty id"), HttpStatus.NOT_ACCEPTABLE);
		}

		String objName = obj.get("name").asText();
		if (objName.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(false, "Empty name"), HttpStatus.NOT_ACCEPTABLE);
		}

		try {
			Optional<ListDeal> editResult = listsService.changeList(UUID.fromString(objId), objName);
			if (editResult.isPresent()) {
				return new ResponseEntity<>(editResult.get(), HttpStatus.OK);
			}

			return new ResponseEntity<>(new ApiResponse(false, "List not found"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Удаление сущности списка
	 *
	 * @param id - id сущности
	 * @return ResponseEntity со статусом
	 */
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Object> deleteList(@PathVariable(name = "id") String id) {
		if (id.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(false, "Empty id"), HttpStatus.NOT_ACCEPTABLE);
		}

		try {
			if (listsService.deleteList(UUID.fromString(id))) {
				return new ResponseEntity<>(new ApiResponse(true, "List deleted"), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse(false, "List not found"), HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}