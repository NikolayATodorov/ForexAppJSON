package com.forex.test.repository;

import com.forex.test.domain.ExtServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ExtServiceRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtServiceRequestRepository extends JpaRepository<ExtServiceRequest, Long> {}
