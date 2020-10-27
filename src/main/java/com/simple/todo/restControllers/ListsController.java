package com.simple.todo.restControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * java-doc пишется так
 */

/*
Надо переназначить мапинги и использовать нужные виды запросов,
а не везде GetMapping. Например, для загрузки нового листа нельзя использовать Get,
так как он не подходит под его характер (идемподентность): он меняет данные сервера
 */
/*
@Post методы не должны содержать в URL передаваемые аргументы, они должны быть в теле зашифрованы.
 */ // TODO: не совсем так. какая-то часть может содержаться, например идентификатор, но не оябзательно
@Controller
// TODO: у нас не просто контроллеры - у нас RestController-ы. они не возвращают данные для станицы, они возвращают
//  объекты в виде JSON/ на выходе модно полать объект какого-=либо сериализуемого класса,
//  а Spring свернёт его в JSON сам
@RequestMapping("/list")
public class ListsController {

//	@GetMapping("/getCountList")
//	public int getCountLists() {
//		return 0;
//	}
	//Возвращаемые значения только для отладки
	@GetMapping("/")
	public String getArrayLists(@RequestParam(name = "count", defaultValue = "10") int count, Model model) {
	    // TODO: нужна сортировка филттрация и пагинация
		if (count <= 0) {
			count = 10;
		}
		count = count > 100 ? 10 : count;

		model.addAttribute("lists", count);
		return "getList";
	}

	@PostMapping("/addList") // TODO: можно просто add. то сто добавить list это и так понятно по @RequestMapping("/list")
	public String addList(@RequestParam(name = "name", required = true) String name, Model model){
		model.addAttribute("name", name);
		return "addList";
	}

	@PostMapping("/deleteList") // TODO: тоже самое
	public String deleteList(@RequestParam(name = "id") long id, Model model){
		model.addAttribute("text", ("You delete list with id = " + id));
		return "text";
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
