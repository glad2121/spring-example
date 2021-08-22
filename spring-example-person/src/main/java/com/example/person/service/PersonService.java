package com.example.person.service;

import org.springframework.stereotype.Service;

import com.example.person.service.model.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PersonService {

    public void execute(Person person) {
        log.info("execute: {}", person);
    }

}
