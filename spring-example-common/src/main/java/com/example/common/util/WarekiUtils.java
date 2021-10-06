package com.example.common.util;

import static com.example.common.util.WarekiResult.Status.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;

public class WarekiUtils {

    static final Map<String, WarekiRange> WAREKI_RANGE_MAP;
    static {
        Map<String, WarekiRange> map = new LinkedHashMap<>();
        map.put("R", new WarekiRange(2019, 1,  5,  1, 99, 99, 99));
        map.put("H", new WarekiRange(1989, 1,  1,  8, 31,  4, 30));
        map.put("S", new WarekiRange(1926, 1, 12, 25, 64,  1,  7));
        map.put("T", new WarekiRange(1912, 1,  7, 30, 15, 12, 24));
        map.put("M", new WarekiRange(1868, 6,  1,  1, 45,  7, 29));
        WAREKI_RANGE_MAP = Collections.unmodifiableMap(map);
    }

    static final WarekiResult ALL0_YEAR = new WarekiResult(ALL0, "0000", null);

    static final WarekiResult ALL0_YEAR_MONTH = new WarekiResult(ALL0, "000000", null);

    static final WarekiResult ALL0_RESULT = new WarekiResult(ALL0, "00000000", null);

    static final WarekiResult ALL9_YEAR = new WarekiResult(ALL9, "9999", null);

    static final WarekiResult ALL9_YEAR_MONTH = new WarekiResult(ALL9, "999999", null);

    static final WarekiResult ALL9_RESULT = new WarekiResult(ALL9, "99999999", null);

    static final WarekiResult INVALID_RESULT = new WarekiResult(INVALID, null, null);

    static final WarekiResult INVALID_ERA_RESULT = new WarekiResult(INVALID_ERA, null, null);

    static final WarekiResult INVALID_DATE_RESULT = new WarekiResult(INVALID_DATE, null, null);

    static final Pattern WAREKI_PATTERN =
            Pattern.compile("([A-Z])(\\d{2})(\\d{2})?(\\d{2})?");

    static final Pattern GREGORIAN_PATTERN =
            Pattern.compile("(\\d{4})(\\d{2})?(\\d{2})?");

    static final Pattern DATE_PATTERN =
            Pattern.compile("(?:([A-Z])(\\d{2})|(\\d{4}))(\\d{2})?(\\d{2})?");

    public static WarekiResult checkWareki(String wareki) {
        Matcher m = WAREKI_PATTERN.matcher(wareki);
        if (!m.matches()) {
            return INVALID_RESULT;
        }
        String era = m.group(1);
        int year = Integer.parseInt(m.group(2));
        if (m.group(3) == null) {
            return checkWareki(era, year);
        }
        int month = Integer.parseInt(m.group(3));
        if (m.group(4) == null) {
            return checkWareki(era, year, month);
        }
        int day = Integer.parseInt(m.group(4));
        return checkWareki(era, year, month, day);
    }

    public static WarekiResult checkWareki(String era, int year) {
        if (year == 0) {
            return ALL0_YEAR;
        }
        if (year == 99) {
            return ALL9_YEAR;
        }
        return checkWareki0(era, year, 1, 1).getYearResult();
    }

    public static WarekiResult checkWareki(String era, int year, int month) {
        if (year == 0 && month == 0) {
            return ALL0_YEAR_MONTH;
        }
        if (year == 99 && month == 99) {
            return ALL9_YEAR_MONTH;
        }
        return checkWareki0(era, year, month, 1).getYearMonthResult();
    }

    public static WarekiResult checkWareki(String era, int year, int month, int day) {
        if (year == 0 && month == 0 && day == 0) {
            return ALL0_RESULT;
        }
        if (year == 99 && month == 99 && day == 99) {
            return ALL9_RESULT;
        }
        return checkWareki0(era, year, month, day);
    }

    static WarekiResult checkWareki0(String era, int year, int month, int day) {
        WarekiRange range = WAREKI_RANGE_MAP.get(era);
        if (range == null) {
            return INVALID_ERA_RESULT;
        }
        if (year < range.firstYear) {
            return INVALID_DATE_RESULT;
        }
        int gYear = range.startYear + year - 1;
        try {
            LocalDate localDate = LocalDate.of(gYear, month, day);
            String gregorian = DateTimeFormatter.BASIC_ISO_DATE.format(localDate);
            if (year == range.firstYear) {
                if (month < range.firstMonth) {
                    return new WarekiResult(START_YEAR, gregorian, localDate);
                }
                if (month == range.firstMonth && day < range.firstDay) {
                    return new WarekiResult(START_MONTH, gregorian, localDate);
                }
            }
            if (year < range.lastYear
                    || (year == range.lastYear && (month < range.lastMonth
                    || (month == range.lastMonth && day <= range.lastDay)))) {
                return new WarekiResult(VALID, gregorian, localDate);
            }
            return new WarekiResult(EXCEEDED, gregorian, localDate);
        } catch (DateTimeException e) {
            return INVALID_DATE_RESULT;
        }
    }

    public static String toWareki(String gregorian) {
        Matcher m = GREGORIAN_PATTERN.matcher(gregorian);
        if (!m.matches()) {
            throw new DateTimeException(gregorian);
        }
        int gYear = Integer.parseInt(m.group(1));
        if (m.group(2) == null) {
            if (gYear == 0) {
                return "M00";
            }
            if (gYear == 99) {
                return "R00";
            }
            return toWareki(gYear);
        }
        int month = Integer.parseInt(m.group(2));
        if (m.group(3) == null) {
            if (gYear == 0 && month == 0) {
                return "M0000";
            }
            if (gYear == 99 && month == 99) {
                return "R9999";
            }
            return toWareki(YearMonth.of(gYear, month));
        }
        int day = Integer.parseInt(m.group(3));
        if (gYear == 0 && month == 0 && day == 0) {
            return "M000000";
        }
        if (gYear == 99 && month == 99 && day == 99) {
            return "R999999";
        }
        return toWareki(LocalDate.of(gYear, month, day));
    }

    public static String toWareki(int gYear) {
        for (var entry : WAREKI_RANGE_MAP.entrySet()) {
            String era = entry.getKey();
            WarekiRange range = entry.getValue();
            int year = gYear - range.startYear + 1;
            if (100 <= year) {
                break;
            }
            if (range.firstYear <= year) {
                return String.format("%s%02d", era, year);
            }
        }
        throw new DateTimeException(String.format("%04d", gYear));
    }

    public static String toWareki(YearMonth yearMonth) {
        int gYear = yearMonth.getYear();
        int month = yearMonth.getMonthValue();
        for (var entry : WAREKI_RANGE_MAP.entrySet()) {
            String era = entry.getKey();
            WarekiRange range = entry.getValue();
            int year = gYear - range.startYear + 1;
            if (100 <= year) {
                break;
            }
            if (range.firstYear < year
                    || (range.firstYear == year && range.firstMonth <= month)) {
                return String.format("%s%02d%02d", era, year, month);
            }
        }
        throw new DateTimeException(yearMonth.toString());
    }

    public static String toWareki(LocalDate localDate) {
        int gYear = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        for (var entry : WAREKI_RANGE_MAP.entrySet()) {
            String era = entry.getKey();
            WarekiRange range = entry.getValue();
            int year = gYear - range.startYear + 1;
            if (100 <= year) {
                break;
            }
            if (range.firstYear < year
                    || (range.firstYear == year && (range.firstMonth < month
                    || (range.firstMonth == month && range.firstDay <= day)))) {
                return String.format("%s%02d%02d%02d", era, year, month, day);
            }
        }
        throw new DateTimeException(localDate.toString());
    }

    public static String toWarekiText(String date) {
        Matcher m = DATE_PATTERN.matcher(date);
        if (!m.matches()) {
            throw new DateTimeException(date);
        }
        if (m.group(1) != null) {
            String era = m.group(1);
            int year = Integer.parseInt(m.group(2));
            if (m.group(4) == null) {
                if (year == 0) {
                    return "";
                }
                return String.format("%s%2d", era, year);
            }
            int month = Integer.parseInt(m.group(4));
            if (m.group(5) == null) {
                if (year == 0 && month == 0) {
                    return "";
                }
                return String.format("%s%2d.%2d", era, year, month);
            }
            int day = Integer.parseInt(m.group(5));
            if (year == 0 && month == 0 && day == 0) {
                return "";
            }
            return String.format("%s%2d.%2d.%2d", era, year, month, day);
        }
        int gYear = Integer.parseInt(m.group(3));
        if (m.group(4) == null) {
            if (gYear == 0) {
                return "";
            }
            if (gYear == 99) {
                return "R99";
            }
            return toWarekiText(gYear);
        }
        int month = Integer.parseInt(m.group(4));
        if (m.group(5) == null) {
            if (gYear == 0 && month == 0) {
                return "";
            }
            if (gYear == 99 && month == 99) {
                return "R99.99";
            }
            return toWarekiText(YearMonth.of(gYear, month));
        }
        int day = Integer.parseInt(m.group(5));
        if (gYear == 0 && month == 0 && day == 0) {
            return "";
        }
        if (gYear == 99 && month == 99 && day == 99) {
            return "R99.99.99";
        }
        return toWarekiText(LocalDate.of(gYear, month, day));
    }

    public static String toWarekiText(int gYear) {
        for (var entry : WAREKI_RANGE_MAP.entrySet()) {
            String era = entry.getKey();
            WarekiRange range = entry.getValue();
            int year = gYear - range.startYear + 1;
            if (100 <= year) {
                break;
            }
            if (range.firstYear <= year) {
                return String.format("%s%2d", era, year);
            }
        }
        throw new DateTimeException(String.format("%04d", gYear));
    }

    public static String toWarekiText(YearMonth yearMonth) {
        int gYear = yearMonth.getYear();
        int month = yearMonth.getMonthValue();
        for (var entry : WAREKI_RANGE_MAP.entrySet()) {
            String era = entry.getKey();
            WarekiRange range = entry.getValue();
            int year = gYear - range.startYear + 1;
            if (100 <= year) {
                break;
            }
            if (range.firstYear < year
                    || (range.firstYear == year && range.firstMonth <= month)) {
                return String.format("%s%2d.%2d", era, year, month);
            }
        }
        throw new DateTimeException(yearMonth.toString());
    }

    public static String toWarekiText(LocalDate localDate) {
        int gYear = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        for (var entry : WAREKI_RANGE_MAP.entrySet()) {
            String era = entry.getKey();
            WarekiRange range = entry.getValue();
            int year = gYear - range.startYear + 1;
            if (100 <= year) {
                break;
            }
            if (range.firstYear < year
                    || (range.firstYear == year && (range.firstMonth < month
                    || (range.firstMonth == month && range.firstDay <= day)))) {
                return String.format("%s%2d.%2d.%2d", era, year, month, day);
            }
        }
        throw new DateTimeException(localDate.toString());
    }

    @RequiredArgsConstructor
    static class WarekiRange {
        final int startYear;
        final int firstYear;
        final int firstMonth;
        final int firstDay;
        final int lastYear;
        final int lastMonth;
        final int lastDay;
    }

}
