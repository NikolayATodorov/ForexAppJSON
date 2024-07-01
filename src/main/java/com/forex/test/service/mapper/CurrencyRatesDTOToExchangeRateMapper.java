package com.forex.test.service.mapper;

import com.forex.test.service.dto.CurrencyRatesDTO;
import com.forex.test.service.dto.ExchangeRateDTO;
import java.math.BigDecimal;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class CurrencyRatesDTOToExchangeRateMapper {

    public ExchangeRateDTO toExchangeRateDTO(CurrencyRatesDTO dto) {
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        exchangeRateDTO.setBase(dto.getBase());
        exchangeRateDTO.setTimestamp(Instant.now());
        exchangeRateDTO.setUsd(BigDecimal.valueOf(dto.getRates().get("USD")));
        exchangeRateDTO.setGbp(BigDecimal.valueOf(dto.getRates().get("GBP")));
        exchangeRateDTO.setChf(BigDecimal.valueOf(dto.getRates().get("CHF")));
        return exchangeRateDTO;
    }
}
