package com.example.web.greeting;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required = false, defaultValue = "World") String name,
            @RequestParam(name="encoding", required = false, defaultValue = "UTF-8") String encoding,
            Model model) {
        log.info("name: {} ({})", toHexString(name), name);
        log.info("encoding: {}", encoding);
        try {
            name = new String(name.getBytes(StandardCharsets.ISO_8859_1), encoding);
        } catch (UnsupportedEncodingException e) {
            log.warn("", e);
        }
        name = HtmlUtils.htmlUnescape(name);
        log.info("name: {} ({})", toHexString(name), name);
        model.addAttribute("name", name);
        return "greeting/greeting";
    }

    static String toHexString(String s) {
        return s.chars()
            .mapToObj(c -> String.format("%02X", c))
            .collect(Collectors.joining(" "));
    }

}
