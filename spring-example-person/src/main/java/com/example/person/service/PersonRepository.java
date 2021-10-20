package com.example.person.service;

import com.example.common.util.Page;
import com.example.person.service.model.Person;
import com.example.person.service.model.PersonCondition;

public interface PersonRepository {

    Person create(Person person);

    Person update(Person person);

    void delete(Person person);

    Person getById(Long id);

    Page<Person> find(PersonCondition condition);

}
