package com.simple.todo.restControllers;

import com.simple.todo.entity.Database;
import com.simple.todo.entity.Deal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * RestController for deals from database
 */
@RestController
@RequestMapping("deal")
public class DealsController {
	private List<Map<String, String>> bdEmu = Database.bdEmu;

	@GetMapping("/")
	public Deal getDeal(long id){return null;}

	@PostMapping("/addDeal")
	public boolean addDeal(@RequestParam(name = "name") String name){return false;}

	@PostMapping("/deleteDeal")
	public boolean deleteDeal(@RequestParam(name = "id") long id){return  false;}

	@PostMapping("/deleteAllDeal")
	public boolean deleteAllDeal(){return  false;}

	// TODO: не хватает метода посмотреть все дела в списке с фильрацией сортировкой и пагинацией
}
