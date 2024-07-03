package com.forex.test.web.rest;

import com.forex.test.service.XmlExchangeRatesRequestService;
import com.forex.test.service.dto.XmlCommandDTO;
import com.forex.test.service.dto.XmlExchangeRatesRequestDTO;
import com.forex.test.web.rest.errors.BadRequestAlertException;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xml_api")
public class XmlCommandController {

    private final XmlExchangeRatesRequestService xmlExchangeRatesRequestService;

    private static final String ENTITY_NAME = "xmlExchangeRatesRequest";

    public XmlCommandController(XmlExchangeRatesRequestService xmlExchangeRatesRequestService) {
        this.xmlExchangeRatesRequestService = xmlExchangeRatesRequestService;
    }

    @PostMapping(value = "/command", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<XmlCommandDTO> handleCommand(@RequestBody XmlCommandDTO command) {
        // check for duplicate keys

        Optional<XmlExchangeRatesRequestDTO> existingXmlExchangeRatesRequestDTO = xmlExchangeRatesRequestService.findByRequestId(
            command.getId()
        );

        if (!existingXmlExchangeRatesRequestDTO.isEmpty()) {
            throw new BadRequestAlertException(
                "An XmlExchangeRatesReques with the provided request id already exists",
                ENTITY_NAME,
                //                ENTITY_NAME_CURR_EX_RATES_JSON_REQUEST,
                "requestidexists"
            );
            //            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (command.getXmlGetRequestDTO() != null) {
            // Process the GET request
            System.out.println("Received GET command ID: " + command.getId());
            System.out.println("Consumer: " + command.getXmlGetRequestDTO().getConsumer());
            System.out.println("Currency: " + command.getXmlGetRequestDTO().getCurrency());

            // save the request in the tables and get the response

            XmlExchangeRatesRequestDTO xmlExchangeRatesRequestDTO = new XmlExchangeRatesRequestDTO();
            xmlExchangeRatesRequestDTO.setRequestId(command.getId());
            xmlExchangeRatesRequestDTO.setConsumerId(command.getXmlGetRequestDTO().getConsumer());
            xmlExchangeRatesRequestService.save(xmlExchangeRatesRequestDTO);
            // save the request in the table

            // get the response

        } else if (command.getXmlHistoryRequestDTO() != null) {
            // Process the HISTORY request
            System.out.println("Received HISTORY command ID: " + command.getId());
            System.out.println("Consumer: " + command.getXmlHistoryRequestDTO().getConsumer());
            System.out.println("Currency: " + command.getXmlHistoryRequestDTO().getCurrency());
            System.out.println("Period: " + command.getXmlHistoryRequestDTO().getPeriod());

            // save the request in the tables and get the response

            XmlExchangeRatesRequestDTO xmlExchangeRatesRequestDTO = new XmlExchangeRatesRequestDTO();
            xmlExchangeRatesRequestDTO.setRequestId(command.getId());
            xmlExchangeRatesRequestDTO.setConsumerId(command.getXmlHistoryRequestDTO().getConsumer());
            xmlExchangeRatesRequestService.save(xmlExchangeRatesRequestDTO);
            // save the request in the table

            // get the response

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // For demonstration, we just return the received command

        return new ResponseEntity<>(command, HttpStatus.OK);
        // return the requested response like in ExchangeRateResource

    }
}
