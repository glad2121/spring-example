package com.example.common.util;

import lombok.Value;

@Value
public class WarekiYear {

    private String era;

    private int year;

    @Override
    public String toString() {
        return String.format("%s%02d", era, year);
    }

}
