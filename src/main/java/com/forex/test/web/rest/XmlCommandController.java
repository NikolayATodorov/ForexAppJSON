package com.forex.test.web.rest;

import com.forex.test.domain.enumeration.ExtServiceName;
import com.forex.test.service.ExchangeRateService;
import com.forex.test.service.ExtServiceRequestService;
import com.forex.test.service.XmlExchangeRatesRequestService;
import com.forex.test.service.dto.*;
import com.forex.test.service.mapper.CurrencyRatesDTOToExchangeRateMapper;
import com.forex.test.service.mapper.JsonToXmlExchangeRatesMapper;
import com.forex.test.web.rest.errors.BadRequestAlertException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xml_api")
public class XmlCommandController {

    private static final String ENTITY_NAME = "xmlExchangeRatesRequest";

    private final XmlExchangeRatesRequestService xmlExchangeRatesRequestService;
    private final ExchangeRateService exchangeRateService;
    private final ExtServiceRequestService extServiceRequestService;
    private final JsonToXmlExchangeRatesMapper jsonToXmlExchangeRatesMapper;

    @Value(value = "${application.exchange-rate-update-interval}")
    int updateInterval;

    public XmlCommandController(
        XmlExchangeRatesRequestService xmlExchangeRatesRequestService,
        ExchangeRateService exchangeRateService,
        CurrencyRatesDTOToExchangeRateMapper currencyRatesDTOToExchangeRateMapper,
        ExtServiceRequestService extServiceRequestService,
        JsonToXmlExchangeRatesMapper jsonToXmlExchangeRatesMapper
    ) {
        this.xmlExchangeRatesRequestService = xmlExchangeRatesRequestService;
        this.exchangeRateService = exchangeRateService;
        this.extServiceRequestService = extServiceRequestService;
        this.jsonToXmlExchangeRatesMapper = jsonToXmlExchangeRatesMapper;
    }

    @PostMapping(value = "/command", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<XmlResponseDTO> handleCommand(@RequestBody XmlCommandDTO command) {
        // check for duplicate keys

        Optional<XmlExchangeRatesRequestDTO> existingXmlExchangeRatesRequestDTO = xmlExchangeRatesRequestService.findByRequestId(
            command.getId()
        );

        if (!existingXmlExchangeRatesRequestDTO.isEmpty()) {
            throw new BadRequestAlertException(
                "An XmlExchangeRatesReques with the provided request id already exists",
                ENTITY_NAME,
                "requestidexists"
            );
        }

        XmlResponseDTO xmlResponseDTO = new XmlResponseDTO();
        ExchangeRateDTO exchangeRateDTO = null;

        if (command.getXmlGetRequestDTO() != null) {
            // Process the GET request

            // save the request in the table for xml requests
            XmlExchangeRatesRequestDTO xmlExchangeRatesRequestDTO = new XmlExchangeRatesRequestDTO();
            xmlExchangeRatesRequestDTO.setRequestId(command.getId());
            xmlExchangeRatesRequestDTO.setConsumerId(command.getXmlGetRequestDTO().getConsumer());
            xmlExchangeRatesRequestService.save(xmlExchangeRatesRequestDTO);

            // save the request in the table for json & xml requests
            ExtServiceRequestDTO extServiceRequestDTO = new ExtServiceRequestDTO();
            extServiceRequestDTO.setServiceName(ExtServiceName.EXT_SERVICE_1.name());
            extServiceRequestDTO.setRequestId(command.getId());
            extServiceRequestDTO.setClientId(command.getXmlGetRequestDTO().getConsumer());
            extServiceRequestDTO.setTimeStamp(Instant.now());
            extServiceRequestService.save(extServiceRequestDTO);

            // get the response
            Instant now = Instant.now();
            Instant startOfPeriod = now.minus(updateInterval, ChronoUnit.MINUTES);
            List<ExchangeRateDTO> exchangeRates = exchangeRateService.findByBaseAndTimestampGreaterThanOrEqualOrderByTimestampDesc(
                command.getXmlGetRequestDTO().getCurrency(),
                startOfPeriod
            );

            if (exchangeRates != null && !exchangeRates.isEmpty() && exchangeRates.listIterator(0).hasNext()) {
                exchangeRateDTO = exchangeRates.listIterator(0).next();
                xmlResponseDTO.getExchangeRates().add(jsonToXmlExchangeRatesMapper.JsonToXml(exchangeRateDTO));
            }
        } else if (command.getXmlHistoryRequestDTO() != null) {
            // Process the HISTORY request

            // save the request in the table for xml requests
            XmlExchangeRatesRequestDTO xmlExchangeRatesRequestDTO = new XmlExchangeRatesRequestDTO();
            xmlExchangeRatesRequestDTO.setRequestId(command.getId());
            xmlExchangeRatesRequestDTO.setConsumerId(command.getXmlHistoryRequestDTO().getConsumer());
            xmlExchangeRatesRequestService.save(xmlExchangeRatesRequestDTO);

            // save the request in the table for json & xml requests
            ExtServiceRequestDTO extServiceRequestDTO = new ExtServiceRequestDTO();
            extServiceRequestDTO.setServiceName(ExtServiceName.EXT_SERVICE_1.name());
            extServiceRequestDTO.setRequestId(command.getId());
            extServiceRequestDTO.setClientId(command.getXmlHistoryRequestDTO().getConsumer());
            extServiceRequestDTO.setTimeStamp(Instant.now());
            extServiceRequestService.save(extServiceRequestDTO);

            // get the response
            Instant now = Instant.now();
            Instant startOfPeriod = now.minus(command.getXmlHistoryRequestDTO().getPeriod(), ChronoUnit.HOURS);
            List<ExchangeRateDTO> exchangeRates = exchangeRateService.findByBaseAndTimestampGreaterThanOrEqualOrderByTimestampDesc(
                command.getXmlHistoryRequestDTO().getCurrency(),
                startOfPeriod
            );

            if (exchangeRates != null && !exchangeRates.isEmpty()) {
                ListIterator<ExchangeRateDTO> iterator = exchangeRates.listIterator(0);
                while (iterator.hasNext()) {
                    xmlResponseDTO.getExchangeRates().add(jsonToXmlExchangeRatesMapper.JsonToXml(iterator.next()));
                }
            }
        } else {
            throw new BadRequestAlertException(
                "An XmlExchangeRatesReques with the provided request id already exists",
                ENTITY_NAME,
                "requestidexists"
            );
        }
        return new ResponseEntity<>(xmlResponseDTO, HttpStatus.OK);
    }
}
