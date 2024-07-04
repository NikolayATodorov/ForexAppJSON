package com.forex.test.service.mapper;

import com.forex.test.service.dto.ExchangeRateDTO;
import com.forex.test.service.dto.XmlExchangeRatesDTO;
import org.springframework.stereotype.Service;

@Service
public class JsonToXmlExchangeRatesMapper {

    public XmlExchangeRatesDTO JsonToXml(ExchangeRateDTO exchangeRateDTO) {
        XmlExchangeRatesDTO xmlExchangeRatesDTO = new XmlExchangeRatesDTO();
        xmlExchangeRatesDTO.setBaseCurrency(exchangeRateDTO.getBase());
        xmlExchangeRatesDTO.setTimestamp(exchangeRateDTO.getTimestamp());
        xmlExchangeRatesDTO.setChf(exchangeRateDTO.getChf());
        xmlExchangeRatesDTO.setGbp(exchangeRateDTO.getGbp());
        xmlExchangeRatesDTO.setUsd(exchangeRateDTO.getUsd());
        return xmlExchangeRatesDTO;
    }
}
