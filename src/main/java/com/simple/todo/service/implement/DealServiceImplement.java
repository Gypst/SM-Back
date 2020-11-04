package com.simple.todo.service.implement;

import com.simple.todo.dto.DealResponse;
import com.simple.todo.entity.Deal;
import com.simple.todo.entity.ListDeal;
import com.simple.todo.repository.DealRepository;
import com.simple.todo.repository.ListDealRepository;
import com.simple.todo.service.DealService;
import com.simple.todo.service.ListsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Реализация интерфейса DealService
 */
@Service
public class DealServiceImplement implements DealService {

	private final DealRepository dealRepository;

	private final ListDealRepository listDealRepository;

	private final ListsService listsService;


	/**
	 * Конструктор сервиса
	 */
	@Autowired
	public DealServiceImplement(DealRepository dealRepository, ListDealRepository listDealRepository, ListsService listsService) {
		this.dealRepository = dealRepository;
		this.listDealRepository = listDealRepository;
		this.listsService = listsService;
	}

	@Override
	@Transactional
	public Optional<Deal> addDeal(Deal deal) {
		if (!checkIfListExistedAndUpdate(deal, false)) return Optional.empty();

		return Optional.of(dealRepository.saveAndFlush(deal));
	}

	@Override
	public Optional<ListDeal> findListById(UUID id) {
		return listDealRepository.findById(id);
	}

	@Override
	public DealResponse getAll(Pageable pageable, UUID id) {
		Page<Deal> searchResult = dealRepository.findAllByListId(pageable, id);
		return getDealResponse(searchResult);
	}

	@Override
	public DealResponse getAllWithSpec(Specification<Deal> spec, Pageable pageable) {
		Page<Deal> searchResult = dealRepository.findAll(spec, pageable);
		return getDealResponse(searchResult);
	}

	private DealResponse getDealResponse(Page<Deal> searchResult) {
		java.util.List<Deal> listOfDeals = searchResult.toList();
		int done = 0;
		int notDone = 0;
		for (Deal deal : listOfDeals) {
			if (deal.isDone()) {
				done++;
			} else {
				notDone++;
			}
		}

		return new DealResponse(done, notDone, searchResult);
	}

	@Override
	@Transactional
	public Optional<Deal> changeDeal(UUID id, String name, Optional<String> description, Optional<Integer> priority, Optional<Boolean> done) {
		Optional<Deal> searchResult = dealRepository.findById(id);
		if (searchResult.isPresent()) {
			Deal foundDeal = searchResult.get();
			foundDeal.setName(name);
			if (description.isPresent()) {
				foundDeal.setDescription(description.get());
			}
			if (priority.isPresent()) {
				foundDeal.setPriority(priority.get());
			}
			if (done.isPresent()) {
				boolean newDoneStatus = done.get();
				foundDeal.setDone(newDoneStatus);

				if (!checkIfListExistedAndUpdate(foundDeal, newDoneStatus)) return Optional.empty();
			} else {
				if (!checkIfListExistedAndUpdate(foundDeal)) return Optional.empty();
			}

			foundDeal.setDateEdition(Timestamp.valueOf(LocalDateTime.now()));

			return Optional.of(dealRepository.saveAndFlush(foundDeal));
		}

		return Optional.empty();
	}

	@Override
	@Transactional
	public boolean deleteDeal(UUID id) {
		Optional<Deal> searchResult = dealRepository.findById(id);
		if (searchResult.isPresent()) {
			Deal foundDeal = searchResult.get();
			if (!foundDeal.isDone()) {
				if (!checkIfListExistedAndUpdate(foundDeal, true)) return false;
			} else {
				if (!checkIfListExistedAndUpdate(foundDeal)) return false;
			}

			dealRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public Optional<Deal> setDone(UUID id) {
		Optional<Deal> searchResult = dealRepository.findById(id);
		if (searchResult.isPresent()) {
			Deal foundDeal = searchResult.get();
			if (!foundDeal.isDone()) {
				foundDeal.setDone(true);

				if (!checkIfListExistedAndUpdate(foundDeal, true)) return Optional.empty();

				foundDeal.setDateEdition(Timestamp.valueOf(LocalDateTime.now()));
				return Optional.of(dealRepository.saveAndFlush(foundDeal));
			}

			return Optional.of(foundDeal);
		}

		return Optional.empty();
	}

	@Override
	@Transactional
	public Optional<Deal> setUnDone(UUID id){
		Optional<Deal> searchResult = dealRepository.findById(id);
		if (searchResult.isPresent()) {
			Deal foundDeal = searchResult.get();
			if (foundDeal.isDone()) {
				foundDeal.setDone(false);
				if (!checkIfListExistedAndUpdate(foundDeal, false)) return Optional.empty();
				foundDeal.setDateEdition(Timestamp.valueOf(LocalDateTime.now()));
				return Optional.of(dealRepository.saveAndFlush(foundDeal));
			}
			return Optional.of(foundDeal);
		}
		return Optional.empty();
	}

	/**
	 * Проверяет список и, если он существует, обновляет его готовность (done)
	 */
	private boolean checkIfListExistedAndUpdate(Deal deal, boolean newDealStatusToUpdate) {
		Optional<ListDeal> searchList = findListById(deal.getListId());
		if (searchList.isPresent()) {
			ListDeal found = searchList.get();

			if (newDealStatusToUpdate) {
				listsService.checkIfListShouldBeDone(found, deal.getId());
			} else {
				found.setDone(false);
			}

			found.setDateEdition(Timestamp.valueOf(LocalDateTime.now()));
			listsService.saveList(found);

			return true;
		}
		return false;
	}

	/**
	 * Проверяет список и, если он существует, обновляет его готовность (done)
	 */
	private boolean checkIfListExistedAndUpdate(Deal deal) {
		Optional<ListDeal> searchList = findListById(deal.getListId());
		if (searchList.isPresent()) {
			ListDeal findedList = searchList.get();

			findedList.setDateEdition(Timestamp.valueOf(LocalDateTime.now()));
			listsService.saveList(findedList);

			return true;
		} else {
			return false;
		}
	}

}

