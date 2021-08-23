package com.example.person.web.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonFormTest {

    PersonForm form;

    @BeforeEach
    void setUp() throws Exception {
        form = new PersonForm();
        form.setName("太郎");
        form.setAge(20);
        form.setEncoding("UTF-8");
    }

    @AfterEach
    void tearDown() throws Exception {
        form = null;
    }

    @Test
    void testHashCode() {
        PersonForm other = new PersonForm();
        other.setName("太郎");
        other.setAge(20);
        other.setEncoding("UTF-8");
        assertThat(form.hashCode()).isEqualTo(other.hashCode());
    }

    @Test
    void testEquals() {
        PersonForm other = new PersonForm();
        other.setName("太郎");
        other.setAge(20);
        other.setEncoding("UTF-8");
        assertThat(form).isEqualTo(other);
    }

    @Test
    void testToString() {
        assertThat(form).hasToString("PersonForm(name=太郎, age=20, encoding=UTF-8)");
    }

}
