package com.example.common.util;

import java.time.LocalDate;
import java.time.YearMonth;

import lombok.Value;

@Value
public class WarekiResult {

    private Status status;

    private String gregorian;

    private LocalDate localDate;

    public YearMonth getYearMonth() {
        return (localDate == null) ? null : YearMonth.from(localDate);
    }

    public Integer getGregorianYear() {
        return (localDate == null) ? null : localDate.getYear();
    }

    WarekiResult getYearResult() {
        return new WarekiResult(status, gregorian.substring(0, 4), localDate);
    }

    WarekiResult getYearMonthResult() {
        return new WarekiResult(status, gregorian.substring(0, 6), localDate);
    }

    public enum Status {
        VALID,
        EXCEEDED,
        START_MONTH,
        START_YEAR,
        ALL0,
        ALL9,
        INVALID,
        INVALID_ERA,
        INVALID_DATE
    }

}
