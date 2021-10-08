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

/**
 * 和暦ユーティリティ。
 *
 * @author glad2121
 */
public class WarekiUtils {

    /**
     * 年の最大値。
     */
    static final int MAX_YEAR = 999;

    /**
     * 元号に対する日付の範囲のマップ。
     */
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

    /**
     * 和暦の正規表現パターン。
     */
    static final Pattern WAREKI_PATTERN =
            Pattern.compile("([A-Z])(\\d{2,3})(\\d{2})?(\\d{2})?");

    /**
     * 西暦の正規表現パターン。
     */
    static final Pattern GREGORIAN_PATTERN =
            Pattern.compile("(\\d{4})(\\d{2})?(\\d{2})?");

    /**
     * 和暦または西暦の正規表現パターン。
     */
    static final Pattern DATE_PATTERN =
            Pattern.compile("(?:([A-Z])(\\d{2,3})|(\\d{4}))(\\d{2})?(\\d{2})?");

    /**
     * 不正を表す結果。
     */
    static final WarekiResult INVALID_RESULT = new WarekiResult(INVALID, null, null);

    /**
     * 元号の不正を表す結果。
     */
    static final WarekiResult INVALID_ERA_RESULT = new WarekiResult(INVALID_ERA, null, null);

    /**
     * 日付の不正を表す結果。
     */
    static final WarekiResult INVALID_DATE_RESULT = new WarekiResult(INVALID_DATE, null, null);

    /**
     * 和暦 (年、年月、現月日) をチェックします。
     *
     * @param wareki 和暦文字列
     * @return チェック結果
     */
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

    /**
     * 和暦年をチェックします。
     *
     * @param era  元号
     * @param year 年
     * @return チェック結果
     */
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

    /**
     * 和暦年月をチェックします。
     *
     * @param era   元号
     * @param year  年
     * @param month 月
     * @return チェック結果
     */
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

    /**
     * 和暦年月日をチェックします。
     *
     * @param era   元号
     * @param year  年
     * @param month 月
     * @param day   日
     * @return チェック結果
     */
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

    /**
     * 和暦年を返します。
     *
     * @param gYear  西暦年
     * @param option 通算オプション
     * @return 和暦年
     */
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

    /**
     * 和暦年を返します。
     *
     * @param yearMonth 西暦年月
     * @param option 通算オプション
     * @return 和暦年
     */
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

    /**
     * 和暦年を返します。
     *
     * @param localDate 西暦年月日
     * @param option 通算オプション
     * @return 和暦年
     */
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

    /**
     * 西暦 (年、年月、現月日) を和暦に変換します。
     *
     * @param gregorian 西暦
     * @return 和暦
     */
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

    /**
     * 西暦年を和暦に変換します (年末基準)。
     *
     * @param gYear 西暦年
     * @return 和暦年
     */
    public static String toWareki(int gYear) {
        WarekiYear wYear = getWarekiYear(gYear, null);
        if (wYear == null) {
            throw new DateTimeException(String.format("%04d", gYear));
        }
        return String.format("%s%02d", wYear.getEra(), wYear.getYear());
    }

    /**
     * 西暦年月を和暦に変換します (月末基準)。
     *
     * @param yearMonth 西暦年月
     * @return 和暦年月
     */
    public static String toWareki(YearMonth yearMonth) {
        WarekiYear wYear = getWarekiYear(yearMonth, null);
        if (wYear == null) {
            throw new DateTimeException(yearMonth.toString());
        }
        return String.format("%s%02d%02d",
                wYear.getEra(), wYear.getYear(), yearMonth.getMonthValue());
    }

    /**
     * 西暦年月日を和暦に変換します。
     *
     * @param localDate 西暦年月日
     * @return 和暦年月日
     */
    public static String toWareki(LocalDate localDate) {
        WarekiYear wYear = getWarekiYear(localDate, null);
        if (wYear == null) {
            throw new DateTimeException(localDate.toString());
        }
        return String.format("%s%02d%02d%02d",
                wYear.getEra(), wYear.getYear(),
                localDate.getMonthValue(), localDate.getDayOfMonth());
    }

    /**
     * 和暦または西暦の日付を和暦テキストに変換します。
     *
     * @param date 日付
     * @return 和暦テキスト
     */
    public static String toWarekiText(String date) {
        Matcher m = DATE_PATTERN.matcher(date);
        if (!m.matches()) {
            throw new DateTimeException(date);
        }
        if (m.group(1) != null) {
            // 入力が和暦の場合。
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
        // 入力が西暦の場合。
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

    /**
     * 西暦年を和暦テキストに変換します。
     *
     * @param gYear 西暦年
     * @return 和暦テキスト
     */
    public static String toWarekiText(int gYear) {
        WarekiYear wYear = getWarekiYear(gYear, null);
        if (wYear == null) {
            throw new DateTimeException(String.format("%04d", gYear));
        }
        return String.format("%s%2d", wYear.getEra(), wYear.getYear());
    }

    /**
     * 西暦年月を和暦テキストに変換します。
     *
     * @param yearMonth 西暦年月
     * @return 和暦テキスト
     */
    public static String toWarekiText(YearMonth yearMonth) {
        WarekiYear wYear = getWarekiYear(yearMonth, null);
        if (wYear == null) {
            throw new DateTimeException(yearMonth.toString());
        }
        return String.format("%s%2d.%2d",
                wYear.getEra(), wYear.getYear(), yearMonth.getMonthValue());
    }

    /**
     * 西暦年月日を和暦テキストに変換します。
     *
     * @param localDate 西暦年月日
     * @return 和暦テキスト
     */
    public static String toWarekiText(LocalDate localDate) {
        WarekiYear wYear = getWarekiYear(localDate, null);
        if (wYear == null) {
            throw new DateTimeException(localDate.toString());
        }
        return String.format("%s%2d.%2d.%2d",
                wYear.getEra(), wYear.getYear(),
                localDate.getMonthValue(), localDate.getDayOfMonth());
    }

    /**
     * 元号に対する日付の範囲。
     */
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
