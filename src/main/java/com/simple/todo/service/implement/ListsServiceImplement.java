package com.simple.todo.service.implement;

import com.simple.todo.dto.ListsResponse;
import com.simple.todo.entity.Deal;
import com.simple.todo.entity.ListDeal;
import com.simple.todo.repository.DealRepository;
import com.simple.todo.repository.ListDealRepository;
import com.simple.todo.service.ListsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ListsServiceImplement implements ListsService {

	private final DealRepository dealRepository;

	private final ListDealRepository listRepository;

	@Autowired
	public ListsServiceImplement(DealRepository dealRepository, ListDealRepository listRepository) {
		this.dealRepository = dealRepository;
		this.listRepository = listRepository;
	}

//	public ListsServiceImplement() {}

	@Override
	@Transactional
	public ListDeal addList(ListDeal list) {
		return listRepository.saveAndFlush(list);
	}

	@Override
	public ListsResponse getAll(Pageable pageable) {
		Page<ListDeal> searchResult = listRepository.findAll(pageable);
		return getGetListResponse(searchResult);
	}

	@Override
	public ListsResponse getAllWithSpec(Specification<ListDeal> spec, Pageable pageable) {
		Page<ListDeal> searchResult = listRepository.findAll(spec, pageable);
		return getGetListResponse(searchResult);
	}

	private ListsResponse getGetListResponse(Page<ListDeal> searchResult) {
		List<ListDeal> listOfLists = searchResult.toList();
		int done = 0;
		int notDone = 0;
		for (ListDeal list : listOfLists) {
			if (list.isDone()) {
				done++;
			} else {
				notDone++;
			}
		}

		return new ListsResponse(done, notDone, searchResult);
	}

	@Override
	@Transactional
	public Optional<ListDeal> changeList(UUID id, String name) {
		Optional<ListDeal> searchResult = listRepository.findById(id);
		if (searchResult.isPresent()) {
			ListDeal foundedList = searchResult.get();
			foundedList.setName(name);
			foundedList.setDateEdition(Timestamp.valueOf(LocalDateTime.now()));

			return Optional.of(listRepository.saveAndFlush(foundedList));
		}

		return Optional.empty();
	}

	@Override
	@Transactional
	public boolean deleteList(UUID id) {
		Optional<ListDeal> searchResult = listRepository.findById(id);
		if (searchResult.isPresent()) {
			dealRepository.deleteByList_id(id);
			listRepository.deleteById(id);
			return true;
		}

		return false;
	}

	/**
	 * Проверяем стоит ли выставить списку статус сделан, например когда изменяем одно из task в списке или удаляем не сделанное task
	 */
	@Override
	public void checkIfListShouldBeDone(ListDeal list, UUID excludeCheckDealId) {
		if (!list.isDone()) {
			boolean checkResult = true;
			List<Deal> listOfDeals = dealRepository.findAllByList_id(list.getId());

			for (Deal deal : listOfDeals) {
				if (!deal.isDone() && deal.getId() != excludeCheckDealId) {
					checkResult = false;
					break;
				}
			}

			if (checkResult) {
				list.setDone(true);
			}
		}
	}

	@Override
	@Transactional
	public void saveList(ListDeal list) {
		listRepository.save(list);
	}
}
