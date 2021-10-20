package com.example.person.service.model;

import java.time.LocalDate;
import java.util.List;

import com.example.common.util.PageRequest;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PersonCondition {

    private List<Long> idIn;

    private String nameStartsWith;

    private LocalDate birthDateFrom;

    private LocalDate birthDateTo;

    private String genderCode;

    private PageRequest pageRequest;

}
