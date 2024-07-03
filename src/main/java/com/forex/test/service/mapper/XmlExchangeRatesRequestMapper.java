package com.forex.test.service.mapper;

import com.forex.test.domain.XmlExchangeRatesRequest;
import com.forex.test.service.dto.XmlExchangeRatesRequestDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link XmlExchangeRatesRequest} and its DTO {@link XmlExchangeRatesRequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface XmlExchangeRatesRequestMapper extends EntityMapper<XmlExchangeRatesRequestDTO, XmlExchangeRatesRequest> {}
