package com.example.greeting.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.common.util.CustomFunctions;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            Model model) {

        log.info(CustomFunctions.number(1234567, "#,###"));
        log.info(CustomFunctions.warekiText("20211023"));

        model.addAttribute("name", name);
        return "greeting/greeting";
    }

}
