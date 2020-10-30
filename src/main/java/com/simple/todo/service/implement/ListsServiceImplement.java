package com.simple.todo.service.implement;

import com.simple.todo.repository.DealRepository;
import com.simple.todo.repository.ListDealRepository;
import com.simple.todo.service.ListsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListsServiceImplement implements ListsService {

	private final DealRepository dealRepository;

	private final ListDealRepository listRepository;

	@Autowired
	public ListServiceImpl(DealRepository dealRepository, ListDealRepository listRepository) {
		this.dealRepository = dealRepository;
		this.listRepository = listRepository;
	}

}
