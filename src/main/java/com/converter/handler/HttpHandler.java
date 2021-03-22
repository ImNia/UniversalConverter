package com.converter.handler;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.converter.handler.Converter.converterValue;

@RestController
public class HttpHandler {
    //TODO независит от имен
    @PostMapping(value = "/convert", consumes = "application/json; charset=utf-16")
    public ReceiveStruct convertGetMessage(@RequestBody SenderStruct getMessage) {
        System.out.println("I get POST message, thanks");
        return new ReceiveStruct(converterValue(getMessage));
    }
}
