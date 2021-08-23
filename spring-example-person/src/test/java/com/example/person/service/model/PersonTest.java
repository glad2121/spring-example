package com.example.person.service.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonTest {

    Person person;

    @BeforeEach
    void setUp() throws Exception {
        person = new Person()
            .setName("太郎")
            .setAge(20);
    }

    @AfterEach
    void tearDown() throws Exception {
        person = null;
    }

    @Test
    void testHashCode() {
        Person other = new Person()
            .setName("太郎")
            .setAge(20);
        assertThat(person.hashCode()).isEqualTo(other.hashCode());
    }

    @Test
    void testEquals() {
        Person other = new Person()
            .setName("太郎")
            .setAge(20);
        assertThat(person).isEqualTo(other);
    }

    @Test
    void testToString() {
        assertThat(person).hasToString("Person(name=太郎, age=20)");
    }

}
