package com.example.person.web;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.example.person.service.PersonService;
import com.example.person.service.model.Person;
import com.example.person.web.model.PersonForm;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock
    PersonService personService;

    @InjectMocks
    PersonController controller;

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testShowForm() {
        String result = controller.showForm(null);
        assertThat(result).isEqualTo("person/form");
    }

    @Test
    void testCheckPersonInfo() throws Exception {
        String encoding = "Windows-31J";
        String name = new String("太郎".getBytes(encoding), StandardCharsets.ISO_8859_1);
        PersonForm form = new PersonForm();
        form.setName(name);
        form.setAge(20);
        form.setEncoding(encoding);
        BindingResult bindingResult = new BeanPropertyBindingResult(form, "personForm");
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String result = controller.checkPersonInfo(form, bindingResult, redirectAttributes);
        assertThat(result).isEqualTo("redirect:/person/results");
        assertThat(bindingResult.hasErrors()).isFalse();
        Map<String, ?> flashAttributes = redirectAttributes.getFlashAttributes();
        assertThat(flashAttributes).hasSize(1);
        assertThat(flashAttributes.get("personForm")).isSameAs(form);
        assertThat(form.getName()).isEqualTo("太郎");

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personService, times(1)).execute(personCaptor.capture());
        Person person = personCaptor.getValue();
        assertThat(person.getName()).isEqualTo("太郎");
        assertThat(person.getAge()).isEqualTo(20);
    }

}
