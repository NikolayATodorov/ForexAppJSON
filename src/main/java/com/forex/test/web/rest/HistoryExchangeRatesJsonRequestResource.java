package com.forex.test.web.rest;

import com.forex.test.repository.HistoryExchangeRatesJsonRequestRepository;
import com.forex.test.service.HistoryExchangeRatesJsonRequestService;
import com.forex.test.service.dto.HistoryExchangeRatesJsonRequestDTO;
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
 * REST controller for managing {@link com.forex.test.domain.HistoryExchangeRatesJsonRequest}.
 */
@RestController
@RequestMapping("/api/history-exchange-rates-json-requests")
public class HistoryExchangeRatesJsonRequestResource {

    private final Logger log = LoggerFactory.getLogger(HistoryExchangeRatesJsonRequestResource.class);

    private static final String ENTITY_NAME = "historyExchangeRatesJsonRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoryExchangeRatesJsonRequestService historyExchangeRatesJsonRequestService;

    private final HistoryExchangeRatesJsonRequestRepository historyExchangeRatesJsonRequestRepository;

    public HistoryExchangeRatesJsonRequestResource(
        HistoryExchangeRatesJsonRequestService historyExchangeRatesJsonRequestService,
        HistoryExchangeRatesJsonRequestRepository historyExchangeRatesJsonRequestRepository
    ) {
        this.historyExchangeRatesJsonRequestService = historyExchangeRatesJsonRequestService;
        this.historyExchangeRatesJsonRequestRepository = historyExchangeRatesJsonRequestRepository;
    }

    /**
     * {@code POST  /history-exchange-rates-json-requests} : Create a new historyExchangeRatesJsonRequest.
     *
     * @param historyExchangeRatesJsonRequestDTO the historyExchangeRatesJsonRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historyExchangeRatesJsonRequestDTO, or with status {@code 400 (Bad Request)} if the historyExchangeRatesJsonRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HistoryExchangeRatesJsonRequestDTO> createHistoryExchangeRatesJsonRequest(
        @Valid @RequestBody HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to save HistoryExchangeRatesJsonRequest : {}", historyExchangeRatesJsonRequestDTO);
        if (historyExchangeRatesJsonRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new historyExchangeRatesJsonRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestService.save(historyExchangeRatesJsonRequestDTO);
        return ResponseEntity.created(new URI("/api/history-exchange-rates-json-requests/" + historyExchangeRatesJsonRequestDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    historyExchangeRatesJsonRequestDTO.getId().toString()
                )
            )
            .body(historyExchangeRatesJsonRequestDTO);
    }

    /**
     * {@code PUT  /history-exchange-rates-json-requests/:id} : Updates an existing historyExchangeRatesJsonRequest.
     *
     * @param id the id of the historyExchangeRatesJsonRequestDTO to save.
     * @param historyExchangeRatesJsonRequestDTO the historyExchangeRatesJsonRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historyExchangeRatesJsonRequestDTO,
     * or with status {@code 400 (Bad Request)} if the historyExchangeRatesJsonRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historyExchangeRatesJsonRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HistoryExchangeRatesJsonRequestDTO> updateHistoryExchangeRatesJsonRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HistoryExchangeRatesJsonRequest : {}, {}", id, historyExchangeRatesJsonRequestDTO);
        if (historyExchangeRatesJsonRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historyExchangeRatesJsonRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historyExchangeRatesJsonRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestService.update(historyExchangeRatesJsonRequestDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    historyExchangeRatesJsonRequestDTO.getId().toString()
                )
            )
            .body(historyExchangeRatesJsonRequestDTO);
    }

    /**
     * {@code PATCH  /history-exchange-rates-json-requests/:id} : Partial updates given fields of an existing historyExchangeRatesJsonRequest, field will ignore if it is null
     *
     * @param id the id of the historyExchangeRatesJsonRequestDTO to save.
     * @param historyExchangeRatesJsonRequestDTO the historyExchangeRatesJsonRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historyExchangeRatesJsonRequestDTO,
     * or with status {@code 400 (Bad Request)} if the historyExchangeRatesJsonRequestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the historyExchangeRatesJsonRequestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the historyExchangeRatesJsonRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HistoryExchangeRatesJsonRequestDTO> partialUpdateHistoryExchangeRatesJsonRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update HistoryExchangeRatesJsonRequest partially : {}, {}",
            id,
            historyExchangeRatesJsonRequestDTO
        );
        if (historyExchangeRatesJsonRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historyExchangeRatesJsonRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historyExchangeRatesJsonRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HistoryExchangeRatesJsonRequestDTO> result = historyExchangeRatesJsonRequestService.partialUpdate(
            historyExchangeRatesJsonRequestDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, historyExchangeRatesJsonRequestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /history-exchange-rates-json-requests} : get all the historyExchangeRatesJsonRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historyExchangeRatesJsonRequests in body.
     */
    @GetMapping("")
    public List<HistoryExchangeRatesJsonRequestDTO> getAllHistoryExchangeRatesJsonRequests() {
        log.debug("REST request to get all HistoryExchangeRatesJsonRequests");
        return historyExchangeRatesJsonRequestService.findAll();
    }

    /**
     * {@code GET  /history-exchange-rates-json-requests/:id} : get the "id" historyExchangeRatesJsonRequest.
     *
     * @param id the id of the historyExchangeRatesJsonRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historyExchangeRatesJsonRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HistoryExchangeRatesJsonRequestDTO> getHistoryExchangeRatesJsonRequest(@PathVariable("id") Long id) {
        log.debug("REST request to get HistoryExchangeRatesJsonRequest : {}", id);
        Optional<HistoryExchangeRatesJsonRequestDTO> historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(historyExchangeRatesJsonRequestDTO);
    }

    /**
     * {@code DELETE  /history-exchange-rates-json-requests/:id} : delete the "id" historyExchangeRatesJsonRequest.
     *
     * @param id the id of the historyExchangeRatesJsonRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistoryExchangeRatesJsonRequest(@PathVariable("id") Long id) {
        log.debug("REST request to delete HistoryExchangeRatesJsonRequest : {}", id);
        historyExchangeRatesJsonRequestService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
