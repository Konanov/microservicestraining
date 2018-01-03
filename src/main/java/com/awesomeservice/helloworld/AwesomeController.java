package com.awesomeservice.helloworld;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@SuppressWarnings("unused")
@RequiredArgsConstructor
public class AwesomeController {

    private final MessageSource messageSource;

    @GetMapping(path = "sup")
    public String sup() {
        return "Sup, faggot!";
    }

    @GetMapping(path = "sup-faggot")
    public Faggot supFaggot() {
        return new Faggot("Sup, faggot!");
    }

    @GetMapping(path = "sup-faggot/with-name/{name}")
    public Faggot supFaggot(@PathVariable String name) {
        return new Faggot(String.format("Sup, %s, you are faggot!", name));
    }

    @GetMapping(path = "hello-world")
    public String hi(@RequestHeader(required = false, name = "Accept-Language") Locale locale) {
        return messageSource.getMessage("good.morning.message", null, locale);
    }
}
