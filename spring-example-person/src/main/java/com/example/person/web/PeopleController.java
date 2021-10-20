package com.example.person.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.common.util.Page;
import com.example.common.util.PageRequest;
import com.example.person.service.PersonService;
import com.example.person.service.model.Person;
import com.example.person.service.model.PersonCondition;
import com.example.person.web.model.PersonListModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
@Slf4j
public class PeopleController {

    final PersonService personService;

    @GetMapping
    public String list(PersonListModel personListModel) {
        PersonCondition condition = new PersonCondition();
        condition.setPageRequest(new PageRequest(20, 0));
        Page<Person> page = personService.find(condition);
        log.info("{}", page);
        personListModel.setList(page.getContent());
        return "people/list";
    }

}
