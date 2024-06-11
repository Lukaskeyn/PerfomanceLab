package com.example.app.controller;

import com.example.app.model.RequestDTO;
import com.example.app.model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
public class MainController {
    Logger logger = LoggerFactory.getLogger(MainController.class);
    ObjectMapper mapper = new ObjectMapper();
    @PostMapping(value = "/info/balance", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object postBalance(@RequestBody RequestDTO requestDTO)  {
    try {
        String clientID = requestDTO.getClientId();
        BigDecimal balance;
        BigDecimal maxLimit;
        String currency;

        char firstDigit = clientID.charAt(0);
        if (firstDigit == '8') {
            currency = "US";
            maxLimit = new BigDecimal("2000.00");
        } else if (firstDigit == '9') {
            currency = "EU";
            maxLimit = new BigDecimal("1000.00");
        } else {
            currency = "RUB";
            maxLimit = new BigDecimal("10000.00");
        }

        balance = BigDecimal.valueOf(Math.round((Math.random() * maxLimit.doubleValue())*100.00)/100.00);

        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setClientId(clientID);
        responseDTO.setRqUID(requestDTO.getRqUID());
        responseDTO.setAccount(requestDTO.getAccount());
        responseDTO.setCurrency(currency);
        responseDTO.setMaxLimit(maxLimit);
        responseDTO.setBalance(balance);

        logger.info("*******RequestDTO*******" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
        logger.info("*******ResponseDTO*******" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

        return responseDTO;
    }
    catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    }
}
