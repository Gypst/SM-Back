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
 * RestController for deals from database
 */
@RestController
@RequestMapping("deal")
public class DealsController {
	private List<Map<String, String>> bdEmu = Database.bdEmu;

	private final DealService dealService;

	@Autowired
	DealsController(DealService dealService){this.dealService = dealService;}

	/**
	 * Получение списков дел с пагинацией
	 *
	 * @param obj - объекст с параметрами
	 * @return ResponseEntity со статусом
	 */
	@GetMapping(value = "/", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> getDeals(@RequestBody ObjectNode obj, OptionalInt numberOfElementsRequired) {
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

			if (!(  SortParameter.equals("id") ||
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

	@GetMapping("/")
	public Deal getDeal(long id){return null;}

	@PostMapping("/addDeal")
	public boolean addDeal(@RequestParam(name = "name") String name){return false;}

	@PostMapping("/deleteDeal")
	public boolean deleteDeal(@RequestParam(name = "id") long id){return  false;}

	@PostMapping("/deleteAllDeal")
	public boolean deleteAllDeal(){return  false;}

	// TODO: не хватает метода посмотреть все дела в списке с фильрацией сортировкой и пагинацией
}
