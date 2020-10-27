package com.simple.todo.entity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TODO: я так понимаю это просто тестовый класс
 */
@Controller
@RequestMapping("/calculator")
public class Calculator {

	@RequestMapping("/")
	public  String calculate(@RequestParam("a") int a,
	                       @RequestParam("b") int b,
	                       @RequestParam("action") String action,
	                       Model model){

		switch (action){
			case "mult":
				model.addAttribute("result", a*b);
				break;
			case "add":
				model.addAttribute("result", a+b);
				break;
			case "sub":
				model.addAttribute("result", a-b);
				break;
			case "div":
				model.addAttribute("result", a/b);
				break;
		}
		return "text";
	}
}
