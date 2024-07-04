package com.forex.test.service.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "exchange_rates_list")
public class XmlResponseDTO {

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<XmlExchangeRatesDTO> exchangeRates = new ArrayList<>();

    public List<XmlExchangeRatesDTO> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(List<XmlExchangeRatesDTO> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }
}
