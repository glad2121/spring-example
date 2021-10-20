package com.example.person.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.common.util.CommonUtils;
import com.example.common.util.Page;
import com.example.person.service.model.Person;
import com.example.person.service.model.PersonCondition;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    final PersonRepository repository;

    public void execute(Person person) {
        log.info("execute: {}", person);
    }

    public Page<Person> find(PersonCondition condition) {
        LocalDate today = CommonUtils.today();
        var page = repository.find(condition);
        for (var person : page.getContent()) {
            person.setAge(CommonUtils.age(person.getBirthDate(), today));
        }
        return page;
    }

}
