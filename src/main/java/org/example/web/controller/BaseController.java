package org.example.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {


    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }
}
