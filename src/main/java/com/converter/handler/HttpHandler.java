package com.converter.handler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpHandler {
    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    public void convertGetMessage() {
        System.out.println("I get POST message, thanks");
    }
}
