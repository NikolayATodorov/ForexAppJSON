package com.forex.test.service.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "command")
public class XmlCommandDTO {

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "get")
    private XmlGetRequestDTO xmlGetRequestDTO;

    @JacksonXmlProperty(localName = "history")
    private XmlHistoryRequestDTO xmlHistoryRequestDTO;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public XmlGetRequestDTO getXmlGetRequestDTO() {
        return xmlGetRequestDTO;
    }

    public void setXmlGetRequestDTO(XmlGetRequestDTO xmlGetRequestDTO) {
        this.xmlGetRequestDTO = xmlGetRequestDTO;
    }

    public XmlHistoryRequestDTO getXmlHistoryRequestDTO() {
        return xmlHistoryRequestDTO;
    }

    public void setXmlHistoryRequestDTO(XmlHistoryRequestDTO xmlHistoryRequestDTO) {
        this.xmlHistoryRequestDTO = xmlHistoryRequestDTO;
    }
}
