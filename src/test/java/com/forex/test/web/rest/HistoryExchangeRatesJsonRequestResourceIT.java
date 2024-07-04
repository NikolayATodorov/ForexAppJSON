package com.forex.test.web.rest;

import static com.forex.test.domain.HistoryExchangeRatesJsonRequestAsserts.*;
import static com.forex.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forex.test.IntegrationTest;
import com.forex.test.domain.HistoryExchangeRatesJsonRequest;
import com.forex.test.repository.HistoryExchangeRatesJsonRequestRepository;
import com.forex.test.service.dto.HistoryExchangeRatesJsonRequestDTO;
import com.forex.test.service.mapper.HistoryExchangeRatesJsonRequestMapper;
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
 * Integration tests for the {@link HistoryExchangeRatesJsonRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HistoryExchangeRatesJsonRequestResourceIT {

    private static final String DEFAULT_REQUEST_ID = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY = "AAA";
    private static final String UPDATED_CURRENCY = "BBB";

    private static final Integer DEFAULT_PERIOD = 1;
    private static final Integer UPDATED_PERIOD = 2;

    private static final String ENTITY_API_URL = "/api/history-exchange-rates-json-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HistoryExchangeRatesJsonRequestRepository historyExchangeRatesJsonRequestRepository;

    @Autowired
    private HistoryExchangeRatesJsonRequestMapper historyExchangeRatesJsonRequestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistoryExchangeRatesJsonRequestMockMvc;

    private HistoryExchangeRatesJsonRequest historyExchangeRatesJsonRequest;

    private HistoryExchangeRatesJsonRequest insertedHistoryExchangeRatesJsonRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoryExchangeRatesJsonRequest createEntity(EntityManager em) {
        HistoryExchangeRatesJsonRequest historyExchangeRatesJsonRequest = new HistoryExchangeRatesJsonRequest()
            .requestId(DEFAULT_REQUEST_ID)
            .timestamp(DEFAULT_TIMESTAMP)
            .client(DEFAULT_CLIENT)
            .currency(DEFAULT_CURRENCY)
            .period(DEFAULT_PERIOD);
        return historyExchangeRatesJsonRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoryExchangeRatesJsonRequest createUpdatedEntity(EntityManager em) {
        HistoryExchangeRatesJsonRequest historyExchangeRatesJsonRequest = new HistoryExchangeRatesJsonRequest()
            .requestId(UPDATED_REQUEST_ID)
            .timestamp(UPDATED_TIMESTAMP)
            .client(UPDATED_CLIENT)
            .currency(UPDATED_CURRENCY)
            .period(UPDATED_PERIOD);
        return historyExchangeRatesJsonRequest;
    }

    @BeforeEach
    public void initTest() {
        historyExchangeRatesJsonRequest = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedHistoryExchangeRatesJsonRequest != null) {
            historyExchangeRatesJsonRequestRepository.delete(insertedHistoryExchangeRatesJsonRequest);
            insertedHistoryExchangeRatesJsonRequest = null;
        }
    }

    @Disabled
    @Test
    @Transactional
    void createHistoryExchangeRatesJsonRequest() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the HistoryExchangeRatesJsonRequest
        HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestMapper.toDto(
            historyExchangeRatesJsonRequest
        );
        var returnedHistoryExchangeRatesJsonRequestDTO = om.readValue(
            restHistoryExchangeRatesJsonRequestMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(historyExchangeRatesJsonRequestDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HistoryExchangeRatesJsonRequestDTO.class
        );

        // Validate the HistoryExchangeRatesJsonRequest in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHistoryExchangeRatesJsonRequest = historyExchangeRatesJsonRequestMapper.toEntity(
            returnedHistoryExchangeRatesJsonRequestDTO
        );
        assertHistoryExchangeRatesJsonRequestUpdatableFieldsEquals(
            returnedHistoryExchangeRatesJsonRequest,
            getPersistedHistoryExchangeRatesJsonRequest(returnedHistoryExchangeRatesJsonRequest)
        );

        insertedHistoryExchangeRatesJsonRequest = returnedHistoryExchangeRatesJsonRequest;
    }

    @Disabled
    @Test
    @Transactional
    void createHistoryExchangeRatesJsonRequestWithExistingId() throws Exception {
        // Create the HistoryExchangeRatesJsonRequest with an existing ID
        historyExchangeRatesJsonRequest.setId(1L);
        HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestMapper.toDto(
            historyExchangeRatesJsonRequest
        );

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historyExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoryExchangeRatesJsonRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Disabled
    @Test
    @Transactional
    void checkRequestIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        historyExchangeRatesJsonRequest.setRequestId(null);

        // Create the HistoryExchangeRatesJsonRequest, which fails.
        HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestMapper.toDto(
            historyExchangeRatesJsonRequest
        );

        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historyExchangeRatesJsonRequestDTO))
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
        historyExchangeRatesJsonRequest.setTimestamp(null);

        // Create the HistoryExchangeRatesJsonRequest, which fails.
        HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestMapper.toDto(
            historyExchangeRatesJsonRequest
        );

        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historyExchangeRatesJsonRequestDTO))
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
        historyExchangeRatesJsonRequest.setClient(null);

        // Create the HistoryExchangeRatesJsonRequest, which fails.
        HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestMapper.toDto(
            historyExchangeRatesJsonRequest
        );

        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historyExchangeRatesJsonRequestDTO))
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
        historyExchangeRatesJsonRequest.setCurrency(null);

        // Create the HistoryExchangeRatesJsonRequest, which fails.
        HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestMapper.toDto(
            historyExchangeRatesJsonRequest
        );

        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historyExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Disabled
    @Test
    @Transactional
    void checkPeriodIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        historyExchangeRatesJsonRequest.setPeriod(null);

        // Create the HistoryExchangeRatesJsonRequest, which fails.
        HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestMapper.toDto(
            historyExchangeRatesJsonRequest
        );

        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historyExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHistoryExchangeRatesJsonRequests() throws Exception {
        // Initialize the database
        insertedHistoryExchangeRatesJsonRequest = historyExchangeRatesJsonRequestRepository.saveAndFlush(historyExchangeRatesJsonRequest);

        // Get all the historyExchangeRatesJsonRequestList
        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historyExchangeRatesJsonRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestId").value(hasItem(DEFAULT_REQUEST_ID)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD)));
    }

    @Test
    @Transactional
    void getHistoryExchangeRatesJsonRequest() throws Exception {
        // Initialize the database
        insertedHistoryExchangeRatesJsonRequest = historyExchangeRatesJsonRequestRepository.saveAndFlush(historyExchangeRatesJsonRequest);

        // Get the historyExchangeRatesJsonRequest
        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, historyExchangeRatesJsonRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historyExchangeRatesJsonRequest.getId().intValue()))
            .andExpect(jsonPath("$.requestId").value(DEFAULT_REQUEST_ID))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD));
    }

    @Test
    @Transactional
    void getNonExistingHistoryExchangeRatesJsonRequest() throws Exception {
        // Get the historyExchangeRatesJsonRequest
        restHistoryExchangeRatesJsonRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Disabled
    @Test
    @Transactional
    void putExistingHistoryExchangeRatesJsonRequest() throws Exception {
        // Initialize the database
        insertedHistoryExchangeRatesJsonRequest = historyExchangeRatesJsonRequestRepository.saveAndFlush(historyExchangeRatesJsonRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the historyExchangeRatesJsonRequest
        HistoryExchangeRatesJsonRequest updatedHistoryExchangeRatesJsonRequest = historyExchangeRatesJsonRequestRepository
            .findById(historyExchangeRatesJsonRequest.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedHistoryExchangeRatesJsonRequest are not directly saved in db
        em.detach(updatedHistoryExchangeRatesJsonRequest);
        updatedHistoryExchangeRatesJsonRequest
            .requestId(UPDATED_REQUEST_ID)
            .timestamp(UPDATED_TIMESTAMP)
            .client(UPDATED_CLIENT)
            .currency(UPDATED_CURRENCY)
            .period(UPDATED_PERIOD);
        HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestMapper.toDto(
            updatedHistoryExchangeRatesJsonRequest
        );

        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historyExchangeRatesJsonRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historyExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the HistoryExchangeRatesJsonRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHistoryExchangeRatesJsonRequestToMatchAllProperties(updatedHistoryExchangeRatesJsonRequest);
    }

    @Disabled
    @Test
    @Transactional
    void putNonExistingHistoryExchangeRatesJsonRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historyExchangeRatesJsonRequest.setId(longCount.incrementAndGet());

        // Create the HistoryExchangeRatesJsonRequest
        HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestMapper.toDto(
            historyExchangeRatesJsonRequest
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historyExchangeRatesJsonRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historyExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoryExchangeRatesJsonRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void putWithIdMismatchHistoryExchangeRatesJsonRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historyExchangeRatesJsonRequest.setId(longCount.incrementAndGet());

        // Create the HistoryExchangeRatesJsonRequest
        HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestMapper.toDto(
            historyExchangeRatesJsonRequest
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historyExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoryExchangeRatesJsonRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void putWithMissingIdPathParamHistoryExchangeRatesJsonRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historyExchangeRatesJsonRequest.setId(longCount.incrementAndGet());

        // Create the HistoryExchangeRatesJsonRequest
        HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestMapper.toDto(
            historyExchangeRatesJsonRequest
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historyExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HistoryExchangeRatesJsonRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void partialUpdateHistoryExchangeRatesJsonRequestWithPatch() throws Exception {
        // Initialize the database
        insertedHistoryExchangeRatesJsonRequest = historyExchangeRatesJsonRequestRepository.saveAndFlush(historyExchangeRatesJsonRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the historyExchangeRatesJsonRequest using partial update
        HistoryExchangeRatesJsonRequest partialUpdatedHistoryExchangeRatesJsonRequest = new HistoryExchangeRatesJsonRequest();
        partialUpdatedHistoryExchangeRatesJsonRequest.setId(historyExchangeRatesJsonRequest.getId());

        partialUpdatedHistoryExchangeRatesJsonRequest.requestId(UPDATED_REQUEST_ID).currency(UPDATED_CURRENCY).period(UPDATED_PERIOD);

        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistoryExchangeRatesJsonRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHistoryExchangeRatesJsonRequest))
            )
            .andExpect(status().isOk());

        // Validate the HistoryExchangeRatesJsonRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHistoryExchangeRatesJsonRequestUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHistoryExchangeRatesJsonRequest, historyExchangeRatesJsonRequest),
            getPersistedHistoryExchangeRatesJsonRequest(historyExchangeRatesJsonRequest)
        );
    }

    @Disabled
    @Test
    @Transactional
    void fullUpdateHistoryExchangeRatesJsonRequestWithPatch() throws Exception {
        // Initialize the database
        insertedHistoryExchangeRatesJsonRequest = historyExchangeRatesJsonRequestRepository.saveAndFlush(historyExchangeRatesJsonRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the historyExchangeRatesJsonRequest using partial update
        HistoryExchangeRatesJsonRequest partialUpdatedHistoryExchangeRatesJsonRequest = new HistoryExchangeRatesJsonRequest();
        partialUpdatedHistoryExchangeRatesJsonRequest.setId(historyExchangeRatesJsonRequest.getId());

        partialUpdatedHistoryExchangeRatesJsonRequest
            .requestId(UPDATED_REQUEST_ID)
            .timestamp(UPDATED_TIMESTAMP)
            .client(UPDATED_CLIENT)
            .currency(UPDATED_CURRENCY)
            .period(UPDATED_PERIOD);

        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistoryExchangeRatesJsonRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHistoryExchangeRatesJsonRequest))
            )
            .andExpect(status().isOk());

        // Validate the HistoryExchangeRatesJsonRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHistoryExchangeRatesJsonRequestUpdatableFieldsEquals(
            partialUpdatedHistoryExchangeRatesJsonRequest,
            getPersistedHistoryExchangeRatesJsonRequest(partialUpdatedHistoryExchangeRatesJsonRequest)
        );
    }

    @Disabled
    @Test
    @Transactional
    void patchNonExistingHistoryExchangeRatesJsonRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historyExchangeRatesJsonRequest.setId(longCount.incrementAndGet());

        // Create the HistoryExchangeRatesJsonRequest
        HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestMapper.toDto(
            historyExchangeRatesJsonRequest
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, historyExchangeRatesJsonRequestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(historyExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoryExchangeRatesJsonRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void patchWithIdMismatchHistoryExchangeRatesJsonRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historyExchangeRatesJsonRequest.setId(longCount.incrementAndGet());

        // Create the HistoryExchangeRatesJsonRequest
        HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestMapper.toDto(
            historyExchangeRatesJsonRequest
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(historyExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoryExchangeRatesJsonRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void patchWithMissingIdPathParamHistoryExchangeRatesJsonRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historyExchangeRatesJsonRequest.setId(longCount.incrementAndGet());

        // Create the HistoryExchangeRatesJsonRequest
        HistoryExchangeRatesJsonRequestDTO historyExchangeRatesJsonRequestDTO = historyExchangeRatesJsonRequestMapper.toDto(
            historyExchangeRatesJsonRequest
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(historyExchangeRatesJsonRequestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HistoryExchangeRatesJsonRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void deleteHistoryExchangeRatesJsonRequest() throws Exception {
        // Initialize the database
        insertedHistoryExchangeRatesJsonRequest = historyExchangeRatesJsonRequestRepository.saveAndFlush(historyExchangeRatesJsonRequest);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the historyExchangeRatesJsonRequest
        restHistoryExchangeRatesJsonRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, historyExchangeRatesJsonRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return historyExchangeRatesJsonRequestRepository.count();
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

    protected HistoryExchangeRatesJsonRequest getPersistedHistoryExchangeRatesJsonRequest(
        HistoryExchangeRatesJsonRequest historyExchangeRatesJsonRequest
    ) {
        return historyExchangeRatesJsonRequestRepository.findById(historyExchangeRatesJsonRequest.getId()).orElseThrow();
    }

    protected void assertPersistedHistoryExchangeRatesJsonRequestToMatchAllProperties(
        HistoryExchangeRatesJsonRequest expectedHistoryExchangeRatesJsonRequest
    ) {
        assertHistoryExchangeRatesJsonRequestAllPropertiesEquals(
            expectedHistoryExchangeRatesJsonRequest,
            getPersistedHistoryExchangeRatesJsonRequest(expectedHistoryExchangeRatesJsonRequest)
        );
    }

    protected void assertPersistedHistoryExchangeRatesJsonRequestToMatchUpdatableProperties(
        HistoryExchangeRatesJsonRequest expectedHistoryExchangeRatesJsonRequest
    ) {
        assertHistoryExchangeRatesJsonRequestAllUpdatablePropertiesEquals(
            expectedHistoryExchangeRatesJsonRequest,
            getPersistedHistoryExchangeRatesJsonRequest(expectedHistoryExchangeRatesJsonRequest)
        );
    }
}
