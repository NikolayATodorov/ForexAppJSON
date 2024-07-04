package com.forex.test.web.rest;

import static com.forex.test.domain.CurrentExchangeRatesJsonRequestAsserts.*;
import static com.forex.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forex.test.IntegrationTest;
import com.forex.test.domain.CurrentExchangeRatesJsonRequest;
import com.forex.test.repository.CurrentExchangeRatesJsonRequestRepository;
import com.forex.test.service.dto.CurrentExchangeRatesJsonRequestDTO;
import com.forex.test.service.mapper.CurrentExchangeRatesJsonRequestMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CurrentExchangeRatesJsonRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CurrentExchangeRatesJsonRequestResourceIT {

    private static final String DEFAULT_REQUEST_ID = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY = "AAA";
    private static final String UPDATED_CURRENCY = "BBB";

    private static final String ENTITY_API_URL = "/api/current-exchange-rates-json-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CurrentExchangeRatesJsonRequestRepository currentExchangeRatesJsonRequestRepository;

    @Autowired
    private CurrentExchangeRatesJsonRequestMapper currentExchangeRatesJsonRequestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCurrentExchangeRatesJsonRequestMockMvc;

    private CurrentExchangeRatesJsonRequest currentExchangeRatesJsonRequest;

    private CurrentExchangeRatesJsonRequest insertedCurrentExchangeRatesJsonRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrentExchangeRatesJsonRequest createEntity(EntityManager em) {
        CurrentExchangeRatesJsonRequest currentExchangeRatesJsonRequest = new CurrentExchangeRatesJsonRequest()
            .requestId(DEFAULT_REQUEST_ID)
            .timestamp(DEFAULT_TIMESTAMP)
            .client(DEFAULT_CLIENT)
            .currency(DEFAULT_CURRENCY);
        return currentExchangeRatesJsonRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrentExchangeRatesJsonRequest createUpdatedEntity(EntityManager em) {
        CurrentExchangeRatesJsonRequest currentExchangeRatesJsonRequest = new CurrentExchangeRatesJsonRequest()
            .requestId(UPDATED_REQUEST_ID)
            .timestamp(UPDATED_TIMESTAMP)
            .client(UPDATED_CLIENT)
            .currency(UPDATED_CURRENCY);
        return currentExchangeRatesJsonRequest;
    }

    @BeforeEach
    public void initTest() {
        currentExchangeRatesJsonRequest = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCurrentExchangeRatesJsonRequest != null) {
            currentExchangeRatesJsonRequestRepository.delete(insertedCurrentExchangeRatesJsonRequest);
            insertedCurrentExchangeRatesJsonRequest = null;
        }
    }

    @Disabled
    @Test
    @Transactional
    void createCurrentExchangeRatesJsonRequest() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CurrentExchangeRatesJsonRequest
        CurrentExchangeRatesJsonRequestDTO currentExchangeRatesJsonRequestDTO = currentExchangeRatesJsonRequestMapper.toDto(
            currentExchangeRatesJsonRequest
        );
        var returnedCurrentExchangeRatesJsonRequestDTO = om.readValue(
            restCurrentExchangeRatesJsonRequestMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(currentExchangeRatesJsonRequestDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CurrentExchangeRatesJsonRequestDTO.class
        );

        // Validate the CurrentExchangeRatesJsonRequest in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCurrentExchangeRatesJsonRequest = currentExchangeRatesJsonRequestMapper.toEntity(
            returnedCurrentExchangeRatesJsonRequestDTO
        );
        assertCurrentExchangeRatesJsonRequestUpdatableFieldsEquals(
            returnedCurrentExchangeRatesJsonRequest,
            getPersistedCurrentExchangeRatesJsonRequest(returnedCurrentExchangeRatesJsonRequest)
        );

        insertedCurrentExchangeRatesJsonRequest = returnedCurrentExchangeRatesJsonRequest;
    }

    @Disabled
    @Test
    @Transactional
    void createCurrentExchangeRatesJsonRequestWithExistingId() throws Exception {
        // Create the CurrentExchangeRatesJsonRequest with an existing ID
        currentExchangeRatesJsonRequest.setId(1L);
        CurrentExchangeRatesJsonRequestDTO currentExchangeRatesJsonRequestDTO = currentExchangeRatesJsonRequestMapper.toDto(
            currentExchangeRatesJsonRequest
        );

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(currentExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrentExchangeRatesJsonRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Disabled
    @Test
    @Transactional
    void checkRequestIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        currentExchangeRatesJsonRequest.setRequestId(null);

        // Create the CurrentExchangeRatesJsonRequest, which fails.
        CurrentExchangeRatesJsonRequestDTO currentExchangeRatesJsonRequestDTO = currentExchangeRatesJsonRequestMapper.toDto(
            currentExchangeRatesJsonRequest
        );

        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(currentExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Disabled
    @Test
    @Transactional
    void checkTimestampIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        currentExchangeRatesJsonRequest.setTimestamp(null);

        // Create the CurrentExchangeRatesJsonRequest, which fails.
        CurrentExchangeRatesJsonRequestDTO currentExchangeRatesJsonRequestDTO = currentExchangeRatesJsonRequestMapper.toDto(
            currentExchangeRatesJsonRequest
        );

        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(currentExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Disabled
    @Test
    @Transactional
    void checkClientIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        currentExchangeRatesJsonRequest.setClient(null);

        // Create the CurrentExchangeRatesJsonRequest, which fails.
        CurrentExchangeRatesJsonRequestDTO currentExchangeRatesJsonRequestDTO = currentExchangeRatesJsonRequestMapper.toDto(
            currentExchangeRatesJsonRequest
        );

        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(currentExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Disabled
    @Test
    @Transactional
    void checkCurrencyIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        currentExchangeRatesJsonRequest.setCurrency(null);

        // Create the CurrentExchangeRatesJsonRequest, which fails.
        CurrentExchangeRatesJsonRequestDTO currentExchangeRatesJsonRequestDTO = currentExchangeRatesJsonRequestMapper.toDto(
            currentExchangeRatesJsonRequest
        );

        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(currentExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCurrentExchangeRatesJsonRequests() throws Exception {
        // Initialize the database
        insertedCurrentExchangeRatesJsonRequest = currentExchangeRatesJsonRequestRepository.saveAndFlush(currentExchangeRatesJsonRequest);

        // Get all the currentExchangeRatesJsonRequestList
        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currentExchangeRatesJsonRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestId").value(hasItem(DEFAULT_REQUEST_ID)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)));
    }

    @Test
    @Transactional
    void getCurrentExchangeRatesJsonRequest() throws Exception {
        // Initialize the database
        insertedCurrentExchangeRatesJsonRequest = currentExchangeRatesJsonRequestRepository.saveAndFlush(currentExchangeRatesJsonRequest);

        // Get the currentExchangeRatesJsonRequest
        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, currentExchangeRatesJsonRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(currentExchangeRatesJsonRequest.getId().intValue()))
            .andExpect(jsonPath("$.requestId").value(DEFAULT_REQUEST_ID))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY));
    }

    @Test
    @Transactional
    void getNonExistingCurrentExchangeRatesJsonRequest() throws Exception {
        // Get the currentExchangeRatesJsonRequest
        restCurrentExchangeRatesJsonRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Disabled
    @Test
    @Transactional
    void putExistingCurrentExchangeRatesJsonRequest() throws Exception {
        // Initialize the database
        insertedCurrentExchangeRatesJsonRequest = currentExchangeRatesJsonRequestRepository.saveAndFlush(currentExchangeRatesJsonRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the currentExchangeRatesJsonRequest
        CurrentExchangeRatesJsonRequest updatedCurrentExchangeRatesJsonRequest = currentExchangeRatesJsonRequestRepository
            .findById(currentExchangeRatesJsonRequest.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedCurrentExchangeRatesJsonRequest are not directly saved in db
        em.detach(updatedCurrentExchangeRatesJsonRequest);
        updatedCurrentExchangeRatesJsonRequest
            .requestId(UPDATED_REQUEST_ID)
            .timestamp(UPDATED_TIMESTAMP)
            .client(UPDATED_CLIENT)
            .currency(UPDATED_CURRENCY);
        CurrentExchangeRatesJsonRequestDTO currentExchangeRatesJsonRequestDTO = currentExchangeRatesJsonRequestMapper.toDto(
            updatedCurrentExchangeRatesJsonRequest
        );

        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, currentExchangeRatesJsonRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(currentExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the CurrentExchangeRatesJsonRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCurrentExchangeRatesJsonRequestToMatchAllProperties(updatedCurrentExchangeRatesJsonRequest);
    }

    @Disabled
    @Test
    @Transactional
    void putNonExistingCurrentExchangeRatesJsonRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        currentExchangeRatesJsonRequest.setId(longCount.incrementAndGet());

        // Create the CurrentExchangeRatesJsonRequest
        CurrentExchangeRatesJsonRequestDTO currentExchangeRatesJsonRequestDTO = currentExchangeRatesJsonRequestMapper.toDto(
            currentExchangeRatesJsonRequest
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, currentExchangeRatesJsonRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(currentExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrentExchangeRatesJsonRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void putWithIdMismatchCurrentExchangeRatesJsonRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        currentExchangeRatesJsonRequest.setId(longCount.incrementAndGet());

        // Create the CurrentExchangeRatesJsonRequest
        CurrentExchangeRatesJsonRequestDTO currentExchangeRatesJsonRequestDTO = currentExchangeRatesJsonRequestMapper.toDto(
            currentExchangeRatesJsonRequest
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(currentExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrentExchangeRatesJsonRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void putWithMissingIdPathParamCurrentExchangeRatesJsonRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        currentExchangeRatesJsonRequest.setId(longCount.incrementAndGet());

        // Create the CurrentExchangeRatesJsonRequest
        CurrentExchangeRatesJsonRequestDTO currentExchangeRatesJsonRequestDTO = currentExchangeRatesJsonRequestMapper.toDto(
            currentExchangeRatesJsonRequest
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(currentExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CurrentExchangeRatesJsonRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void partialUpdateCurrentExchangeRatesJsonRequestWithPatch() throws Exception {
        // Initialize the database
        insertedCurrentExchangeRatesJsonRequest = currentExchangeRatesJsonRequestRepository.saveAndFlush(currentExchangeRatesJsonRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the currentExchangeRatesJsonRequest using partial update
        CurrentExchangeRatesJsonRequest partialUpdatedCurrentExchangeRatesJsonRequest = new CurrentExchangeRatesJsonRequest();
        partialUpdatedCurrentExchangeRatesJsonRequest.setId(currentExchangeRatesJsonRequest.getId());

        partialUpdatedCurrentExchangeRatesJsonRequest.requestId(UPDATED_REQUEST_ID);

        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrentExchangeRatesJsonRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCurrentExchangeRatesJsonRequest))
            )
            .andExpect(status().isOk());

        // Validate the CurrentExchangeRatesJsonRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCurrentExchangeRatesJsonRequestUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCurrentExchangeRatesJsonRequest, currentExchangeRatesJsonRequest),
            getPersistedCurrentExchangeRatesJsonRequest(currentExchangeRatesJsonRequest)
        );
    }

    @Disabled
    @Test
    @Transactional
    void fullUpdateCurrentExchangeRatesJsonRequestWithPatch() throws Exception {
        // Initialize the database
        insertedCurrentExchangeRatesJsonRequest = currentExchangeRatesJsonRequestRepository.saveAndFlush(currentExchangeRatesJsonRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the currentExchangeRatesJsonRequest using partial update
        CurrentExchangeRatesJsonRequest partialUpdatedCurrentExchangeRatesJsonRequest = new CurrentExchangeRatesJsonRequest();
        partialUpdatedCurrentExchangeRatesJsonRequest.setId(currentExchangeRatesJsonRequest.getId());

        partialUpdatedCurrentExchangeRatesJsonRequest
            .requestId(UPDATED_REQUEST_ID)
            .timestamp(UPDATED_TIMESTAMP)
            .client(UPDATED_CLIENT)
            .currency(UPDATED_CURRENCY);

        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrentExchangeRatesJsonRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCurrentExchangeRatesJsonRequest))
            )
            .andExpect(status().isOk());

        // Validate the CurrentExchangeRatesJsonRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCurrentExchangeRatesJsonRequestUpdatableFieldsEquals(
            partialUpdatedCurrentExchangeRatesJsonRequest,
            getPersistedCurrentExchangeRatesJsonRequest(partialUpdatedCurrentExchangeRatesJsonRequest)
        );
    }

    @Disabled
    @Test
    @Transactional
    void patchNonExistingCurrentExchangeRatesJsonRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        currentExchangeRatesJsonRequest.setId(longCount.incrementAndGet());

        // Create the CurrentExchangeRatesJsonRequest
        CurrentExchangeRatesJsonRequestDTO currentExchangeRatesJsonRequestDTO = currentExchangeRatesJsonRequestMapper.toDto(
            currentExchangeRatesJsonRequest
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, currentExchangeRatesJsonRequestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(currentExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrentExchangeRatesJsonRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void patchWithIdMismatchCurrentExchangeRatesJsonRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        currentExchangeRatesJsonRequest.setId(longCount.incrementAndGet());

        // Create the CurrentExchangeRatesJsonRequest
        CurrentExchangeRatesJsonRequestDTO currentExchangeRatesJsonRequestDTO = currentExchangeRatesJsonRequestMapper.toDto(
            currentExchangeRatesJsonRequest
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(currentExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrentExchangeRatesJsonRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void patchWithMissingIdPathParamCurrentExchangeRatesJsonRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        currentExchangeRatesJsonRequest.setId(longCount.incrementAndGet());

        // Create the CurrentExchangeRatesJsonRequest
        CurrentExchangeRatesJsonRequestDTO currentExchangeRatesJsonRequestDTO = currentExchangeRatesJsonRequestMapper.toDto(
            currentExchangeRatesJsonRequest
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(currentExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CurrentExchangeRatesJsonRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void deleteCurrentExchangeRatesJsonRequest() throws Exception {
        // Initialize the database
        insertedCurrentExchangeRatesJsonRequest = currentExchangeRatesJsonRequestRepository.saveAndFlush(currentExchangeRatesJsonRequest);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the currentExchangeRatesJsonRequest
        restCurrentExchangeRatesJsonRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, currentExchangeRatesJsonRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return currentExchangeRatesJsonRequestRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected CurrentExchangeRatesJsonRequest getPersistedCurrentExchangeRatesJsonRequest(
        CurrentExchangeRatesJsonRequest currentExchangeRatesJsonRequest
    ) {
        return currentExchangeRatesJsonRequestRepository.findById(currentExchangeRatesJsonRequest.getId()).orElseThrow();
    }

    protected void assertPersistedCurrentExchangeRatesJsonRequestToMatchAllProperties(
        CurrentExchangeRatesJsonRequest expectedCurrentExchangeRatesJsonRequest
    ) {
        assertCurrentExchangeRatesJsonRequestAllPropertiesEquals(
            expectedCurrentExchangeRatesJsonRequest,
            getPersistedCurrentExchangeRatesJsonRequest(expectedCurrentExchangeRatesJsonRequest)
        );
    }

    protected void assertPersistedCurrentExchangeRatesJsonRequestToMatchUpdatableProperties(
        CurrentExchangeRatesJsonRequest expectedCurrentExchangeRatesJsonRequest
    ) {
        assertCurrentExchangeRatesJsonRequestAllUpdatablePropertiesEquals(
            expectedCurrentExchangeRatesJsonRequest,
            getPersistedCurrentExchangeRatesJsonRequest(expectedCurrentExchangeRatesJsonRequest)
        );
    }
}
