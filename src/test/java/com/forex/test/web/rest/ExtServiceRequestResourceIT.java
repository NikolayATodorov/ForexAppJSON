package com.forex.test.web.rest;

import static com.forex.test.domain.ExtServiceRequestAsserts.*;
import static com.forex.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forex.test.IntegrationTest;
import com.forex.test.domain.ExtServiceRequest;
import com.forex.test.repository.ExtServiceRequestRepository;
import com.forex.test.service.dto.ExtServiceRequestDTO;
import com.forex.test.service.mapper.ExtServiceRequestMapper;
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
 * Integration tests for the {@link ExtServiceRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExtServiceRequestResourceIT {

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_ID = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIME_STAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_STAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ext-service-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ExtServiceRequestRepository extServiceRequestRepository;

    @Autowired
    private ExtServiceRequestMapper extServiceRequestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExtServiceRequestMockMvc;

    private ExtServiceRequest extServiceRequest;

    private ExtServiceRequest insertedExtServiceRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtServiceRequest createEntity(EntityManager em) {
        ExtServiceRequest extServiceRequest = new ExtServiceRequest()
            .serviceName(DEFAULT_SERVICE_NAME)
            .requestId(DEFAULT_REQUEST_ID)
            .timeStamp(DEFAULT_TIME_STAMP)
            .clientId(DEFAULT_CLIENT_ID);
        return extServiceRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtServiceRequest createUpdatedEntity(EntityManager em) {
        ExtServiceRequest extServiceRequest = new ExtServiceRequest()
            .serviceName(UPDATED_SERVICE_NAME)
            .requestId(UPDATED_REQUEST_ID)
            .timeStamp(UPDATED_TIME_STAMP)
            .clientId(UPDATED_CLIENT_ID);
        return extServiceRequest;
    }

    @BeforeEach
    public void initTest() {
        extServiceRequest = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedExtServiceRequest != null) {
            extServiceRequestRepository.delete(insertedExtServiceRequest);
            insertedExtServiceRequest = null;
        }
    }

    @Disabled
    @Test
    @Transactional
    void createExtServiceRequest() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ExtServiceRequest
        ExtServiceRequestDTO extServiceRequestDTO = extServiceRequestMapper.toDto(extServiceRequest);
        var returnedExtServiceRequestDTO = om.readValue(
            restExtServiceRequestMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(extServiceRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ExtServiceRequestDTO.class
        );

        // Validate the ExtServiceRequest in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedExtServiceRequest = extServiceRequestMapper.toEntity(returnedExtServiceRequestDTO);
        assertExtServiceRequestUpdatableFieldsEquals(returnedExtServiceRequest, getPersistedExtServiceRequest(returnedExtServiceRequest));

        insertedExtServiceRequest = returnedExtServiceRequest;
    }

    @Disabled
    @Test
    @Transactional
    void createExtServiceRequestWithExistingId() throws Exception {
        // Create the ExtServiceRequest with an existing ID
        extServiceRequest.setId(1L);
        ExtServiceRequestDTO extServiceRequestDTO = extServiceRequestMapper.toDto(extServiceRequest);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExtServiceRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(extServiceRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExtServiceRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Disabled
    @Test
    @Transactional
    void checkServiceNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        extServiceRequest.setServiceName(null);

        // Create the ExtServiceRequest, which fails.
        ExtServiceRequestDTO extServiceRequestDTO = extServiceRequestMapper.toDto(extServiceRequest);

        restExtServiceRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(extServiceRequestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Disabled
    @Test
    @Transactional
    void checkRequestIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        extServiceRequest.setRequestId(null);

        // Create the ExtServiceRequest, which fails.
        ExtServiceRequestDTO extServiceRequestDTO = extServiceRequestMapper.toDto(extServiceRequest);

        restExtServiceRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(extServiceRequestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Disabled
    @Test
    @Transactional
    void checkTimeStampIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        extServiceRequest.setTimeStamp(null);

        // Create the ExtServiceRequest, which fails.
        ExtServiceRequestDTO extServiceRequestDTO = extServiceRequestMapper.toDto(extServiceRequest);

        restExtServiceRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(extServiceRequestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Disabled
    @Test
    @Transactional
    void checkClientIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        extServiceRequest.setClientId(null);

        // Create the ExtServiceRequest, which fails.
        ExtServiceRequestDTO extServiceRequestDTO = extServiceRequestMapper.toDto(extServiceRequest);

        restExtServiceRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(extServiceRequestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExtServiceRequests() throws Exception {
        // Initialize the database
        insertedExtServiceRequest = extServiceRequestRepository.saveAndFlush(extServiceRequest);

        // Get all the extServiceRequestList
        restExtServiceRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extServiceRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].requestId").value(hasItem(DEFAULT_REQUEST_ID)))
            .andExpect(jsonPath("$.[*].timeStamp").value(hasItem(DEFAULT_TIME_STAMP.toString())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)));
    }

    @Test
    @Transactional
    void getExtServiceRequest() throws Exception {
        // Initialize the database
        insertedExtServiceRequest = extServiceRequestRepository.saveAndFlush(extServiceRequest);

        // Get the extServiceRequest
        restExtServiceRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, extServiceRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(extServiceRequest.getId().intValue()))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME))
            .andExpect(jsonPath("$.requestId").value(DEFAULT_REQUEST_ID))
            .andExpect(jsonPath("$.timeStamp").value(DEFAULT_TIME_STAMP.toString()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID));
    }

    @Test
    @Transactional
    void getNonExistingExtServiceRequest() throws Exception {
        // Get the extServiceRequest
        restExtServiceRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Disabled
    @Test
    @Transactional
    void putExistingExtServiceRequest() throws Exception {
        // Initialize the database
        insertedExtServiceRequest = extServiceRequestRepository.saveAndFlush(extServiceRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the extServiceRequest
        ExtServiceRequest updatedExtServiceRequest = extServiceRequestRepository.findById(extServiceRequest.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedExtServiceRequest are not directly saved in db
        em.detach(updatedExtServiceRequest);
        updatedExtServiceRequest
            .serviceName(UPDATED_SERVICE_NAME)
            .requestId(UPDATED_REQUEST_ID)
            .timeStamp(UPDATED_TIME_STAMP)
            .clientId(UPDATED_CLIENT_ID);
        ExtServiceRequestDTO extServiceRequestDTO = extServiceRequestMapper.toDto(updatedExtServiceRequest);

        restExtServiceRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, extServiceRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(extServiceRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the ExtServiceRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedExtServiceRequestToMatchAllProperties(updatedExtServiceRequest);
    }

    @Disabled
    @Test
    @Transactional
    void putNonExistingExtServiceRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        extServiceRequest.setId(longCount.incrementAndGet());

        // Create the ExtServiceRequest
        ExtServiceRequestDTO extServiceRequestDTO = extServiceRequestMapper.toDto(extServiceRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExtServiceRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, extServiceRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(extServiceRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExtServiceRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void putWithIdMismatchExtServiceRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        extServiceRequest.setId(longCount.incrementAndGet());

        // Create the ExtServiceRequest
        ExtServiceRequestDTO extServiceRequestDTO = extServiceRequestMapper.toDto(extServiceRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExtServiceRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(extServiceRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExtServiceRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void putWithMissingIdPathParamExtServiceRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        extServiceRequest.setId(longCount.incrementAndGet());

        // Create the ExtServiceRequest
        ExtServiceRequestDTO extServiceRequestDTO = extServiceRequestMapper.toDto(extServiceRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExtServiceRequestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(extServiceRequestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExtServiceRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void partialUpdateExtServiceRequestWithPatch() throws Exception {
        // Initialize the database
        insertedExtServiceRequest = extServiceRequestRepository.saveAndFlush(extServiceRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the extServiceRequest using partial update
        ExtServiceRequest partialUpdatedExtServiceRequest = new ExtServiceRequest();
        partialUpdatedExtServiceRequest.setId(extServiceRequest.getId());

        restExtServiceRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExtServiceRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExtServiceRequest))
            )
            .andExpect(status().isOk());

        // Validate the ExtServiceRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExtServiceRequestUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedExtServiceRequest, extServiceRequest),
            getPersistedExtServiceRequest(extServiceRequest)
        );
    }

    @Disabled
    @Test
    @Transactional
    void fullUpdateExtServiceRequestWithPatch() throws Exception {
        // Initialize the database
        insertedExtServiceRequest = extServiceRequestRepository.saveAndFlush(extServiceRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the extServiceRequest using partial update
        ExtServiceRequest partialUpdatedExtServiceRequest = new ExtServiceRequest();
        partialUpdatedExtServiceRequest.setId(extServiceRequest.getId());

        partialUpdatedExtServiceRequest
            .serviceName(UPDATED_SERVICE_NAME)
            .requestId(UPDATED_REQUEST_ID)
            .timeStamp(UPDATED_TIME_STAMP)
            .clientId(UPDATED_CLIENT_ID);

        restExtServiceRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExtServiceRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExtServiceRequest))
            )
            .andExpect(status().isOk());

        // Validate the ExtServiceRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExtServiceRequestUpdatableFieldsEquals(
            partialUpdatedExtServiceRequest,
            getPersistedExtServiceRequest(partialUpdatedExtServiceRequest)
        );
    }

    @Disabled
    @Test
    @Transactional
    void patchNonExistingExtServiceRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        extServiceRequest.setId(longCount.incrementAndGet());

        // Create the ExtServiceRequest
        ExtServiceRequestDTO extServiceRequestDTO = extServiceRequestMapper.toDto(extServiceRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExtServiceRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, extServiceRequestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(extServiceRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExtServiceRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void patchWithIdMismatchExtServiceRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        extServiceRequest.setId(longCount.incrementAndGet());

        // Create the ExtServiceRequest
        ExtServiceRequestDTO extServiceRequestDTO = extServiceRequestMapper.toDto(extServiceRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExtServiceRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(extServiceRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExtServiceRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void patchWithMissingIdPathParamExtServiceRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        extServiceRequest.setId(longCount.incrementAndGet());

        // Create the ExtServiceRequest
        ExtServiceRequestDTO extServiceRequestDTO = extServiceRequestMapper.toDto(extServiceRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExtServiceRequestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(extServiceRequestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExtServiceRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Disabled
    @Test
    @Transactional
    void deleteExtServiceRequest() throws Exception {
        // Initialize the database
        insertedExtServiceRequest = extServiceRequestRepository.saveAndFlush(extServiceRequest);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the extServiceRequest
        restExtServiceRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, extServiceRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return extServiceRequestRepository.count();
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

    protected ExtServiceRequest getPersistedExtServiceRequest(ExtServiceRequest extServiceRequest) {
        return extServiceRequestRepository.findById(extServiceRequest.getId()).orElseThrow();
    }

    protected void assertPersistedExtServiceRequestToMatchAllProperties(ExtServiceRequest expectedExtServiceRequest) {
        assertExtServiceRequestAllPropertiesEquals(expectedExtServiceRequest, getPersistedExtServiceRequest(expectedExtServiceRequest));
    }

    protected void assertPersistedExtServiceRequestToMatchUpdatableProperties(ExtServiceRequest expectedExtServiceRequest) {
        assertExtServiceRequestAllUpdatablePropertiesEquals(
            expectedExtServiceRequest,
            getPersistedExtServiceRequest(expectedExtServiceRequest)
        );
    }
}
