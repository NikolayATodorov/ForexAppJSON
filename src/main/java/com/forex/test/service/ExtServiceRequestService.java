package com.forex.test.service;

import com.forex.test.service.dto.ExtServiceRequestDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.forex.test.domain.ExtServiceRequest}.
 */
public interface ExtServiceRequestService {
    /**
     * Save a extServiceRequest.
     *
     * @param extServiceRequestDTO the entity to save.
     * @return the persisted entity.
     */
    ExtServiceRequestDTO save(ExtServiceRequestDTO extServiceRequestDTO);

    /**
     * Updates a extServiceRequest.
     *
     * @param extServiceRequestDTO the entity to update.
     * @return the persisted entity.
     */
    ExtServiceRequestDTO update(ExtServiceRequestDTO extServiceRequestDTO);

    /**
     * Partially updates a extServiceRequest.
     *
     * @param extServiceRequestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExtServiceRequestDTO> partialUpdate(ExtServiceRequestDTO extServiceRequestDTO);

    /**
     * Get all the extServiceRequests.
     *
     * @return the list of entities.
     */
    List<ExtServiceRequestDTO> findAll();

    /**
     * Get the "id" extServiceRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExtServiceRequestDTO> findOne(Long id);

    /**
     * Delete the "id" extServiceRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
