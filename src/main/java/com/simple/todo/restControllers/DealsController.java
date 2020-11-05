package com.simple.todo.restControllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.simple.todo.dto.ApiResponse;
import com.simple.todo.entity.Database;
import com.simple.todo.entity.Deal;
import com.simple.todo.service.DealService;
import com.simple.todo.service.implement.DealServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RestController для дел, который работает с БД через сервис DealService.
 */
@RestController
@RequestMapping("deal")
public class DealsController {
	private final DealService dealService;

	@Autowired
	DealsController(DealService dealService) {
		this.dealService = dealService;
	}

	/**
	 * Получение списков дел с пагинацией.
	 * Ключи объекта:
	 * В "numberOfElements" можно отправить нужное кол-во элем. для отображения.
	 * "numberOfElements" содержит нужную страницу для открытия.
	 * "SortParameter" содержит имя поля, по которуму будут отсортированы дела.
	 * "SortType" как выводить дела.
	 *
	 * @param obj - объекст с параметрами
	 * @return ResponseEntity со статусом
	 */
	@GetMapping(value = "/", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> getDeals(@RequestBody ObjectNode obj) {
		//DealSpecificationsBuilder builder = new DealSpecificationsBuilder(); //Это пригодится для фильтрации
		int requestPage = 0;
		int numberOfElements = 10;
		UUID listId;
		String SortParameter = "dateCreation";
		String SortType = "ascending";
		Pageable pageable;

		if (obj.has("list_id")) {
			listId = UUID.fromString(obj.get("listId").asText()); //TODO: валидация list_id
		} else {
			return new ResponseEntity<>(new ApiResponse(false, "Parameter list_id not provided"), HttpStatus.NOT_ACCEPTABLE);
		}

		if (obj.has("requestPage")) {
			requestPage = obj.get("requestPage").asInt();
			if (requestPage < 0 || requestPage > 100_000) {
				return new ResponseEntity<>(new ApiResponse(false, "Parameter requestPage can be only 0-100000"), HttpStatus.NOT_ACCEPTABLE);
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

			if (!(SortParameter.equals("id") ||
					SortParameter.equals("name") ||
					SortParameter.equals("description") ||
					SortParameter.equals("priority") ||
					SortParameter.equals("isDone") ||
					SortParameter.equals("dateCreation") ||
					SortParameter.equals("dateEdition") ||
					SortParameter.equals("list_id"))) {
				return new ResponseEntity<>(
						new ApiResponse(false, "Bad SortParameter, it can be only id|" +
								"list_id|name|dateCreation|dateEdition|description|priority|isDone"),
						HttpStatus.NOT_ACCEPTABLE);
			}
		}

		if (obj.has("SortType")) {
			SortType = obj.get("SortType").asText();
			if (!(SortType.equals("ascending") || SortType.equals("descending"))) {
				return new ResponseEntity<>(new ApiResponse(false,
						"Bad SortType, it can be only ascending|descending"),
						HttpStatus.NOT_ACCEPTABLE);
			}
		}

		if (SortType.equals("ascending")) {
			pageable = PageRequest.of(requestPage, numberOfElements, Sort.by(SortParameter).ascending());
		} else {
			pageable = PageRequest.of(requestPage, numberOfElements, Sort.by(SortParameter).descending());
		}

		//TODO: UUID в specification + builder для фильтрации
//		if (obj.has("filter")) {
//			String filter = obj.get("filter").asText();
//
//			Pattern pattern =
//					Pattern.compile("([A-Za-z0-9_а-яА-Я]{2,})(" + SearchOperation.SIMPLE_OPERATION_SET + ")(\\*?)([A-Za-z0-9_а-яА-Я:\\-.+\\s]+?)(\\*?),",
//							Pattern.UNICODE_CHARACTER_CLASS);
//			Matcher matcher = pattern.matcher(filter + ",");
//			while (matcher.find()) {
//				builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
//			}
//
//			Specification<Deal> spec = builder.build();
//
//			//Specification<Task> spec1 = new TaskSpecification(new SearchCriteria("listId", SearchOperation.EQUALITY, listId));
//
//			//return new ResponseEntity<>(taskService.getAllWithSpec(Specification.where(spec1).and(spec), pageable), HttpStatus.OK);
//			return new ResponseEntity<>(dealService.getAllWithSpec(spec, pageable), HttpStatus.OK);
//		}

		try {
			return new ResponseEntity<>(dealService.getAll(pageable, listId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Добавление нового дела
	 *
	 * @param deal - сущность дела
	 * @return ResponseEntity со статусом
	 */
	@PostMapping(value = "/", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> addDeal(@RequestBody Deal deal) {
		String objName = deal.getName();
		if (objName == null) {
			return new ResponseEntity<>(new ApiResponse(false, "Parameter name not provided"), HttpStatus.NOT_ACCEPTABLE);
		}

		int objPriority = deal.getPriority();
		String objPriorityCheckResult = Deal.checkPriority(objPriority);
		if (!objPriorityCheckResult.equals("ok")) {
			return new ResponseEntity<>(new ApiResponse(false, objPriorityCheckResult), HttpStatus.NOT_ACCEPTABLE);
		}

		String objDescription = deal.getDescription();
		if (objDescription == null) {
			objDescription = "";
		} else {
			String objDescriptionCheckResult = Deal.checkDescription(objDescription);
			if (!objDescriptionCheckResult.equals("ok")) {
				return new ResponseEntity<>(new ApiResponse(false, "Description needed!"), HttpStatus.NOT_ACCEPTABLE);
			}
		}

		if (deal.getListId() == null){
			return new ResponseEntity<>(new ApiResponse(false, "ListId needed!"), HttpStatus.NOT_ACCEPTABLE);
		}

		Deal newDeal = new Deal(deal.getName(), objPriority, objDescription, deal.getListId());

		try {
			Optional<Deal> editResult = dealService.addDeal(newDeal);

			if (editResult.isPresent()) {
				return new ResponseEntity<>(editResult.get(), HttpStatus.CREATED);
			}

			return new ResponseEntity<>(new ApiResponse(false, "List not found by list_Id or database error"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Изменение сущности дела.
	 *
	 * @param obj - объекст с разными параметрами
	 * @return ResponseEntity со статусом
	 */
	@PutMapping(value = "/", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> changeDeal(@RequestBody ObjectNode obj) {
		Optional<String> name = Optional.empty();
		Optional<String> description = Optional.empty();
		Optional<Boolean> done = Optional.empty();
		Optional<Integer> priority = Optional.empty();

		if (!obj.has("id")) {
			return new ResponseEntity<>(new ApiResponse(false, "Parameter id not provided"), HttpStatus.NOT_ACCEPTABLE);
		}
		String id = obj.get("id").asText();
		if (id.equals("") || id.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(false, "Null id"), HttpStatus.NOT_ACCEPTABLE);
		}

		if (obj.has("name")) {
			String objName = obj.get("name").asText();
			if (objName.isEmpty()) {
				return new ResponseEntity<>(new ApiResponse(false, "Empty name"), HttpStatus.NOT_ACCEPTABLE);
			} else {
				name = Optional.of(objName);
			}
		}

		if (obj.has("description")) {
			String objDescription = obj.get("description").asText();
			if (objDescription.isEmpty()) {
				return new ResponseEntity<>(new ApiResponse(false, "Empty description"), HttpStatus.NOT_ACCEPTABLE);
			} else {
				description = Optional.of(objDescription);
			}
		}

		if (obj.has("done")) {
			done = Optional.of(obj.get("done").asBoolean());
		}

		if (obj.has("priority")) {
			int objPriority = obj.get("priority").asInt();
			String objPriorityCheckResult = Deal.checkPriority(objPriority);
			if (!objPriorityCheckResult.equals("ok")) {
				return new ResponseEntity<>(new ApiResponse(false, objPriorityCheckResult), HttpStatus.NOT_ACCEPTABLE);
			} else {
				priority = Optional.of(objPriority);
			}
		}

		if (!name.isPresent() && !description.isPresent() && !done.isPresent() && !priority.isPresent()) {
			return new ResponseEntity<>(new ApiResponse(false, "None of parameter name,description,priority,done provided"), HttpStatus.NOT_ACCEPTABLE);
		}

		try {
			Optional<Deal> editResult = dealService.changeDeal(UUID.fromString(id), name.toString(), description, priority, done);

			if (editResult.isPresent()) {
				return new ResponseEntity<>(editResult.get(), HttpStatus.OK);
			}

			return new ResponseEntity<>(new ApiResponse(false, "Deal not found, or database error (List not found)"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Установление готовности (done) дела
	 *
	 * @param id - id сущности
	 * @return ResponseEntity со статусом
	 */
	@PutMapping(value = "/setDone/{id}", produces = "application/json")
	public ResponseEntity<Object> setDone(@PathVariable(name = "id") String id) {
		if (id.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(false, "Empty id"), HttpStatus.NOT_ACCEPTABLE);
		}

		try {
			Optional<Deal> editDeal = dealService.setDone(UUID.fromString(id));

			if (editDeal.isPresent()) {
				return new ResponseEntity<>(editDeal.get(), HttpStatus.OK);
			}

			return new ResponseEntity<>(new ApiResponse(false, "Deal not found, or database error (List not found)"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Удаление сущности дела
	 *
	 * @param id - id сущности
	 * @return ResponseEntity со статусом
	 */
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Object> deleteDeal(@PathVariable(name = "id") String id) {
		if (id.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(false, "Empty id"), HttpStatus.NOT_ACCEPTABLE);
		}

		try {
			if (dealService.deleteDeal(UUID.fromString(id))) {
				return new ResponseEntity<>(new ApiResponse(true, "Deal deleted"), HttpStatus.OK);
			}

			return new ResponseEntity<>(new ApiResponse(false, "Deal not found, or database error (List not found)"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
