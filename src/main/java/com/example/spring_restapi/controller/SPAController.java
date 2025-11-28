package com.example.spring_restapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SPAController {
    @GetMapping(path = {"/", "/{path:[^\\.]*}", "/{path:.*}/{path2:[^\\.]*}"})
    public String indexPage(){
        return "forward:/index.html";
    }
}
