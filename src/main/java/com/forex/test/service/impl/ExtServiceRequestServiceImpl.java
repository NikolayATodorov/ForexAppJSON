package com.forex.test.service.impl;

import com.forex.test.domain.ExtServiceRequest;
import com.forex.test.repository.ExtServiceRequestRepository;
import com.forex.test.service.ExtServiceRequestService;
import com.forex.test.service.dto.ExtServiceRequestDTO;
import com.forex.test.service.mapper.ExtServiceRequestMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ExtServiceRequest}.
 */
@Service
@Transactional
public class ExtServiceRequestServiceImpl implements ExtServiceRequestService {

    private final Logger log = LoggerFactory.getLogger(ExtServiceRequestServiceImpl.class);

    private final ExtServiceRequestRepository extServiceRequestRepository;

    private final ExtServiceRequestMapper extServiceRequestMapper;

    private final RabbitMQSender rabbitMQSender;

    public ExtServiceRequestServiceImpl(
        ExtServiceRequestRepository extServiceRequestRepository,
        ExtServiceRequestMapper extServiceRequestMapper,
        RabbitMQSender rabbitMQSender
    ) {
        this.extServiceRequestRepository = extServiceRequestRepository;
        this.extServiceRequestMapper = extServiceRequestMapper;
        this.rabbitMQSender = rabbitMQSender;
    }

    @Override
    public ExtServiceRequestDTO save(ExtServiceRequestDTO extServiceRequestDTO) {
        log.debug("Request to save ExtServiceRequest : {}", extServiceRequestDTO);
        ExtServiceRequest extServiceRequest = extServiceRequestMapper.toEntity(extServiceRequestDTO);
        extServiceRequest = extServiceRequestRepository.save(extServiceRequest);
        ExtServiceRequestDTO result = extServiceRequestMapper.toDto(extServiceRequest);

        // Send the message to RabbitMQ
        //        rabbitMQSender.send(result);
        rabbitMQSender.send(extServiceRequest);

        return result;
    }

    @Override
    public ExtServiceRequestDTO update(ExtServiceRequestDTO extServiceRequestDTO) {
        log.debug("Request to update ExtServiceRequest : {}", extServiceRequestDTO);
        ExtServiceRequest extServiceRequest = extServiceRequestMapper.toEntity(extServiceRequestDTO);
        extServiceRequest = extServiceRequestRepository.save(extServiceRequest);
        return extServiceRequestMapper.toDto(extServiceRequest);
    }

    @Override
    public Optional<ExtServiceRequestDTO> partialUpdate(ExtServiceRequestDTO extServiceRequestDTO) {
        log.debug("Request to partially update ExtServiceRequest : {}", extServiceRequestDTO);

        return extServiceRequestRepository
            .findById(extServiceRequestDTO.getId())
            .map(existingExtServiceRequest -> {
                extServiceRequestMapper.partialUpdate(existingExtServiceRequest, extServiceRequestDTO);

                return existingExtServiceRequest;
            })
            .map(extServiceRequestRepository::save)
            .map(extServiceRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExtServiceRequestDTO> findAll() {
        log.debug("Request to get all ExtServiceRequests");
        return extServiceRequestRepository
            .findAll()
            .stream()
            .map(extServiceRequestMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExtServiceRequestDTO> findOne(Long id) {
        log.debug("Request to get ExtServiceRequest : {}", id);
        return extServiceRequestRepository.findById(id).map(extServiceRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExtServiceRequest : {}", id);
        extServiceRequestRepository.deleteById(id);
    }
}
