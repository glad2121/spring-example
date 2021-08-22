package com.example.person.service.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Person {

    private String name;

    private Integer age;

}
