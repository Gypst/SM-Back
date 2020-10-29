package com.simple.todo.restControllers;

import com.simple.todo.entity.Database;
import com.simple.todo.exceptions.NotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
@Post методы не должны содержать в URL передаваемые аргументы, они должны быть в теле зашифрованы.
 */
//Возвращаемые значения только для отладки

/**
 * RestController for lists from database.
 */
@RestController
@RequestMapping("list")
public class ListsController {
	private List<Map<String, String>> dbEmu = Database.bdEmu;

	/**
	 * Receive all lists from database.
	 *
	 * @param count How many lists needed.
	 * @return New database with added list.
	 */
	@GetMapping
	public List<Map<String, String >> getArrayLists(@RequestParam(name = "count", defaultValue = "10") int count) {
		// TODO: нужна сортировка филттрация и пагинация
		if (count <= 0 && count < Database.counterOfLists) {
			count = 10;
		}
		count = count > 100 ? 10 : count;

		//Reverse order
		List<Map<String, String >> receivedDB = new ArrayList<>(count);
		do {
			receivedDB.add(getListFromDB(Integer.toString(count)));
		} while (--count > 0);

		return receivedDB;
	}

	/**
	 * Receive list with the current id via getLustFromDB() function.
	 *
	 * @param id Id of the list.
	 * @return Receive list with the current id.
	 */
	@GetMapping("{id}")
	public Map<String, String> getList(@PathVariable String id){
		return getListFromDB(id);
	}

	/**
	 * Finding list by id in database.
	 *
	 * @param id Required id to find list.
	 * @return List from database with required id.
	 */
	private Map<String, String> getListFromDB(String id) {
		return dbEmu.stream().filter(bdEmu -> bdEmu.get("id").equals(id))
				.findFirst()
				.orElseThrow(NotFoundException::new);
	}

	/**
	 * Adding a new list to the database.
	 *
	 * @param list Required list which will be added to the database
	 * @return
	 */
	@PostMapping
	public Map<String, String> addList(@RequestBody Map<String, String> list){
		list.put("id", String.valueOf(Database.counterOfLists += 1));
		return list;
	}

	/**
	 * Update a list with the current id.
	 *
	 * @param id ID of updating list.
	 * @param message Key-value which will exchanging old value by id.
	 * @return Updated database.
	 */
	//TODO: Check working
	@PutMapping("{id}")
	public List<Map<String, String>> changeList(@PathVariable String id, @RequestBody Map<String, String> message){

//		Map<String, String> messageFromDB = getListFromDB(message.get("id"));
//		messageFromDB.putAll(message);
//		messageFromDB.put("id", id); //or "message.get("id")" instead "id"?

		dbEmu.remove(Integer.parseInt(id));
		dbEmu.add(Integer.parseInt(id), message);
		return dbEmu;
	}

	/**
	 * Delete list with current id.
	 *
	 * @param id ID of deleting list.
	 */
	@DeleteMapping("{id}")
	public void deleteList(@PathVariable String id){
		Map<String, String> message = getList(id);
		dbEmu.remove(message);
	}

	/**
	 * Delete all lists from database.
	 */
	@PostMapping("/deleteAllList")
	public String deleteAllList(){
		return null;
	}

}