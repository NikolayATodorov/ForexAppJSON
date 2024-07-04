package com.forex.test.service;

import com.forex.test.service.dto.HistoryExchangeRatesJsonRequestDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.forex.test.domain.HistoryExchangeRatesJsonRequest}.
 */
public interface HistoryExchangeRatesJsonRequestService {
    /**
     * Save a historyExchangeRatesJsonRequest.
     *
     * @param historyExchangeRatesJsonRequestDTO the entity to save.
     * @return the persisted entity.
     */
    HistoryExchangeRatesJsonRequestDTO save(HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO);

    /**
     * Updates a historyExchangeRatesJsonRequest.
     *
     * @param historyExchangeRatesJsonRequestDTO the entity to update.
     * @return the persisted entity.
     */
    HistoryExchangeRatesJsonRequestDTO update(HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO);

    /**
     * Partially updates a historyExchangeRatesJsonRequest.
     *
     * @param historyExchangeRatesJsonRequestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HistoryExchangeRatesJsonRequestDTO> partialUpdate(HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO);

    /**
     * Get all the historyExchangeRatesJsonRequests.
     *
     * @return the list of entities.
     */
    List<HistoryExchangeRatesJsonRequestDTO> findAll();

    /**
     * Get the "id" historyExchangeRatesJsonRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HistoryExchangeRatesJsonRequestDTO> findOne(Long id);

    /**
     * Get the "requestId" historyExchangeRatesJsonRequest.
     *
     * @param requestId the id of the entity.
     * @return the entity.
     */
    Optional<HistoryExchangeRatesJsonRequestDTO> findByRequestId(String requestId);

    /**
     * Delete the "id" historyExchangeRatesJsonRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
