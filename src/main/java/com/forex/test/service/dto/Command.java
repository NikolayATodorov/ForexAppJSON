package com.forex.test.service.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "command")
public class Command {

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "get")
    private GetRequest getRequest;

    @JacksonXmlProperty(localName = "history")
    private HistoryRequest historyRequest;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GetRequest getGetRequest() {
        return getRequest;
    }

    public void setGetRequest(GetRequest getRequest) {
        this.getRequest = getRequest;
    }

    public HistoryRequest getHistoryRequest() {
        return historyRequest;
    }

    public void setHistoryRequest(HistoryRequest historyRequest) {
        this.historyRequest = historyRequest;
    }
}
