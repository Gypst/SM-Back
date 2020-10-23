package com.simple.todo.restControllers;

import com.simple.todo.entity.Deal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/deal")
public class DealsController {

	@GetMapping("/")
	public Deal getDeal(long id){return null;}

	@PostMapping("/addDeal")
	public boolean addDeal(@RequestParam(name = "name") String name){return false;}

	@PostMapping("/deleteDeal")
	public boolean deleteDeal(@RequestParam(name = "id") long id){return  false;}

	@PostMapping("/deleteAllDeal")
	public boolean deleteAllDeal(){return  false;}

}