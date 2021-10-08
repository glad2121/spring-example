package com.example.common.util;

import java.time.LocalDate;
import java.time.YearMonth;

import lombok.Value;

/**
 * 和暦のチェック結果。
 *
 * @author glad2121
 */
@Value
public class WarekiResult {

    /**
     * 結果ステータス。
     */
    private Status status;

    /**
     * 西暦 (年、年月、年月日) を表す文字列。
     */
    private String gregorian;

    /**
     * {@code LocalDate} オブジェクト。
     */
    private LocalDate localDate;

    /**
     * {@code YearMonth} オブジェクトを返します。
     *
     * @return {@code YearMonth} オブジェクト
     */
    public YearMonth getYearMonth() {
        return (localDate == null) ? null : YearMonth.from(localDate);
    }

    /**
     * 西暦年を返します。
     *
     * @return 西暦年
     */
    public Integer getGregorianYear() {
        return (localDate == null) ? null : localDate.getYear();
    }

    /**
     * 結果ステータス。
     */
    public enum Status {

        /** 元号の最初の年 (改元前の場合あり)。 */
        FIRST_YEAR,

        /** 元号の最初の月 (改元前の場合あり)。 */
        FIRST_MONTH,

        /** 妥当な日付。 */
        VALID,

        /** 元号の最後の月 (改元後の場合あり)。 */
        LAST_MONTH,

        /** 元号の最後の年 (改元後の場合あり)。 */
        LAST_YEAR,

        /** 改元後の通算日付。 */
        EXCEEDED,

        /** 不正な入力。 */
        INVALID,

        /** 不正な元号。 */
        INVALID_ERA,

        /** 不正な日付。 */
        INVALID_DATE;

        /**
         * 妥当な年月日か判定します。
         *
         * @return 妥当な年月日であれば {@code true}
         */
        public boolean isValidDate() {
            return this == VALID;
        }

        /**
         * 妥当な年月か判定します (開始月、終了月を許容)。
         *
         * @return 妥当な年月であれば {@code true}
         */
        public boolean isValidYearMonth() {
            return (FIRST_MONTH.compareTo(this) <= 0) && (this.compareTo(LAST_MONTH) <= 0);
        }

        /**
         * 妥当な年か判定します (開始年、終了年を許容)。
         *
         * @return 妥当な年であれば {@code true}
         */
        public boolean isValidYear() {
            return this.compareTo(LAST_YEAR) <= 0;
        }

        /**
         * 許容できる和暦か判定します (開始年、通算日付を許容)。
         *
         * @return 許容できる和暦であれば {@code true}
         */
        public boolean isAcceptable() {
            return this.compareTo(EXCEEDED) <= 0;
        }

        /**
         * 理由を問わず不正な入力か判定します。
         *
         * @return 不正な入力であれば {@code true}
         */
        public boolean isInvalid() {
            return INVALID.compareTo(this) <= 0;
        }

    }

}
