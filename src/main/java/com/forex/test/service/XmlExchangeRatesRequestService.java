package com.forex.test.service;

import com.forex.test.service.dto.XmlExchangeRatesRequestDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.forex.test.domain.XmlExchangeRatesRequest}.
 */
public interface XmlExchangeRatesRequestService {
    /**
     * Save a xmlExchangeRatesRequest.
     *
     * @param xmlExchangeRatesRequestDTO the entity to save.
     * @return the persisted entity.
     */
    XmlExchangeRatesRequestDTO save(XmlExchangeRatesRequestDTO xmlExchangeRatesRequestDTO);

    /**
     * Updates a xmlExchangeRatesRequest.
     *
     * @param xmlExchangeRatesRequestDTO the entity to update.
     * @return the persisted entity.
     */
    XmlExchangeRatesRequestDTO update(XmlExchangeRatesRequestDTO xmlExchangeRatesRequestDTO);

    /**
     * Partially updates a xmlExchangeRatesRequest.
     *
     * @param xmlExchangeRatesRequestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<XmlExchangeRatesRequestDTO> partialUpdate(XmlExchangeRatesRequestDTO xmlExchangeRatesRequestDTO);

    /**
     * Get all the xmlExchangeRatesRequests.
     *
     * @return the list of entities.
     */
    List<XmlExchangeRatesRequestDTO> findAll();

    /**
     * Get the "id" xmlExchangeRatesRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<XmlExchangeRatesRequestDTO> findOne(Long id);

    /**
     * Get the "requestId" xmlExchangeRatesRequest.
     *
     * @param requestId the requestId of the entity.
     * @return the entity.
     */
    Optional<XmlExchangeRatesRequestDTO> findByRequestId(String requestId);

    /**
     * Delete the "id" xmlExchangeRatesRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
