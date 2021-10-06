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
        assertThat(WarekiUtils.checkWareki("H31"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "2019")
            .hasFieldOrPropertyWithValue("gregorianYear", 2019);
        assertThat(WarekiUtils.checkWareki("R01"))
            .hasFieldOrPropertyWithValue("status", START_YEAR)
            .hasFieldOrPropertyWithValue("gregorian", "2019")
            .hasFieldOrPropertyWithValue("gregorianYear", 2019);
        assertThat(WarekiUtils.checkWareki("H32"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "2020")
            .hasFieldOrPropertyWithValue("gregorianYear", 2020);
        assertThat(WarekiUtils.checkWareki("R02"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "2020")
            .hasFieldOrPropertyWithValue("gregorianYear", 2020);
    }

    @Test
    void testCheckWarekiYM() {
        assertThat(WarekiUtils.checkWareki("R0101"))
            .hasFieldOrPropertyWithValue("status", START_YEAR)
            .hasFieldOrPropertyWithValue("gregorian", "201901")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2019, 1));
        assertThat(WarekiUtils.checkWareki("H3104"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "201904")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2019, 4));
        assertThat(WarekiUtils.checkWareki("H3105"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "201905")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2019, 5));
        assertThat(WarekiUtils.checkWareki("R0105"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "201905")
            .hasFieldOrPropertyWithValue("yearMonth", YearMonth.of(2019, 5));
    }

    @Test
    void testCheckWarekiYMD() {
        assertThat(WarekiUtils.checkWareki("R010101"))
            .hasFieldOrPropertyWithValue("status", START_YEAR)
            .hasFieldOrPropertyWithValue("gregorian", "20190101")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2019, 1, 1));
        assertThat(WarekiUtils.checkWareki("H310430"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "20190430")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2019, 4, 30));
        assertThat(WarekiUtils.checkWareki("H310501"))
            .hasFieldOrPropertyWithValue("status", EXCEEDED)
            .hasFieldOrPropertyWithValue("gregorian", "20190501")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2019, 5, 1));
        assertThat(WarekiUtils.checkWareki("R010501"))
            .hasFieldOrPropertyWithValue("status", VALID)
            .hasFieldOrPropertyWithValue("gregorian", "20190501")
            .hasFieldOrPropertyWithValue("localDate", LocalDate.of(2019, 5, 1));
    }

    @Test
    void testToWarekiY() {
        assertThat(WarekiUtils.toWareki("2018")).isEqualTo("H30");
        assertThat(WarekiUtils.toWareki("2019")).isEqualTo("R01");
        assertThat(WarekiUtils.toWareki("2020")).isEqualTo("R02");
    }

    @Test
    void testToWarekiYM() {
        assertThat(WarekiUtils.toWareki("201904")).isEqualTo("H3104");
        assertThat(WarekiUtils.toWareki("201905")).isEqualTo("R0105");
        assertThat(WarekiUtils.toWareki("201906")).isEqualTo("R0106");
    }

    @Test
    void testToWarekiYMD() {
        assertThat(WarekiUtils.toWareki("20190430")).isEqualTo("H310430");
        assertThat(WarekiUtils.toWareki("20190501")).isEqualTo("R010501");
        assertThat(WarekiUtils.toWareki("20190502")).isEqualTo("R010502");
    }

    @Test
    void testToWarekiTextY() {
        assertThat(WarekiUtils.toWarekiText("H30")).isEqualTo("H30");
        assertThat(WarekiUtils.toWarekiText("H31")).isEqualTo("H31");
        assertThat(WarekiUtils.toWarekiText("R01")).isEqualTo("R 1");
        assertThat(WarekiUtils.toWarekiText("R02")).isEqualTo("R 2");
        assertThat(WarekiUtils.toWarekiText("2018")).isEqualTo("H30");
        assertThat(WarekiUtils.toWarekiText("2019")).isEqualTo("R 1");
        assertThat(WarekiUtils.toWarekiText("2020")).isEqualTo("R 2");
    }

    @Test
    void testToWarekiTextYM() {
        assertThat(WarekiUtils.toWarekiText("H3104")).isEqualTo("H31. 4");
        assertThat(WarekiUtils.toWarekiText("H3105")).isEqualTo("H31. 5");
        assertThat(WarekiUtils.toWarekiText("R0105")).isEqualTo("R 1. 5");
        assertThat(WarekiUtils.toWarekiText("R0106")).isEqualTo("R 1. 6");
        assertThat(WarekiUtils.toWarekiText("R0112")).isEqualTo("R 1.12");
        assertThat(WarekiUtils.toWarekiText("201904")).isEqualTo("H31. 4");
        assertThat(WarekiUtils.toWarekiText("201905")).isEqualTo("R 1. 5");
        assertThat(WarekiUtils.toWarekiText("201906")).isEqualTo("R 1. 6");
        assertThat(WarekiUtils.toWarekiText("201912")).isEqualTo("R 1.12");
    }

    @Test
    void testToWarekiTextYMD() {
        assertThat(WarekiUtils.toWarekiText("H310430")).isEqualTo("H31. 4.30");
        assertThat(WarekiUtils.toWarekiText("H310501")).isEqualTo("H31. 5. 1");
        assertThat(WarekiUtils.toWarekiText("R010501")).isEqualTo("R 1. 5. 1");
        assertThat(WarekiUtils.toWarekiText("R010502")).isEqualTo("R 1. 5. 2");
        assertThat(WarekiUtils.toWarekiText("R011231")).isEqualTo("R 1.12.31");
        assertThat(WarekiUtils.toWarekiText("20190430")).isEqualTo("H31. 4.30");
        assertThat(WarekiUtils.toWarekiText("20190501")).isEqualTo("R 1. 5. 1");
        assertThat(WarekiUtils.toWarekiText("20190502")).isEqualTo("R 1. 5. 2");
        assertThat(WarekiUtils.toWarekiText("20191231")).isEqualTo("R 1.12.31");
    }

}
