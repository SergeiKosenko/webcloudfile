package ru.kosenko.webcloudfile.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("contacts")
    public String contakts() {
        return "contacts";
    }

    @GetMapping("registered")
    public String registered() {
        return "registered";
    }
}
