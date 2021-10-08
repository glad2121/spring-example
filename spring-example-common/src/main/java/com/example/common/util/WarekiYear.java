package com.example.common.util;

import lombok.Value;

/**
 * 和暦年。
 *
 * @author glad2121
 */
@Value
public class WarekiYear {

    /**
     * 元号。
     */
    private String era;

    /**
     * 年。
     */
    private int year;

    /**
     * 文字列表現を返します。
     */
    @Override
    public String toString() {
        return String.format("%s%02d", era, year);
    }

}
