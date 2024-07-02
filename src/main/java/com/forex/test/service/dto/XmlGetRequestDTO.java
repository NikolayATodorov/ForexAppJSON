package com.forex.test.service.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class XmlGetRequestDTO {

    @JacksonXmlProperty(isAttribute = true)
    private String consumer;

    @JacksonXmlProperty(localName = "currency")
    private String currency;

    // Getters and setters
    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
