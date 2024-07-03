package com.forex.test.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A XmlExchangeRatesRequest.
 */
@Entity
@Table(name = "xml_exchange_rates_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class XmlExchangeRatesRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "request_id", nullable = false, unique = true)
    private String requestId;

    @NotNull
    @Column(name = "consumer_id", nullable = false)
    private String consumerId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public XmlExchangeRatesRequest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public XmlExchangeRatesRequest requestId(String requestId) {
        this.setRequestId(requestId);
        return this;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getConsumerId() {
        return this.consumerId;
    }

    public XmlExchangeRatesRequest consumerId(String consumerId) {
        this.setConsumerId(consumerId);
        return this;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof XmlExchangeRatesRequest)) {
            return false;
        }
        return getId() != null && getId().equals(((XmlExchangeRatesRequest) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "XmlExchangeRatesRequest{" +
            "id=" + getId() +
            ", requestId='" + getRequestId() + "'" +
            ", consumerId='" + getConsumerId() + "'" +
            "}";
    }
}
