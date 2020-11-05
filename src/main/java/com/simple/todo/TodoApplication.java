package com.simple.todo;

import liquibase.pro.packaged.S;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

@SpringBootApplication
public class TodoApplication {

	private static final Logger log = LoggerFactory.getLogger(TodoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);

//		System.out.println("Press Enter to exit");
//		Scanner scanner = new Scanner(System.in);
//		scanner.next();
	}

//For testing repository
//	@Bean
//	public CommandLineRunner demo(ListDealRepository repository) {
//		return (args) -> {
//			// save a few customers
//			repository.save(new ListDeal("Jack"));
//			repository.save(new ListDeal("Chloe"));
//			repository.save(new ListDeal("Kim"));
//			repository.save(new ListDeal("David"));
//			repository.save(new ListDeal("Michelle"));
//
//			// fetch all customers
//			log.info("Customers found with findAll():");
//			log.info("-------------------------------");
//			for (ListDeal listDeal : repository.findAll()) {
//				log.info(listDeal.toString());
//			}
//			log.info("");
//
//			// fetch an individual customer by ID
//			ListDeal listDeal = repository.findById(1L);
//			log.info("Customer found with findById(1L):");
//			log.info("--------------------------------");
//			log.info(listDeal.toString());
//			log.info("");

			//NOT WORKING with ListDeal
			// fetch customers by last name
//			log.info("Customer found with findByName('Bauer'):");
//			log.info("--------------------------------------------");
//			repository.findByName("Bauer").forEach(bauer -> {
//				log.info(bauer.toString());
//			});
			// for (Customer bauer : repository.findByLastName("Bauer")) {
			//  log.info(bauer.toString());
			// }
//			log.info("");
//		};
//	}

}
