package com.example.common.util;

import static com.example.common.util.WarekiResult.Status.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;

public class WarekiUtils {

    static final int MAX_YEAR = 999;

    static final Map<String, WarekiRange> WAREKI_RANGE_MAP;
    static {
        Map<String, WarekiRange> map = new LinkedHashMap<>();
        map.put("R", new WarekiRange(2019, 1,  5,  1, 999, 99, 99));
        map.put("H", new WarekiRange(1989, 1,  1,  8,  31,  4, 30));
        map.put("S", new WarekiRange(1926, 1, 12, 25,  64,  1,  7));
        map.put("T", new WarekiRange(1912, 1,  7, 30,  15, 12, 24));
        map.put("M", new WarekiRange(1868, 6,  1,  1,  45,  7, 29));
        WAREKI_RANGE_MAP = Collections.unmodifiableMap(map);
    }

    static final Pattern WAREKI_PATTERN =
            Pattern.compile("([A-Z])(\\d{2,3})(\\d{2})?(\\d{2})?");

    static final Pattern GREGORIAN_PATTERN =
            Pattern.compile("(\\d{4})(\\d{2})?(\\d{2})?");

    static final Pattern DATE_PATTERN =
            Pattern.compile("(?:([A-Z])(\\d{2,3})|(\\d{4}))(\\d{2})?(\\d{2})?");

    static final WarekiResult INVALID_RESULT = new WarekiResult(INVALID, null, null);

    static final WarekiResult INVALID_ERA_RESULT = new WarekiResult(INVALID_ERA, null, null);

    static final WarekiResult INVALID_DATE_RESULT = new WarekiResult(INVALID_DATE, null, null);

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
        WarekiRange range = WAREKI_RANGE_MAP.get(era);
        if (range == null) {
            return INVALID_ERA_RESULT;
        }
        if (year < range.firstYear) {
            return INVALID_DATE_RESULT;
        }
        int gYear = range.baseYear + year - 1;
        try {
            LocalDate localDate = LocalDate.of(gYear, 1, 1);
            String gregorian = String.format("%04d", gYear);
            if (year == 1) {
                return new WarekiResult(FIRST_YEAR, gregorian, localDate);
            }
            if (year < range.lastYear) {
                return new WarekiResult(VALID, gregorian, localDate);
            }
            if (year == range.lastYear) {
                return new WarekiResult(LAST_YEAR, gregorian, localDate);
            }
            return new WarekiResult(EXCEEDED, gregorian, localDate);
        } catch (DateTimeException e) {
            return INVALID_DATE_RESULT;
        }
    }

    public static WarekiResult checkWareki(String era, int year, int month) {
        WarekiRange range = WAREKI_RANGE_MAP.get(era);
        if (range == null) {
            return INVALID_ERA_RESULT;
        }
        if (year < range.firstYear) {
            return INVALID_DATE_RESULT;
        }
        int gYear = range.baseYear + year - 1;
        try {
            LocalDate localDate = LocalDate.of(gYear, month, 1);
            String gregorian = String.format("%04d%02d", gYear, month);
            if (year == 1) {
                if (month < range.firstMonth) {
                    return new WarekiResult(FIRST_YEAR, gregorian, localDate);
                }
                if (month == range.firstMonth) {
                    return new WarekiResult(FIRST_MONTH, gregorian, localDate);
                }
            }
            if (year < range.lastYear) {
                return new WarekiResult(VALID, gregorian, localDate);
            }
            if (year == range.lastYear) {
                if (month < range.lastMonth) {
                    return new WarekiResult(VALID, gregorian, localDate);
                }
                if (month == range.lastMonth) {
                    return new WarekiResult(LAST_MONTH, gregorian, localDate);
                }
                return new WarekiResult(LAST_YEAR, gregorian, localDate);
            }
            return new WarekiResult(EXCEEDED, gregorian, localDate);
        } catch (DateTimeException e) {
            return INVALID_DATE_RESULT;
        }
    }

    public static WarekiResult checkWareki(String era, int year, int month, int day) {
        WarekiRange range = WAREKI_RANGE_MAP.get(era);
        if (range == null) {
            return INVALID_ERA_RESULT;
        }
        if (year < range.firstYear) {
            return INVALID_DATE_RESULT;
        }
        int gYear = range.baseYear + year - 1;
        try {
            LocalDate localDate = LocalDate.of(gYear, month, day);
            String gregorian = String.format("%04d%02d%02d", gYear, month, day);
            if (year == 1) {
                if (month < range.firstMonth) {
                    return new WarekiResult(FIRST_YEAR, gregorian, localDate);
                }
                if (month == range.firstMonth && day < range.firstDay) {
                    return new WarekiResult(FIRST_MONTH, gregorian, localDate);
                }
            }
            if (year < range.lastYear) {
                return new WarekiResult(VALID, gregorian, localDate);
            }
            if (year == range.lastYear) {
                if (month < range.lastMonth) {
                    return new WarekiResult(VALID, gregorian, localDate);
                }
                if (month == range.lastMonth) {
                    if (day <= range.lastDay) {
                        return new WarekiResult(VALID, gregorian, localDate);
                    }
                    return new WarekiResult(LAST_MONTH, gregorian, localDate);
                }
                return new WarekiResult(LAST_YEAR, gregorian, localDate);
            }
            return new WarekiResult(EXCEEDED, gregorian, localDate);
        } catch (DateTimeException e) {
            return INVALID_DATE_RESULT;
        }
    }

    public static WarekiYear getWarekiYear(int gYear, String option) {
        if (option != null && !WAREKI_RANGE_MAP.containsKey(option)) {
            throw new DateTimeException("illegal option: " + option);
        }
        for (var entry : WAREKI_RANGE_MAP.entrySet()) {
            String era = entry.getKey();
            if (option != null) {
                if (!option.equals(era)) {
                    continue;
                }
                option = null;
            }
            WarekiRange range = entry.getValue();
            int year = gYear - range.baseYear + 1;
            if (MAX_YEAR < year) {
                break;
            }
            if (range.firstYear <= year) {
                return new WarekiYear(era, year);
            }
        }
        return null;
    }

    public static WarekiYear getWarekiYear(YearMonth yearMonth, String option) {
        if (option != null && !WAREKI_RANGE_MAP.containsKey(option)) {
            throw new DateTimeException("illegal option: " + option);
        }
        int gYear = yearMonth.getYear();
        int month = yearMonth.getMonthValue();
        for (var entry : WAREKI_RANGE_MAP.entrySet()) {
            String era = entry.getKey();
            if (option != null) {
                if (!option.equals(era)) {
                    continue;
                }
                option = null;
            }
            WarekiRange range = entry.getValue();
            int year = gYear - range.baseYear + 1;
            if (MAX_YEAR < year) {
                break;
            }
            if (range.firstYear < year
                    || (range.firstYear == year && range.firstMonth <= month)) {
                return new WarekiYear(era, year);
            }
        }
        return null;
    }

    public static WarekiYear getWarekiYear(LocalDate localDate, String option) {
        if (option != null && !WAREKI_RANGE_MAP.containsKey(option)) {
            throw new DateTimeException("illegal option: " + option);
        }
        int gYear = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        for (var entry : WAREKI_RANGE_MAP.entrySet()) {
            String era = entry.getKey();
            if (option != null) {
                if (!option.equals(era)) {
                    continue;
                }
                option = null;
            }
            WarekiRange range = entry.getValue();
            int year = gYear - range.baseYear + 1;
            if (MAX_YEAR < year) {
                break;
            }
            if (range.firstYear < year
                    || (range.firstYear == year && (range.firstMonth < month
                    || (range.firstMonth == month && range.firstDay <= day)))) {
                return new WarekiYear(era, year);
            }
        }
        return null;
    }

    public static String toWareki(String gregorian) {
        Matcher m = GREGORIAN_PATTERN.matcher(gregorian);
        if (!m.matches()) {
            throw new DateTimeException(gregorian);
        }
        int gYear = Integer.parseInt(m.group(1));
        if (m.group(2) == null) {
            return toWareki(gYear);
        }
        int month = Integer.parseInt(m.group(2));
        if (m.group(3) == null) {
            return toWareki(YearMonth.of(gYear, month));
        }
        int day = Integer.parseInt(m.group(3));
        return toWareki(LocalDate.of(gYear, month, day));
    }

    public static String toWareki(int gYear) {
        WarekiYear wYear = getWarekiYear(gYear, null);
        if (wYear == null) {
            throw new DateTimeException(String.format("%04d", gYear));
        }
        return String.format("%s%02d", wYear.getEra(), wYear.getYear());
    }

    public static String toWareki(YearMonth yearMonth) {
        WarekiYear wYear = getWarekiYear(yearMonth, null);
        if (wYear == null) {
            throw new DateTimeException(yearMonth.toString());
        }
        return String.format("%s%02d%02d",
                wYear.getEra(), wYear.getYear(), yearMonth.getMonthValue());
    }

    public static String toWareki(LocalDate localDate) {
        WarekiYear wYear = getWarekiYear(localDate, null);
        if (wYear == null) {
            throw new DateTimeException(localDate.toString());
        }
        return String.format("%s%02d%02d%02d",
                wYear.getEra(), wYear.getYear(),
                localDate.getMonthValue(), localDate.getDayOfMonth());
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
                return String.format("%s%2d", era, year);
            }
            int month = Integer.parseInt(m.group(4));
            if (m.group(5) == null) {
                return String.format("%s%2d.%2d", era, year, month);
            }
            int day = Integer.parseInt(m.group(5));
            return String.format("%s%2d.%2d.%2d", era, year, month, day);
        }
        int gYear = Integer.parseInt(m.group(3));
        if (m.group(4) == null) {
            return toWarekiText(gYear);
        }
        int month = Integer.parseInt(m.group(4));
        if (m.group(5) == null) {
            return toWarekiText(YearMonth.of(gYear, month));
        }
        int day = Integer.parseInt(m.group(5));
        return toWarekiText(LocalDate.of(gYear, month, day));
    }

    public static String toWarekiText(int gYear) {
        WarekiYear wYear = getWarekiYear(gYear, null);
        if (wYear == null) {
            throw new DateTimeException(String.format("%04d", gYear));
        }
        return String.format("%s%2d", wYear.getEra(), wYear.getYear());
    }

    public static String toWarekiText(YearMonth yearMonth) {
        WarekiYear wYear = getWarekiYear(yearMonth, null);
        if (wYear == null) {
            throw new DateTimeException(yearMonth.toString());
        }
        return String.format("%s%2d.%2d",
                wYear.getEra(), wYear.getYear(), yearMonth.getMonthValue());
    }

    public static String toWarekiText(LocalDate localDate) {
        WarekiYear wYear = getWarekiYear(localDate, null);
        if (wYear == null) {
            throw new DateTimeException(localDate.toString());
        }
        return String.format("%s%2d.%2d.%2d",
                wYear.getEra(), wYear.getYear(),
                localDate.getMonthValue(), localDate.getDayOfMonth());
    }

    @RequiredArgsConstructor
    static class WarekiRange {
        final int baseYear;
        final int firstYear;
        final int firstMonth;
        final int firstDay;
        final int lastYear;
        final int lastMonth;
        final int lastDay;
    }

}
