package com.simple.todo.service;

import com.simple.todo.dto.ListsResponse;
import com.simple.todo.entity.ListDeal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

public interface ListsService {

	ListDeal addList(ListDeal list);

	ListsResponse getAll(Pageable pageable);

	ListsResponse getAllWithSpec(Specification<ListDeal> spec, Pageable pageable);

	Optional<ListDeal> changeList(UUID id, String name);

	boolean deleteList(UUID id);

	void checkIfListShouldBeDone(ListDeal list, UUID excludeCheckTaskId);

	void saveList(ListDeal list);






	//////////////////////////////////////////////////////////////////////////////////////////////////
//	public static List<Map<String, String>> dbEmu = Database.bdEmu;
//	public static Map<String, String> getListFromDB(String id) {
//		return dbEmu.stream().filter(bdEmu -> bdEmu.get("id").equals(id))
//				.findFirst()
//				.orElseThrow(NotFoundException::new);
//	}
//
//	public static List<Map<String, String >> getLists(@RequestParam(name = "count", defaultValue = "10")int count){
//
//		// TODO: нужна сортировка филттрация и пагинация
//		if (count <= 0 && count < Database.counterOfLists) {
//			count = 10;
//		}
//		count = count > 100 ? 10 : count;
//
//		new ListDTONOT.Response.Public();
//
//		List<Map<String, String >> receivedDB = new ArrayList<>(count);
//		do {
//			receivedDB.add(getListFromDB(Integer.toString(count)));
//		} while (--count > 0);
//
//		return receivedDB;
//	}



}
