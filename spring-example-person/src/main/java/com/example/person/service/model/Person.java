package com.example.person.service.model;

import java.time.Instant;
import java.time.LocalDate;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Person {

    private Long id;

    private Long version;

    private String name;

    private LocalDate birthDate;

    private Integer age;

    private String genderCode;

    private String genderName;

    private String memo;

    private Instant createdAt;

    private String createdBy;

    private Instant updatedAt;

    private String updatedBy;

}
