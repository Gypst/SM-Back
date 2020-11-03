package com.simple.todo.restControllers;

import com.simple.todo.entity.Database;
import com.simple.todo.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/*
@Post методы не должны содержать в URL передаваемые аргументы, они должны быть в теле зашифрованы.
 */
//Возвращаемые значения только для отладки

/**
 * RestController for lists from database.
 */
// TODO: java-doc лучше писать на русском языке, т.к. команда разработчиков русскоязычная
@RestController
@RequestMapping("list")
public class ListsController {

    private List<Map<String, String>> dbEmu = Database.bdEmu;
    // TODO: вместо этого нам нужны сервисы и интерфейсы к ним. в рестах сервисы подключаются через интерфейсы
    //  и аннотоцию @Autowired
    //  интерфейсы нужны к сервисам, чтоб мы могли быстро подменить реализацию

    // TODO: если метод rest что-то возвращает, то лучше это делать не через Map<String, String>, т.к. объект может быть
    //  сложнее чем набор строковых ключ-значение, для возврата обчно используют ДТО. и вообще rest-ы ничего не должны
    //  знать о сущностях модели, иначе мы жестко связаны - как следствие цена исправления велика

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
				.orElseThrow(NotFoundException::new); // TODO: мы так бросаем исключение, то оно должно обрабытываться
        // в ErrorHandler? пример https://mkyong.com/spring-boot/spring-rest-error-handling-example/
	}

	/**
	 * Adding a new list to the database.
	 *
	 * @param list Required list which will be added to the database
	 * @return
	 */
	@PostMapping
	public Map<String, String> addList(@RequestBody Map<String, String> list){
		Map<String, String> toInsert = list;
		int t = ++Database.counterOfLists;
		toInsert.put("id", Integer.toString(t));
		dbEmu.add(list);
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
		int index = Integer.parseInt(id);
		dbEmu.remove(--index);

		Map<String, String> delGup = new HashMap<String, String>();
		delGup.put("id", id);
		delGup.put("text", "Deleted");
		dbEmu.add(index, delGup);
	}

	/**
	 * Delete all lists from database.
	 */
	@PostMapping("/deleteAllList")
	public String deleteAllList(){
		return null;
	}

}
