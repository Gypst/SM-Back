package com.simple.todo.restControllers;

import com.simple.todo.exceptions.NotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
@Post методы не должны содержать в URL передаваемые аргументы, они должны быть в теле зашифрованы.
 */
//Возвращаемые значения только для отладки
@RestController
@RequestMapping("list")
public class ListsController {
	private int counterOfLists = 3;
	private List<Map<String, String>> bdEmu = new ArrayList<Map<String, String>>() {{
		add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First message"); }});
		add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second message"); }});
		add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third message"); }});
	}};

	@GetMapping
	public List<Map<String, String >> getArrayLists(@RequestParam(name = "count", defaultValue = "10") int count, Model model) {
//		if (count <= 0) {
//			count = 10;
//		}
//		count = count > 100 ? 10 : count;
//
//		model.addAttribute("lists", count);
		return bdEmu;
	}

	@GetMapping("{id}")
	public Map<String, String> getList(@PathVariable String id){
		return getListFromDB(id);
	}

	private Map<String, String> getListFromDB(String id) {
		return bdEmu.stream().filter(bdEmu -> bdEmu.get("id").equals(id))
				.findFirst()
				.orElseThrow(NotFoundException::new);
	}

	@PostMapping
	public Map<String, String> addList(@RequestBody Map<String, String> list){
		list.put("id", String.valueOf(++counterOfLists));
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
		bdEmu.remove(message);
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