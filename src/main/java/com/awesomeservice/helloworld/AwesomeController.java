package com.awesomeservice.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings("unused")
public class AwesomeController {

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
}
