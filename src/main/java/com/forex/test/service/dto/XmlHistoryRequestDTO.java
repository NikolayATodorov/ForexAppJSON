package com.forex.test.service.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class XmlHistoryRequestDTO {

    @JacksonXmlProperty(isAttribute = true)
    private String consumer;

    @JacksonXmlProperty(isAttribute = true)
    private String currency;

    @JacksonXmlProperty(isAttribute = true)
    private String period;

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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
