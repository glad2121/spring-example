package com.example.greeting.web;

import static org.assertj.core.api.Assertions.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

class GreetingControllerTest {

    GreetingController controller;

    @BeforeEach
    void setUp() throws Exception {
        controller = new GreetingController();
    }

    @AfterEach
    void tearDown() throws Exception {
        controller = null;
    }

    @Test
    void testGreeting() throws Exception {
        String encoding = "Windows-31J";
        String name = new String("太郎".getBytes(encoding), StandardCharsets.ISO_8859_1);
        Model model = new ExtendedModelMap();
        String result = controller.greeting(name, encoding, model);
        assertThat(result).isEqualTo("greeting/greeting");
        assertThat(model.getAttribute("name")).isEqualTo("太郎");
    }

}
