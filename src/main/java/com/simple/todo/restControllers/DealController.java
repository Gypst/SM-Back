package com.simple.todo.restControllers;

import com.simple.todo.entity.Deal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DealController {

	@GetMapping("/deal")
	public Deal getDeal(long id){return null;}

	@GetMapping("/addDeal")
	public boolean addDeal(@RequestParam(name = "name") String name){return false;}

	@GetMapping("/deleteDeal")
	public boolean deleteDeal(@RequestParam(name = "id") long id){return  false;}

	@GetMapping("/deleteAllDeal")
	public boolean deleteAllDeal(){return  false;}

}
