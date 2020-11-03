package com.simple.todo.restControllers;

import com.simple.todo.entity.Database;
import com.simple.todo.entity.Deal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * RestController for deals from database
 */
// TODO: java-doc лучше писать на русском языке, т.к. команда разработчиков русскоязычная
@RestController
@RequestMapping("deal")
public class DealsController {

	private List<Map<String, String>> bdEmu = Database.bdEmu;
    // TODO: вместо этого нам нужны сервисы и интерфейсы к ним. в рестах сервисы подключаются через интерфейсы
    //  и аннотоцию @Autowired
    //  интерфейсы нужны к сервисам, чтоб мы могли быстро подменить реализацию

    // TODO: если метод rest что-то возвращает, то лучше это делать не через Map<String, String>, т.к. объект может быть
    //  сложнее чем набор строковых ключ-значение, для возврата обчно используют ДТО. и вообще rest-ы ничего не должны
    //  знать о сущностях модели, иначе мы жестко связаны - как следствие цена исправления велика

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
