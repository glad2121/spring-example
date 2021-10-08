package com.example.common.util;

import static com.example.common.util.WarekiResult.Status.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.YearMonth;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WarekiUtilsTest {

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testCheckWarekiY() {
        // 旧暦 → 新暦
        assertThat(WarekiUtils.checkWareki("M05"))
            .hasFieldOrPropertyWithValue("status", INVALID_DATE)
            .hasFieldOrPropertyWithValue("gregorian", null)
            .hasFieldOrPropertyWithValue("gregorianYear", null);
        assertThat(WarekiUtils.checkWareki("M06"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "1873")
            .hasFieldOrPropertyWithValue("gregorianYear", 1873);
        assertThat(WarekiUtils.checkWareki("M99"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "1966")
            .hasFieldOrPropertyWithValue("gregorianYear", 1966);
        assertThat(WarekiUtils.checkWareki("M999"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "2866")
            .hasFieldOrPropertyWithValue("gregorianYear", 2866);

        // 明治 → 大正
        assertThat(WarekiUtils.checkWareki("M45"))
            .hasFieldOrPropertyWithValue("status", LAST_YEAR)
            .hasFieldOrPropertyWithValue("gregorian", "1912")
            .hasFieldOrPropertyWithValue("gregorianYear", 1912);
        assertThat(WarekiUtils.checkWareki("T01"))
            .hasFieldOrPropertyWithValue("status", FIRST_YEAR)
            .hasFieldOrPropertyWithValue("gregorian", "1912")
            .hasFieldOrPropertyWithValue("gregorianYear", 1912);
        assertThat(WarekiUtils.checkWareki("T99"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "2010")
            .hasFieldOrPropertyWithValue("gregorianYear", 2010);
        assertThat(WarekiUtils.checkWareki("T999"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "2910")
            .hasFieldOrPropertyWithValue("gregorianYear", 2910);

        // 大正 → 昭和
        assertThat(WarekiUtils.checkWareki("T15"))
            .hasFieldOrPropertyWithValue("status", LAST_YEAR)
            .hasFieldOrPropertyWithValue("gregorian", "1926")
            .hasFieldOrPropertyWithValue("gregorianYear", 1926);
        assertThat(WarekiUtils.checkWareki("S01"))
            .hasFieldOrPropertyWithValue("status", FIRST_YEAR)
            .hasFieldOrPropertyWithValue("gregorian", "1926")
            .hasFieldOrPropertyWithValue("gregorianYear", 1926);
        assertThat(WarekiUtils.checkWareki("S99"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "2024")
            .hasFieldOrPropertyWithValue("gregorianYear", 2024);
        assertThat(WarekiUtils.checkWareki("S999"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "2924")
            .hasFieldOrPropertyWithValue("gregorianYear", 2924);

        // 昭和 → 平成
        assertThat(WarekiUtils.checkWareki("S64"))
            .hasFieldOrPropertyWithValue("status", LAST_YEAR)
            .hasFieldOrPropertyWithValue("gregorian", "1989")
            .hasFieldOrPropertyWithValue("gregorianYear", 1989);
        assertThat(WarekiUtils.checkWareki("H01"))
            .hasFieldOrPropertyWithValue("status", FIRST_YEAR)
            .hasFieldOrPropertyWithValue("gregorian", "1989")
            .hasFieldOrPropertyWithValue("gregorianYear", 1989);
        assertThat(WarekiUtils.checkWareki("H99"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "2087")
            .hasFieldOrPropertyWithValue("gregorianYear", 2087);
        assertThat(WarekiUtils.checkWareki("H999"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "2987")
            .hasFieldOrPropertyWithValue("gregorianYear", 2987);

        // 平成 → 令和
        assertThat(WarekiUtils.checkWareki("H30"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "2018")
            .hasFieldOrPropertyWithValue("gregorianYear", 2018);
        assertThat(WarekiUtils.checkWareki("H31"))
            .hasFieldOrPropertyWithValue("status", LAST_YEAR)
            .hasFieldOrPropertyWithValue("gregorian", "2019")
            .hasFieldOrPropertyWithValue("gregorianYear", 2019);
        assertThat(WarekiUtils.checkWareki("H32"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "2020")
            .hasFieldOrPropertyWithValue("gregorianYear", 2020);
        assertThat(WarekiUtils.checkWareki("R01"))
            .hasFieldOrPropertyWithValue("status", FIRST_YEAR)
            .hasFieldOrPropertyWithValue("gregorian", "2019")
            .hasFieldOrPropertyWithValue("gregorianYear", 2019);
        assertThat(WarekiUtils.checkWareki("R02"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "2020")
            .hasFieldOrPropertyWithValue("gregorianYear", 2020);
        assertThat(WarekiUtils.checkWareki("R99"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "2117")
            .hasFieldOrPropertyWithValue("gregorianYear", 2117);
        assertThat(WarekiUtils.checkWareki("R999"))
            .hasFieldOrPropertyWithValue("status", LAST_YEAR)
            .hasFieldOrPropertyWithValue("gregorian", "3017")
            .hasFieldOrPropertyWithValue("gregorianYear", 3017);
    }

    @Test
    void testCheckWarekiYM() {
        // 旧暦 → 新暦
        assertThat(WarekiUtils.checkWareki("M0512"))
            .hasFieldOrPropertyWithValue("status", INVALID_DATE)
            .hasFieldOrPropertyWithValue("gregorian", null)
            .hasFieldOrPropertyWithValue("yearMonth", null);
        assertThat(WarekiUtils.checkWareki("M0601"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "187301")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(1873, 1));
        assertThat(WarekiUtils.checkWareki("M9912"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "196612")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(1966, 12));
        assertThat(WarekiUtils.checkWareki("M99912"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "286612")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2866, 12));

        // 明治 → 大正
        assertThat(WarekiUtils.checkWareki("M4507"))
            .hasFieldOrPropertyWithValue("status", LAST_MONTH)
            .hasFieldOrPropertyWithValue("gregorian", "191207")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(1912, 7));
        assertThat(WarekiUtils.checkWareki("T0107"))
            .hasFieldOrPropertyWithValue("status", FIRST_MONTH)
            .hasFieldOrPropertyWithValue("gregorian", "191207")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(1912, 7));
        assertThat(WarekiUtils.checkWareki("T9912"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "201012")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2010, 12));
        assertThat(WarekiUtils.checkWareki("T99912"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "291012")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2910, 12));

        // 大正 → 昭和
        assertThat(WarekiUtils.checkWareki("T1512"))
            .hasFieldOrPropertyWithValue("status", LAST_MONTH)
            .hasFieldOrPropertyWithValue("gregorian", "192612")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(1926, 12));
        assertThat(WarekiUtils.checkWareki("S0112"))
            .hasFieldOrPropertyWithValue("status", FIRST_MONTH)
            .hasFieldOrPropertyWithValue("gregorian", "192612")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(1926, 12));
        assertThat(WarekiUtils.checkWareki("S9912"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "202412")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2024, 12));
        assertThat(WarekiUtils.checkWareki("S99912"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "292412")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2924, 12));

        // 昭和 → 平成
        assertThat(WarekiUtils.checkWareki("S6401"))
            .hasFieldOrPropertyWithValue("status", LAST_MONTH)
            .hasFieldOrPropertyWithValue("gregorian", "198901")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(1989, 1));
        assertThat(WarekiUtils.checkWareki("H0101"))
            .hasFieldOrPropertyWithValue("status", FIRST_MONTH)
            .hasFieldOrPropertyWithValue("gregorian", "198901")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(1989, 1));
        assertThat(WarekiUtils.checkWareki("H9912"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "208712")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2087, 12));
        assertThat(WarekiUtils.checkWareki("H99912"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "298712")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2987, 12));

        // 平成 → 令和
        assertThat(WarekiUtils.checkWareki("H3103"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "201903")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2019, 3));
        assertThat(WarekiUtils.checkWareki("H3104"))
            .hasFieldOrPropertyWithValue("status", LAST_MONTH)
            .hasFieldOrPropertyWithValue("gregorian", "201904")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2019, 4));
        assertThat(WarekiUtils.checkWareki("H3105"))
            .hasFieldOrPropertyWithValue("status", LAST_YEAR)
            .hasFieldOrPropertyWithValue("gregorian", "201905")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2019, 5));
        assertThat(WarekiUtils.checkWareki("H3201"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "202001")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2020, 1));
        assertThat(WarekiUtils.checkWareki("R0101"))
            .hasFieldOrPropertyWithValue("status", FIRST_YEAR)
            .hasFieldOrPropertyWithValue("gregorian", "201901")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2019, 1));
        assertThat(WarekiUtils.checkWareki("R0105"))
            .hasFieldOrPropertyWithValue("status", FIRST_MONTH)
            .hasFieldOrPropertyWithValue("gregorian", "201905")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2019, 5));
        assertThat(WarekiUtils.checkWareki("R0106"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "201906")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2019, 6));
        assertThat(WarekiUtils.checkWareki("R9912"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "211712")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2117, 12));
        assertThat(WarekiUtils.checkWareki("R99912"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "301712")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(3017, 12));
    }

    @Test
    void testCheckWarekiYMD() {
        // 旧暦 → 新暦
        assertThat(WarekiUtils.checkWareki("M051231"))
            .hasFieldOrPropertyWithValue("status", INVALID_DATE)
            .hasFieldOrPropertyWithValue("gregorian", null)
            .hasFieldOrPropertyWithValue("localDate", null);
        assertThat(WarekiUtils.checkWareki("M060101"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "18730101")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(1873, 1, 1));
        assertThat(WarekiUtils.checkWareki("M991231"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "19661231")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(1966, 12, 31));
        assertThat(WarekiUtils.checkWareki("M9991231"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "28661231")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2866, 12, 31));

        // 明治 → 大正
        assertThat(WarekiUtils.checkWareki("M450729"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "19120729")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(1912, 7, 29));
        assertThat(WarekiUtils.checkWareki("T010730"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "19120730")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(1912, 7, 30));
        assertThat(WarekiUtils.checkWareki("T991231"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "20101231")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2010, 12, 31));
        assertThat(WarekiUtils.checkWareki("T9991231"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "29101231")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2910, 12, 31));

        // 大正 → 昭和
        assertThat(WarekiUtils.checkWareki("T151224"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "19261224")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(1926, 12, 24));
        assertThat(WarekiUtils.checkWareki("S011225"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "19261225")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(1926, 12, 25));
        assertThat(WarekiUtils.checkWareki("S991231"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "20241231")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2024, 12, 31));
        assertThat(WarekiUtils.checkWareki("S9991231"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "29241231")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2924, 12, 31));

        // 昭和 → 平成
        assertThat(WarekiUtils.checkWareki("S640107"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "19890107")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(1989, 1, 7));
        assertThat(WarekiUtils.checkWareki("H010108"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "19890108")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(1989, 1, 8));
        assertThat(WarekiUtils.checkWareki("H991231"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "20871231")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2087, 12, 31));
        assertThat(WarekiUtils.checkWareki("H9991231"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "29871231")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2987, 12, 31));

        // 平成 → 令和
        assertThat(WarekiUtils.checkWareki("H310430"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "20190430")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2019, 4, 30));
        assertThat(WarekiUtils.checkWareki("H310501"))
            .hasFieldOrPropertyWithValue("status", LAST_YEAR)
            .hasFieldOrPropertyWithValue("gregorian", "20190501")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2019, 5, 1));
        assertThat(WarekiUtils.checkWareki("H320101"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "20200101")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2020, 1, 1));
        assertThat(WarekiUtils.checkWareki("R010101"))
            .hasFieldOrPropertyWithValue("status", FIRST_YEAR)
            .hasFieldOrPropertyWithValue("gregorian", "20190101")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2019, 1, 1));
        assertThat(WarekiUtils.checkWareki("R010501"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "20190501")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2019, 5, 1));
        assertThat(WarekiUtils.checkWareki("R991231"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "21171231")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2117, 12, 31));
        assertThat(WarekiUtils.checkWareki("R9991231"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "30171231")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(3017, 12, 31));
    }

    @Test
    void testGetWarekiYearY() {
        // 旧暦 → 新暦
        assertThat(WarekiUtils.getWarekiYear(1873, null)).hasToString("M06");
        assertThat(WarekiUtils.getWarekiYear(1873, "R")).hasToString("M06");
        assertThat(WarekiUtils.getWarekiYear(1873, "H")).hasToString("M06");
        assertThat(WarekiUtils.getWarekiYear(1873, "S")).hasToString("M06");
        assertThat(WarekiUtils.getWarekiYear(1873, "T")).hasToString("M06");
        assertThat(WarekiUtils.getWarekiYear(1873, "M")).hasToString("M06");

        // 明治 → 大正
        assertThat(WarekiUtils.getWarekiYear(1911, null)).hasToString("M44");
        assertThat(WarekiUtils.getWarekiYear(1912, null)).hasToString("T01");
        assertThat(WarekiUtils.getWarekiYear(1912, "R")).hasToString("T01");
        assertThat(WarekiUtils.getWarekiYear(1912, "H")).hasToString("T01");
        assertThat(WarekiUtils.getWarekiYear(1912, "S")).hasToString("T01");
        assertThat(WarekiUtils.getWarekiYear(1912, "T")).hasToString("T01");
        assertThat(WarekiUtils.getWarekiYear(1912, "M")).hasToString("M45");

        // 大正 → 昭和
        assertThat(WarekiUtils.getWarekiYear(1925, null)).hasToString("T14");
        assertThat(WarekiUtils.getWarekiYear(1926, null)).hasToString("S01");
        assertThat(WarekiUtils.getWarekiYear(1926, "R")).hasToString("S01");
        assertThat(WarekiUtils.getWarekiYear(1926, "H")).hasToString("S01");
        assertThat(WarekiUtils.getWarekiYear(1926, "S")).hasToString("S01");
        assertThat(WarekiUtils.getWarekiYear(1926, "T")).hasToString("T15");
        assertThat(WarekiUtils.getWarekiYear(1926, "M")).hasToString("M59");

        // 昭和 → 平成
        assertThat(WarekiUtils.getWarekiYear(1988, null)).hasToString("S63");
        assertThat(WarekiUtils.getWarekiYear(1989, null)).hasToString("H01");
        assertThat(WarekiUtils.getWarekiYear(1989, "R")).hasToString("H01");
        assertThat(WarekiUtils.getWarekiYear(1989, "H")).hasToString("H01");
        assertThat(WarekiUtils.getWarekiYear(1989, "S")).hasToString("S64");
        assertThat(WarekiUtils.getWarekiYear(1989, "T")).hasToString("T78");
        assertThat(WarekiUtils.getWarekiYear(1989, "M")).hasToString("M122");

        // 平成 → 令和
        assertThat(WarekiUtils.getWarekiYear(2018, null)).hasToString("H30");
        assertThat(WarekiUtils.getWarekiYear(2019, null)).hasToString("R01");
        assertThat(WarekiUtils.getWarekiYear(2019, "R")).hasToString("R01");
        assertThat(WarekiUtils.getWarekiYear(2019, "H")).hasToString("H31");
        assertThat(WarekiUtils.getWarekiYear(2019, "S")).hasToString("S94");
        assertThat(WarekiUtils.getWarekiYear(2019, "T")).hasToString("T108");
        assertThat(WarekiUtils.getWarekiYear(2019, "M")).hasToString("M152");
    }

    @Test
    void testGetWarekiYearYM() {
        // 旧暦 → 新暦
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1873, 1), null)).hasToString("M06");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1873, 1), "R")).hasToString("M06");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1873, 1), "H")).hasToString("M06");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1873, 1), "S")).hasToString("M06");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1873, 1), "T")).hasToString("M06");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1873, 1), "M")).hasToString("M06");

        // 明治 → 大正
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1912, 6), null)).hasToString("M45");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1912, 7), null)).hasToString("T01");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1912, 7), "R")).hasToString("T01");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1912, 7), "H")).hasToString("T01");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1912, 7), "S")).hasToString("T01");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1912, 7), "T")).hasToString("T01");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1912, 7), "M")).hasToString("M45");

        // 大正 → 昭和
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1926, 11), null)).hasToString("T15");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1926, 12), null)).hasToString("S01");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1926, 12), "R")).hasToString("S01");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1926, 12), "H")).hasToString("S01");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1926, 12), "S")).hasToString("S01");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1926, 12), "T")).hasToString("T15");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1926, 12), "M")).hasToString("M59");

        // 昭和 → 平成
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1988, 12), null)).hasToString("S63");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1989, 1), null)).hasToString("H01");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1989, 1), "R")).hasToString("H01");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1989, 1), "H")).hasToString("H01");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1989, 1), "S")).hasToString("S64");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1989, 1), "T")).hasToString("T78");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(1989, 1), "M")).hasToString("M122");

        // 平成 → 令和
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(2019, 4), null)).hasToString("H31");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(2019, 5), null)).hasToString("R01");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(2019, 5), "R")).hasToString("R01");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(2019, 5), "H")).hasToString("H31");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(2019, 5), "S")).hasToString("S94");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(2019, 5), "T")).hasToString("T108");
        assertThat(WarekiUtils.getWarekiYear(YearMonth.of(2019, 5), "M")).hasToString("M152");
    }

    @Test
    void testGetWarekiYearYMD() {
        // 旧暦 → 新暦
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1873, 1, 1), null)).hasToString("M06");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1873, 1, 1), "R")).hasToString("M06");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1873, 1, 1), "H")).hasToString("M06");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1873, 1, 1), "S")).hasToString("M06");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1873, 1, 1), "T")).hasToString("M06");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1873, 1, 1), "M")).hasToString("M06");

        // 明治 → 大正
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1912, 7, 29), null)).hasToString("M45");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1912, 7, 30), null)).hasToString("T01");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1912, 7, 30), "R")).hasToString("T01");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1912, 7, 30), "H")).hasToString("T01");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1912, 7, 30), "S")).hasToString("T01");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1912, 7, 30), "T")).hasToString("T01");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1912, 7, 30), "M")).hasToString("M45");

        // 大正 → 昭和
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1926, 12, 24), null)).hasToString("T15");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1926, 12, 25), null)).hasToString("S01");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1926, 12, 25), "R")).hasToString("S01");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1926, 12, 25), "H")).hasToString("S01");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1926, 12, 25), "S")).hasToString("S01");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1926, 12, 25), "T")).hasToString("T15");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1926, 12, 25), "M")).hasToString("M59");

        // 昭和 → 平成
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1989, 1, 7), null)).hasToString("S64");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1989, 1, 8), null)).hasToString("H01");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1989, 1, 8), "R")).hasToString("H01");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1989, 1, 8), "H")).hasToString("H01");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1989, 1, 8), "S")).hasToString("S64");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1989, 1, 8), "T")).hasToString("T78");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(1989, 1, 8), "M")).hasToString("M122");

        // 平成 → 令和
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(2019, 4, 30), null)).hasToString("H31");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(2019, 5, 1), null)).hasToString("R01");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(2019, 5, 1), "R")).hasToString("R01");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(2019, 5, 1), "H")).hasToString("H31");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(2019, 5, 1), "S")).hasToString("S94");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(2019, 5, 1), "T")).hasToString("T108");
        assertThat(WarekiUtils.getWarekiYear(LocalDate.of(2019, 5, 1), "M")).hasToString("M152");
    }

    @Test
    void testToWarekiY() {
        // 旧暦 → 新暦
        assertThat(WarekiUtils.toWareki("1873")).isEqualTo("M06");

        // 明治 → 大正
        assertThat(WarekiUtils.toWareki("1911")).isEqualTo("M44");
        assertThat(WarekiUtils.toWareki("1912")).isEqualTo("T01");

        // 大正 → 昭和
        assertThat(WarekiUtils.toWareki("1925")).isEqualTo("T14");
        assertThat(WarekiUtils.toWareki("1926")).isEqualTo("S01");

        // 昭和 → 平成
        assertThat(WarekiUtils.toWareki("1988")).isEqualTo("S63");
        assertThat(WarekiUtils.toWareki("1989")).isEqualTo("H01");

        // 平成 → 令和
        assertThat(WarekiUtils.toWareki("2018")).isEqualTo("H30");
        assertThat(WarekiUtils.toWareki("2019")).isEqualTo("R01");
        assertThat(WarekiUtils.toWareki("2020")).isEqualTo("R02");
        assertThat(WarekiUtils.toWareki("2117")).isEqualTo("R99");
        assertThat(WarekiUtils.toWareki("3017")).isEqualTo("R999");
    }

    @Test
    void testToWarekiYM() {
        // 旧暦 → 新暦
        assertThat(WarekiUtils.toWareki("187301")).isEqualTo("M0601");

        // 明治 → 大正
        assertThat(WarekiUtils.toWareki("191206")).isEqualTo("M4506");
        assertThat(WarekiUtils.toWareki("191207")).isEqualTo("T0107");

        // 大正 → 昭和
        assertThat(WarekiUtils.toWareki("192611")).isEqualTo("T1511");
        assertThat(WarekiUtils.toWareki("192612")).isEqualTo("S0112");

        // 昭和 → 平成
        assertThat(WarekiUtils.toWareki("198812")).isEqualTo("S6312");
        assertThat(WarekiUtils.toWareki("198901")).isEqualTo("H0101");

        // 平成 → 令和
        assertThat(WarekiUtils.toWareki("201904")).isEqualTo("H3104");
        assertThat(WarekiUtils.toWareki("201905")).isEqualTo("R0105");
        assertThat(WarekiUtils.toWareki("201906")).isEqualTo("R0106");
        assertThat(WarekiUtils.toWareki("211712")).isEqualTo("R9912");
        assertThat(WarekiUtils.toWareki("301712")).isEqualTo("R99912");
    }

    @Test
    void testToWarekiYMD() {
        // 旧暦 → 新暦
        assertThat(WarekiUtils.toWareki("18730101")).isEqualTo("M060101");

        // 明治 → 大正
        assertThat(WarekiUtils.toWareki("19120729")).isEqualTo("M450729");
        assertThat(WarekiUtils.toWareki("19120730")).isEqualTo("T010730");

        // 大正 → 昭和
        assertThat(WarekiUtils.toWareki("19261224")).isEqualTo("T151224");
        assertThat(WarekiUtils.toWareki("19261225")).isEqualTo("S011225");

        // 昭和 → 平成
        assertThat(WarekiUtils.toWareki("19890107")).isEqualTo("S640107");
        assertThat(WarekiUtils.toWareki("19890108")).isEqualTo("H010108");

        // 平成 → 令和
        assertThat(WarekiUtils.toWareki("20190430")).isEqualTo("H310430");
        assertThat(WarekiUtils.toWareki("20190501")).isEqualTo("R010501");
        assertThat(WarekiUtils.toWareki("20190502")).isEqualTo("R010502");
        assertThat(WarekiUtils.toWareki("21171231")).isEqualTo("R991231");
        assertThat(WarekiUtils.toWareki("30171231")).isEqualTo("R9991231");
    }

    @Test
    void testToWarekiTextY() {
        // 旧暦 → 新暦
        assertThat(WarekiUtils.toWarekiText("1873")).isEqualTo("M 6");

        // 明治 → 大正
        assertThat(WarekiUtils.toWarekiText("1911")).isEqualTo("M44");
        assertThat(WarekiUtils.toWarekiText("1912")).isEqualTo("T 1");

        // 大正 → 昭和
        assertThat(WarekiUtils.toWarekiText("1925")).isEqualTo("T14");
        assertThat(WarekiUtils.toWarekiText("1926")).isEqualTo("S 1");

        // 昭和 → 平成
        assertThat(WarekiUtils.toWarekiText("1988")).isEqualTo("S63");
        assertThat(WarekiUtils.toWarekiText("1989")).isEqualTo("H 1");

        // 平成 → 令和
        assertThat(WarekiUtils.toWarekiText("H30")).isEqualTo("H30");
        assertThat(WarekiUtils.toWarekiText("H31")).isEqualTo("H31");
        assertThat(WarekiUtils.toWarekiText("R01")).isEqualTo("R 1");
        assertThat(WarekiUtils.toWarekiText("R02")).isEqualTo("R 2");
        assertThat(WarekiUtils.toWarekiText("R99")).isEqualTo("R99");
        assertThat(WarekiUtils.toWarekiText("R999")).isEqualTo("R999");

        assertThat(WarekiUtils.toWarekiText("2018")).isEqualTo("H30");
        assertThat(WarekiUtils.toWarekiText("2019")).isEqualTo("R 1");
        assertThat(WarekiUtils.toWarekiText("2020")).isEqualTo("R 2");
        assertThat(WarekiUtils.toWarekiText("2117")).isEqualTo("R99");
        assertThat(WarekiUtils.toWarekiText("3017")).isEqualTo("R999");
    }

    @Test
    void testToWarekiTextYM() {
        // 旧暦 → 新暦
        assertThat(WarekiUtils.toWarekiText("187301")).isEqualTo("M 6. 1");

        // 明治 → 大正
        assertThat(WarekiUtils.toWarekiText("191206")).isEqualTo("M45. 6");
        assertThat(WarekiUtils.toWarekiText("191207")).isEqualTo("T 1. 7");

        // 大正 → 昭和
        assertThat(WarekiUtils.toWarekiText("192611")).isEqualTo("T15.11");
        assertThat(WarekiUtils.toWarekiText("192612")).isEqualTo("S 1.12");

        // 昭和 → 平成
        assertThat(WarekiUtils.toWarekiText("198812")).isEqualTo("S63.12");
        assertThat(WarekiUtils.toWarekiText("198901")).isEqualTo("H 1. 1");

        // 平成 → 令和
        assertThat(WarekiUtils.toWarekiText("H3104")).isEqualTo("H31. 4");
        assertThat(WarekiUtils.toWarekiText("H3105")).isEqualTo("H31. 5");
        assertThat(WarekiUtils.toWarekiText("R0105")).isEqualTo("R 1. 5");
        assertThat(WarekiUtils.toWarekiText("R0106")).isEqualTo("R 1. 6");
        assertThat(WarekiUtils.toWarekiText("R9912")).isEqualTo("R99.12");
        assertThat(WarekiUtils.toWarekiText("R99912")).isEqualTo("R999.12");

        assertThat(WarekiUtils.toWarekiText("201904")).isEqualTo("H31. 4");
        assertThat(WarekiUtils.toWarekiText("201905")).isEqualTo("R 1. 5");
        assertThat(WarekiUtils.toWarekiText("201906")).isEqualTo("R 1. 6");
        assertThat(WarekiUtils.toWarekiText("211712")).isEqualTo("R99.12");
        assertThat(WarekiUtils.toWarekiText("301712")).isEqualTo("R999.12");
    }

    @Test
    void testToWarekiTextYMD() {
        // 旧暦 → 新暦
        assertThat(WarekiUtils.toWarekiText("18730101")).isEqualTo("M 6. 1. 1");

        // 明治 → 大正
        assertThat(WarekiUtils.toWarekiText("19120729")).isEqualTo("M45. 7.29");
        assertThat(WarekiUtils.toWarekiText("19120730")).isEqualTo("T 1. 7.30");

        // 大正 → 昭和
        assertThat(WarekiUtils.toWarekiText("19261224")).isEqualTo("T15.12.24");
        assertThat(WarekiUtils.toWarekiText("19261225")).isEqualTo("S 1.12.25");

        // 昭和 → 平成
        assertThat(WarekiUtils.toWarekiText("19890107")).isEqualTo("S64. 1. 7");
        assertThat(WarekiUtils.toWarekiText("19890108")).isEqualTo("H 1. 1. 8");

        // 平成 → 令和
        assertThat(WarekiUtils.toWarekiText("H310430")).isEqualTo("H31. 4.30");
        assertThat(WarekiUtils.toWarekiText("H310501")).isEqualTo("H31. 5. 1");
        assertThat(WarekiUtils.toWarekiText("R010501")).isEqualTo("R 1. 5. 1");
        assertThat(WarekiUtils.toWarekiText("R010502")).isEqualTo("R 1. 5. 2");
        assertThat(WarekiUtils.toWarekiText("R991231")).isEqualTo("R99.12.31");
        assertThat(WarekiUtils.toWarekiText("R9991231")).isEqualTo("R999.12.31");

        assertThat(WarekiUtils.toWarekiText("20190430")).isEqualTo("H31. 4.30");
        assertThat(WarekiUtils.toWarekiText("20190501")).isEqualTo("R 1. 5. 1");
        assertThat(WarekiUtils.toWarekiText("20190502")).isEqualTo("R 1. 5. 2");
        assertThat(WarekiUtils.toWarekiText("21171231")).isEqualTo("R99.12.31");
        assertThat(WarekiUtils.toWarekiText("30171231")).isEqualTo("R999.12.31");
    }

}
