package com.forex.test.service.dto;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.forex.test.domain.XmlExchangeRatesRequest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class XmlExchangeRatesRequestDTO implements Serializable {

    private Long id;

    @NotNull
    private String requestId;

    @NotNull
    private String consumerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof XmlExchangeRatesRequestDTO)) {
            return false;
        }

        XmlExchangeRatesRequestDTO xmlExchangeRatesRequestDTO = (XmlExchangeRatesRequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, xmlExchangeRatesRequestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "XmlExchangeRatesRequestDTO{" +
            "id=" + getId() +
            ", requestId='" + getRequestId() + "'" +
            ", consumerId='" + getConsumerId() + "'" +
            "}";
    }
}
