package com.example.web.person;

import java.io.UnsupportedEncodingException;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import com.example.common.util.CommonUtils;
import com.example.web.person.model.PersonForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/person")
public class PersonController implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/person/results").setViewName("person/results");
    }

    @GetMapping
    public String showForm(PersonForm personForm) {
        return "person/form";
    }

    @PostMapping
    public String checkPersonInfo(@Valid PersonForm personForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        String name = personForm.getName();
        log.info("name: {} ({})", CommonUtils.toHexString(name), name);
        log.info("age: {}", personForm.getAge());
        String encoding = personForm.getEncoding();
        log.info("encoding: {}", encoding);

        name = decode(name, encoding);
        log.info("name: {} ({})", CommonUtils.toHexString(name), name);
        personForm.setName(name);

        if (bindingResult.hasErrors()) {
            return "person/form";
        }

        redirectAttributes.addFlashAttribute(personForm);
        return "redirect:/person/results";
    }

    static String decode(String s, String encoding) {
        try {
            s = CommonUtils.decode(s, encoding);
        } catch (UnsupportedEncodingException e) {
            log.error("decode error", e);
        }
        return HtmlUtils.htmlUnescape(s);
    }

}
