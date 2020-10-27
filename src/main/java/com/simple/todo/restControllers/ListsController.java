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
 * RestController for lists from database
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
		List<Map<String, String >> recivedDB = new ArrayList<>(count);
		do {
			recivedDB.add(getListFromDB(Integer.toString(count)));
		} while (--count > 0);

		return recivedDB;
	}

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

	@PostMapping
	public Map<String, String> addList(@RequestBody Map<String, String> list){
		list.put("id", String.valueOf(Database.counterOfLists += 1));
		return list;
	}

	@PutMapping("{id}")
	public Map<String, String> changeList(@PathVariable String id, @RequestBody Map<String, String> message){
		Map<String, String> messageFromDB= getListFromDB(message.get("id"));

		messageFromDB.putAll(message);
		messageFromDB.put("id", id);

		return messageFromDB;
	}

	@DeleteMapping("{id}")
	public void deleteList(@PathVariable String id){
		Map<String, String> message = getList(id);
		dbEmu.remove(message);
	}

	@PostMapping("/deleteAllList")
	public String deleteAllList(Model model){
		model.addAttribute("text", "You delete all lists =(");
		return "text";
	}

	@GetMapping("/test")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		if (name.equals("Tonny")){
			name = "But not you! 0_0";
		}
		model.addAttribute("name", name);
		return "greeting";
	}
}