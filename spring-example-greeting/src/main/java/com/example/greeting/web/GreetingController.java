package com.example.greeting.web;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.common.util.CommonUtils;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            @RequestParam(name = "encoding", required = false) String encoding,
            Model model) {

        log.info("name: {} ({})", CommonUtils.toHexString(name), name);
        log.info("encoding: {}", encoding);

        name = decode(name, encoding);
        log.info("name: {} ({})", CommonUtils.toHexString(name), name);

        model.addAttribute("name", name);
        return "greeting/greeting";
    }

    static String decode(String s, String encoding) {
        try {
            s = CommonUtils.decode(s, encoding);
        } catch (UnsupportedEncodingException e) {
            log.error("decode error", e);
        }
        return CommonUtils.htmlUnescape(s);
    }

}
