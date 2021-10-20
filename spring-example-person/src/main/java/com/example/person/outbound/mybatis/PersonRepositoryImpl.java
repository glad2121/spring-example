package com.example.person.outbound.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.common.util.Page;
import com.example.person.service.PersonRepository;
import com.example.person.service.model.Person;
import com.example.person.service.model.PersonCondition;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    @Autowired
    PersonDao dao;

    @Override
    public Person create(Person person) {
        dao.create(person);
        return dao.getById(person.getId());
    }

    @Override
    public Person update(Person person) {
        dao.update(person);
        return dao.getById(person.getId());
    }

    @Override
    public void delete(Person person) {
        dao.delete(person);
    }

    @Override
    public Person getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public Page<Person> find(PersonCondition condition) {
        List<Person> list = dao.find(condition);
        return new Page<>(condition.getPageRequest(), list);
    }

}
