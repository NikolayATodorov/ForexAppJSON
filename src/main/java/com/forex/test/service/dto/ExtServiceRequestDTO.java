package com.forex.test.service.dto;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.forex.test.domain.ExtServiceRequest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExtServiceRequestDTO implements Serializable {

    private Long id;

    @NotNull
    private String serviceName;

    @NotNull
    private String requestId;

    @NotNull
    private Instant timeStamp;

    @NotNull
    private String clientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtServiceRequestDTO)) {
            return false;
        }

        ExtServiceRequestDTO extServiceRequestDTO = (ExtServiceRequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, extServiceRequestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExtServiceRequestDTO{" +
            "id=" + getId() +
            ", serviceName='" + getServiceName() + "'" +
            ", requestId='" + getRequestId() + "'" +
            ", timeStamp='" + getTimeStamp() + "'" +
            ", clientId='" + getClientId() + "'" +
            "}";
    }
}
