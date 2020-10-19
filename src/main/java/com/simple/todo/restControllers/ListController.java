package com.simple.todo.restControllers;

import com.simple.todo.classes.ListDeal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ListController {

//	@GetMapping("/getCountList")
//	public int getCountLists() {
//		return 0;
//	}
	//Возвращаемые значения только для отладки
	@GetMapping("/")
	public String getArrayLists(@RequestParam(name = "count", defaultValue = "10") int count, Model model) {
		if (count <= 0) {
			count = 10;
		}
		count = count > 100 ? 10 : count;

		model.addAttribute("lists", count);
		return "getList";
	}

	@GetMapping("/addList")
	public String addList(@RequestParam(name = "name", required = true) String name, Model model){
		model.addAttribute("name", name);
		return "addList";
	}

	@GetMapping("/deleteList")
	public String deleteList(@RequestParam(name = "id") long id, Model model){
		model.addAttribute("text", ("You delete list with id = " + id));
		return "text";
	}

	@GetMapping("/deleteAllList")
	public String deleteAllList(Model model){
		model.addAttribute("text", "You delete all lists =(");
		return "text";
	}

	@GetMapping("/testName")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		if (name.equals("Tonny")){
			name = "But not you! 0_0";
		}
		model.addAttribute("name", name);
		return "greeting";
	}
}