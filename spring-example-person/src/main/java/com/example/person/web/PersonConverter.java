package com.example.person.web;

import com.example.person.service.model.Person;
import com.example.person.web.model.PersonForm;

class PersonConverter {

    static Person toPerson(PersonForm form) {
        return new Person()
            .setName(form.getName())
            .setAge(form.getAge());
    }

}
