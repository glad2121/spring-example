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

    public enum Status {
        FIRST_YEAR,
        FIRST_MONTH,
        VALID,
        LAST_MONTH,
        LAST_YEAR,
        EXCEEDED,
        INVALID,
        INVALID_ERA,
        INVALID_DATE;

        public boolean isValidDate() {
            return this == VALID;
        }

        public boolean isValidYearMonth() {
            return (FIRST_MONTH.compareTo(this) <= 0) && (this.compareTo(LAST_MONTH) <= 0);
        }

        public boolean isValidYear() {
            return (FIRST_YEAR.compareTo(this) <= 0) && (this.compareTo(LAST_YEAR) <= 0);
        }

        public boolean isAcceptable() {
            return this.compareTo(EXCEEDED) <= 0;
        }

        public boolean isInvalid() {
            return INVALID.compareTo(this) <= 0;
        }

    }

}
