package com.forex.test.web.rest;

import com.forex.test.service.dto.XmlCommandDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xml_api")
public class XmlCommandController {

    @PostMapping(value = "/command", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<XmlCommandDTO> handleCommand(@RequestBody XmlCommandDTO command) {
        if (command.getXmlGetRequestDTO() != null) {
            // Process the GET request
            System.out.println("Received GET command ID: " + command.getId());
            System.out.println("Consumer: " + command.getXmlGetRequestDTO().getConsumer());
            System.out.println("Currency: " + command.getXmlGetRequestDTO().getCurrency());
            // save the request in the tables
            // and get the response

        } else if (command.getHistoryRequest() != null) {
            // Process the HISTORY request
            System.out.println("Received HISTORY command ID: " + command.getId());
            System.out.println("Consumer: " + command.getHistoryRequest().getConsumer());
            System.out.println("Currency: " + command.getHistoryRequest().getCurrency());
            System.out.println("Period: " + command.getHistoryRequest().getPeriod());
            // save the request in the tables
            // and get the response

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // For demonstration, we just return the received command

        return new ResponseEntity<>(command, HttpStatus.OK);
        // return the requested response

    }
}
