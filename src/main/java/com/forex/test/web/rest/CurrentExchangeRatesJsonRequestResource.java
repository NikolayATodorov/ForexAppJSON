package com.forex.test.web.rest;

import com.forex.test.repository.CurrentExchangeRatesJsonRequestRepository;
import com.forex.test.service.CurrentExchangeRatesJsonRequestService;
import com.forex.test.service.dto.CurrentExchangeRatesJsonRequestDTO;
import com.forex.test.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.forex.test.domain.CurrentExchangeRatesJsonRequest}.
 */
@RestController
@RequestMapping("/api/current-exchange-rates-json-requests")
public class CurrentExchangeRatesJsonRequestResource {

    private final Logger log = LoggerFactory.getLogger(CurrentExchangeRatesJsonRequestResource.class);

    private static final String ENTITY_NAME = "currentExchangeRatesJsonRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurrentExchangeRatesJsonRequestService currentExchangeRatesJsonRequestService;

    private final CurrentExchangeRatesJsonRequestRepository currentExchangeRatesJsonRequestRepository;

    public CurrentExchangeRatesJsonRequestResource(
        CurrentExchangeRatesJsonRequestService currentExchangeRatesJsonRequestService,
        CurrentExchangeRatesJsonRequestRepository currentExchangeRatesJsonRequestRepository
    ) {
        this.currentExchangeRatesJsonRequestService = currentExchangeRatesJsonRequestService;
        this.currentExchangeRatesJsonRequestRepository = currentExchangeRatesJsonRequestRepository;
    }

    /**
     * {@code POST  /current-exchange-rates-json-requests} : Create a new currentExchangeRatesJsonRequest.
     *
     * @param currentExchangeRatesJsonRequestDTO the currentExchangeRatesJsonRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new currentExchangeRatesJsonRequestDTO, or with status {@code 400 (Bad Request)} if the currentExchangeRatesJsonRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    //    @PostMapping("")
    public ResponseEntity<CurrentExchangeRatesJsonRequestDTO> createCurrentExchangeRatesJsonRequest(
        @Valid @RequestBody CurrentExchangeRatesJsonRequestDTO currentExchangeRatesJsonRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CurrentExchangeRatesJsonRequest : {}", currentExchangeRatesJsonRequestDTO);
        if (currentExchangeRatesJsonRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new currentExchangeRatesJsonRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        currentExchangeRatesJsonRequestDTO = currentExchangeRatesJsonRequestService.save(currentExchangeRatesJsonRequestDTO);
        return ResponseEntity.created(new URI("/api/current-exchange-rates-json-requests/" + currentExchangeRatesJsonRequestDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    currentExchangeRatesJsonRequestDTO.getId().toString()
                )
            )
            .body(currentExchangeRatesJsonRequestDTO);
    }

    /**
     * {@code PUT  /current-exchange-rates-json-requests/:id} : Updates an existing currentExchangeRatesJsonRequest.
     *
     * @param id the id of the currentExchangeRatesJsonRequestDTO to save.
     * @param currentExchangeRatesJsonRequestDTO the currentExchangeRatesJsonRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currentExchangeRatesJsonRequestDTO,
     * or with status {@code 400 (Bad Request)} if the currentExchangeRatesJsonRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the currentExchangeRatesJsonRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    //    @PutMapping("/{id}")
    public ResponseEntity<CurrentExchangeRatesJsonRequestDTO> updateCurrentExchangeRatesJsonRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CurrentExchangeRatesJsonRequestDTO currentExchangeRatesJsonRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CurrentExchangeRatesJsonRequest : {}, {}", id, currentExchangeRatesJsonRequestDTO);
        if (currentExchangeRatesJsonRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, currentExchangeRatesJsonRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!currentExchangeRatesJsonRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        currentExchangeRatesJsonRequestDTO = currentExchangeRatesJsonRequestService.update(currentExchangeRatesJsonRequestDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    currentExchangeRatesJsonRequestDTO.getId().toString()
                )
            )
            .body(currentExchangeRatesJsonRequestDTO);
    }

    /**
     * {@code PATCH  /current-exchange-rates-json-requests/:id} : Partial updates given fields of an existing currentExchangeRatesJsonRequest, field will ignore if it is null
     *
     * @param id the id of the currentExchangeRatesJsonRequestDTO to save.
     * @param currentExchangeRatesJsonRequestDTO the currentExchangeRatesJsonRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currentExchangeRatesJsonRequestDTO,
     * or with status {@code 400 (Bad Request)} if the currentExchangeRatesJsonRequestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the currentExchangeRatesJsonRequestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the currentExchangeRatesJsonRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    //    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CurrentExchangeRatesJsonRequestDTO> partialUpdateCurrentExchangeRatesJsonRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CurrentExchangeRatesJsonRequestDTO currentExchangeRatesJsonRequestDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update CurrentExchangeRatesJsonRequest partially : {}, {}",
            id,
            currentExchangeRatesJsonRequestDTO
        );
        if (currentExchangeRatesJsonRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, currentExchangeRatesJsonRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!currentExchangeRatesJsonRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CurrentExchangeRatesJsonRequestDTO> result = currentExchangeRatesJsonRequestService.partialUpdate(
            currentExchangeRatesJsonRequestDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, currentExchangeRatesJsonRequestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /current-exchange-rates-json-requests} : get all the currentExchangeRatesJsonRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currentExchangeRatesJsonRequests in body.
     */
    @GetMapping("")
    public List<CurrentExchangeRatesJsonRequestDTO> getAllCurrentExchangeRatesJsonRequests() {
        log.debug("REST request to get all CurrentExchangeRatesJsonRequests");
        return currentExchangeRatesJsonRequestService.findAll();
    }

    /**
     * {@code GET  /current-exchange-rates-json-requests/:id} : get the "id" currentExchangeRatesJsonRequest.
     *
     * @param id the id of the currentExchangeRatesJsonRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the currentExchangeRatesJsonRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CurrentExchangeRatesJsonRequestDTO> getCurrentExchangeRatesJsonRequest(@PathVariable("id") Long id) {
        log.debug("REST request to get CurrentExchangeRatesJsonRequest : {}", id);
        Optional<CurrentExchangeRatesJsonRequestDTO> currentExchangeRatesJsonRequestDTO = currentExchangeRatesJsonRequestService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(currentExchangeRatesJsonRequestDTO);
    }

    /**
     * {@code DELETE  /current-exchange-rates-json-requests/:id} : delete the "id" currentExchangeRatesJsonRequest.
     *
     * @param id the id of the currentExchangeRatesJsonRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    //    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrentExchangeRatesJsonRequest(@PathVariable("id") Long id) {
        log.debug("REST request to delete CurrentExchangeRatesJsonRequest : {}", id);
        currentExchangeRatesJsonRequestService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
