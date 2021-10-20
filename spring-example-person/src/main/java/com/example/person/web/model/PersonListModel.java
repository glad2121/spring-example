package com.example.person.web.model;

import java.util.List;

import com.example.person.service.model.Person;

import lombok.Data;

@Data
public class PersonListModel {

    private List<Person> list;

}
