package com.converter.handler;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.converter.handler.Converter.converterValue;

@RestController
public class HttpHandler {
    @PostMapping(value = "/convert", consumes = "application/json")
    public void convertGetMessage(@RequestBody SenderStruct getMessage) {
        System.out.println("I get POST message, thanks");
        converterValue(getMessage);
    }
}
