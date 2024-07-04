package com.forex.test.service.mapper;

import com.forex.test.domain.ExtServiceRequest;
import com.forex.test.service.dto.ExtServiceRequestDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ExtServiceRequest} and its DTO {@link ExtServiceRequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExtServiceRequestMapper extends EntityMapper<ExtServiceRequestDTO, ExtServiceRequest> {}
