package com.forex.test.repository;

import com.forex.test.domain.XmlExchangeRatesRequest;
import com.forex.test.service.dto.XmlExchangeRatesRequestDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the XmlExchangeRatesRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface XmlExchangeRatesRequestRepository extends JpaRepository<XmlExchangeRatesRequest, Long> {
    Optional<XmlExchangeRatesRequest> findByRequestId(String requestId);
}
