package ava.rest;

import ava.common.Methods;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @GetMapping(Methods.HELLO_WORLD)
    public String helloWorld() {
        return "Hello world, dude!";
    }
}
