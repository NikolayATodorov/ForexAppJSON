package com.forex.test.service.impl;

import com.forex.test.domain.XmlExchangeRatesRequest;
import com.forex.test.repository.XmlExchangeRatesRequestRepository;
import com.forex.test.service.XmlExchangeRatesRequestService;
import com.forex.test.service.dto.XmlExchangeRatesRequestDTO;
import com.forex.test.service.mapper.XmlExchangeRatesRequestMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link XmlExchangeRatesRequest}.
 */
@Service
@Transactional
public class XmlExchangeRatesRequestServiceImpl implements XmlExchangeRatesRequestService {

    private final Logger log = LoggerFactory.getLogger(XmlExchangeRatesRequestServiceImpl.class);

    private final XmlExchangeRatesRequestRepository xmlExchangeRatesRequestRepository;

    private final XmlExchangeRatesRequestMapper xmlExchangeRatesRequestMapper;

    public XmlExchangeRatesRequestServiceImpl(
        XmlExchangeRatesRequestRepository xmlExchangeRatesRequestRepository,
        XmlExchangeRatesRequestMapper xmlExchangeRatesRequestMapper
    ) {
        this.xmlExchangeRatesRequestRepository = xmlExchangeRatesRequestRepository;
        this.xmlExchangeRatesRequestMapper = xmlExchangeRatesRequestMapper;
    }

    @Override
    public XmlExchangeRatesRequestDTO save(XmlExchangeRatesRequestDTO xmlExchangeRatesRequestDTO) {
        log.debug("Request to save XmlExchangeRatesRequest : {}", xmlExchangeRatesRequestDTO);
        XmlExchangeRatesRequest xmlExchangeRatesRequest = xmlExchangeRatesRequestMapper.toEntity(xmlExchangeRatesRequestDTO);
        xmlExchangeRatesRequest = xmlExchangeRatesRequestRepository.save(xmlExchangeRatesRequest);
        return xmlExchangeRatesRequestMapper.toDto(xmlExchangeRatesRequest);
    }

    @Override
    public XmlExchangeRatesRequestDTO update(XmlExchangeRatesRequestDTO xmlExchangeRatesRequestDTO) {
        log.debug("Request to update XmlExchangeRatesRequest : {}", xmlExchangeRatesRequestDTO);
        XmlExchangeRatesRequest xmlExchangeRatesRequest = xmlExchangeRatesRequestMapper.toEntity(xmlExchangeRatesRequestDTO);
        xmlExchangeRatesRequest = xmlExchangeRatesRequestRepository.save(xmlExchangeRatesRequest);
        return xmlExchangeRatesRequestMapper.toDto(xmlExchangeRatesRequest);
    }

    @Override
    public Optional<XmlExchangeRatesRequestDTO> partialUpdate(XmlExchangeRatesRequestDTO xmlExchangeRatesRequestDTO) {
        log.debug("Request to partially update XmlExchangeRatesRequest : {}", xmlExchangeRatesRequestDTO);

        return xmlExchangeRatesRequestRepository
            .findById(xmlExchangeRatesRequestDTO.getId())
            .map(existingXmlExchangeRatesRequest -> {
                xmlExchangeRatesRequestMapper.partialUpdate(existingXmlExchangeRatesRequest, xmlExchangeRatesRequestDTO);

                return existingXmlExchangeRatesRequest;
            })
            .map(xmlExchangeRatesRequestRepository::save)
            .map(xmlExchangeRatesRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<XmlExchangeRatesRequestDTO> findAll() {
        log.debug("Request to get all XmlExchangeRatesRequests");
        return xmlExchangeRatesRequestRepository
            .findAll()
            .stream()
            .map(xmlExchangeRatesRequestMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<XmlExchangeRatesRequestDTO> findOne(Long id) {
        log.debug("Request to get XmlExchangeRatesRequest : {}", id);
        return xmlExchangeRatesRequestRepository.findById(id).map(xmlExchangeRatesRequestMapper::toDto);
    }

    @Override
    public Optional<XmlExchangeRatesRequestDTO> findByRequestId(String requestId) {
        log.debug("Request to get XmlExchangeRatesRequest : {}", requestId);
        return xmlExchangeRatesRequestRepository.findByRequestId(requestId).map(xmlExchangeRatesRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete XmlExchangeRatesRequest : {}", id);
        xmlExchangeRatesRequestRepository.deleteById(id);
    }
}
