package com.forex.test.service.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@JacksonXmlRootElement(localName = "exchange rates")
public class XmlExchangeRatesDTO {

    @NotNull
    @Size(min = 3, max = 3)
    private String baseCurrency;

    @NotNull
    private Instant timestamp;

    private BigDecimal usd;

    private BigDecimal gbp;

    private BigDecimal chf;

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String base) {
        this.baseCurrency = base;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getUsd() {
        return usd;
    }

    public void setUsd(BigDecimal usd) {
        this.usd = usd;
    }

    public BigDecimal getGbp() {
        return gbp;
    }

    public void setGbp(BigDecimal gbp) {
        this.gbp = gbp;
    }

    public BigDecimal getChf() {
        return chf;
    }

    public void setChf(BigDecimal chf) {
        this.chf = chf;
    }
}
