package com.jekdev.com.sacontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "test")
public class SaController {

    @GetMapping(path = "hello")
    public String getString(){
        return "Hello\n";
    }
}
