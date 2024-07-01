package com.forex.test.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forex.test.config.ApplicationProperties;
import com.forex.test.service.ExchangeRateService;
import com.forex.test.service.ExchangeRatesUpdater;
import com.forex.test.service.dto.CurrencyRatesDTO;
import com.forex.test.service.dto.ExchangeRateDTO;
import com.forex.test.service.mapper.CurrencyRatesDTOToExchangeRateMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(ApplicationProperties.class)
public class ExchangeRatesUpdaterImpl implements ExchangeRatesUpdater {

    private final Logger log = LoggerFactory.getLogger(ExchangeRatesUpdaterImpl.class);
    private final ExchangeRateService exchangeRateService;

    @Value("${application.API_KEY}")
    String API_KEY;

    @Value("${application.base}")
    String base;

    @Value("${application.symbols}")
    String symbols;

    public ExchangeRatesUpdaterImpl(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    public ExchangeRatesUpdater setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;
        return this;
    }

    @Scheduled(fixedRateString = "${application.exchange-rate-update-interval}" + "000")
    public String updateExchangeRates() {
        String endpointUrl = "https://data.fixer.io/api/latest?access_key=" + API_KEY + "&base=" + base + "&symbols=" + symbols;

        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(endpointUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) { // 200 OK
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // map to exchangeRateDTO and save

                String test =
                    "{\n" +
                    "  \"base\": \"EUR\",\n" +
                    "  \"date\": \"2018-02-13\",\n" +
                    "  \"rates\": {\n" +
                    "    \"USD\": 1.260046,\n" +
                    "    \"CHF\": 0.933058,\n" +
                    "    \"GBP\": 0.719154\n" +
                    "  }\n" +
                    "}";

                ObjectMapper objectMapper = new ObjectMapper();
                CurrencyRatesDTO currencyRates = objectMapper.readValue(test, CurrencyRatesDTO.class);
                //                CurrencyRatesDTO currencyRates = objectMapper.readValue(result, CurrencyRatesDTO.class);

                CurrencyRatesDTOToExchangeRateMapper m = new CurrencyRatesDTOToExchangeRateMapper();
                ExchangeRateDTO exchangeRateDTO = m.toExchangeRateDTO(currencyRates);
                exchangeRateService.save(exchangeRateDTO);
                reader.close();
            } else {
                result.append("GET request not worked, Response Code: ").append(responseCode);
                log.error(String.valueOf(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception: " + e.getMessage());
            return "Exception: " + e.getMessage();
        }
        return result.toString();
    }
}
