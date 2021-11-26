package com.example.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

public class CustomFunctions {

    public static String number(Object o, String pattern) {
        if (o == null || "".equals(o)) {
            return "";
        }
        NumberFormat f = new DecimalFormat(pattern);
        if (o instanceof String) {
            return f.format(new BigDecimal(String.class.cast(o)));
        }
        if (o instanceof Number) {
            return f.format(o);
        }
        return String.format("[%s]", o);
    }

    public static String warekiText(Object o) {
        if (o == null || "".equals(o)) {
            return "";
        }
        if (o instanceof String) {
            return WarekiUtils.toWarekiText(String.class.cast(o));
        }
        if (o instanceof LocalDate) {
            return WarekiUtils.toWarekiText(LocalDate.class.cast(o));
        }
        if (o instanceof YearMonth) {
            return WarekiUtils.toWarekiText(YearMonth.class.cast(o));
        }
        if (o instanceof Year) {
            return WarekiUtils.toWarekiText(Year.class.cast(o).getValue());
        }
        return String.format("[%s]", o);
    }

}
