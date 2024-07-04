package com.forex.test.web.rest;

import com.forex.test.repository.ExtServiceRequestRepository;
import com.forex.test.service.ExtServiceRequestService;
import com.forex.test.service.dto.ExtServiceRequestDTO;
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
 * REST controller for managing {@link com.forex.test.domain.ExtServiceRequest}.
 */
@RestController
@RequestMapping("/api/ext-service-requests")
public class ExtServiceRequestResource {

    private final Logger log = LoggerFactory.getLogger(ExtServiceRequestResource.class);

    private static final String ENTITY_NAME = "extServiceRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExtServiceRequestService extServiceRequestService;

    private final ExtServiceRequestRepository extServiceRequestRepository;

    public ExtServiceRequestResource(
        ExtServiceRequestService extServiceRequestService,
        ExtServiceRequestRepository extServiceRequestRepository
    ) {
        this.extServiceRequestService = extServiceRequestService;
        this.extServiceRequestRepository = extServiceRequestRepository;
    }

    /**
     * {@code POST  /ext-service-requests} : Create a new extServiceRequest.
     *
     * @param extServiceRequestDTO the extServiceRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new extServiceRequestDTO, or with status {@code 400 (Bad Request)} if the extServiceRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    //    @PostMapping("")
    public ResponseEntity<ExtServiceRequestDTO> createExtServiceRequest(@Valid @RequestBody ExtServiceRequestDTO extServiceRequestDTO)
        throws URISyntaxException {
        log.debug("REST request to save ExtServiceRequest : {}", extServiceRequestDTO);
        if (extServiceRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new extServiceRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        extServiceRequestDTO = extServiceRequestService.save(extServiceRequestDTO);
        return ResponseEntity.created(new URI("/api/ext-service-requests/" + extServiceRequestDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, extServiceRequestDTO.getId().toString()))
            .body(extServiceRequestDTO);
    }

    /**
     * {@code PUT  /ext-service-requests/:id} : Updates an existing extServiceRequest.
     *
     * @param id the id of the extServiceRequestDTO to save.
     * @param extServiceRequestDTO the extServiceRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated extServiceRequestDTO,
     * or with status {@code 400 (Bad Request)} if the extServiceRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the extServiceRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    //    @PutMapping("/{id}")
    public ResponseEntity<ExtServiceRequestDTO> updateExtServiceRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ExtServiceRequestDTO extServiceRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ExtServiceRequest : {}, {}", id, extServiceRequestDTO);
        if (extServiceRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, extServiceRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!extServiceRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        extServiceRequestDTO = extServiceRequestService.update(extServiceRequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, extServiceRequestDTO.getId().toString()))
            .body(extServiceRequestDTO);
    }

    /**
     * {@code PATCH  /ext-service-requests/:id} : Partial updates given fields of an existing extServiceRequest, field will ignore if it is null
     *
     * @param id the id of the extServiceRequestDTO to save.
     * @param extServiceRequestDTO the extServiceRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated extServiceRequestDTO,
     * or with status {@code 400 (Bad Request)} if the extServiceRequestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the extServiceRequestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the extServiceRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    //    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExtServiceRequestDTO> partialUpdateExtServiceRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ExtServiceRequestDTO extServiceRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExtServiceRequest partially : {}, {}", id, extServiceRequestDTO);
        if (extServiceRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, extServiceRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!extServiceRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExtServiceRequestDTO> result = extServiceRequestService.partialUpdate(extServiceRequestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, extServiceRequestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ext-service-requests} : get all the extServiceRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of extServiceRequests in body.
     */
    @GetMapping("")
    public List<ExtServiceRequestDTO> getAllExtServiceRequests() {
        log.debug("REST request to get all ExtServiceRequests");
        return extServiceRequestService.findAll();
    }

    /**
     * {@code GET  /ext-service-requests/:id} : get the "id" extServiceRequest.
     *
     * @param id the id of the extServiceRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the extServiceRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExtServiceRequestDTO> getExtServiceRequest(@PathVariable("id") Long id) {
        log.debug("REST request to get ExtServiceRequest : {}", id);
        Optional<ExtServiceRequestDTO> extServiceRequestDTO = extServiceRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(extServiceRequestDTO);
    }

    /**
     * {@code DELETE  /ext-service-requests/:id} : delete the "id" extServiceRequest.
     *
     * @param id the id of the extServiceRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    //    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExtServiceRequest(@PathVariable("id") Long id) {
        log.debug("REST request to delete ExtServiceRequest : {}", id);
        extServiceRequestService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
