package com.example.common.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.springframework.util.ObjectUtils;

public class CommonUtils {

    public static String toHexString(String s) {
        return s.chars()
            .mapToObj(c -> String.format("%02X", c))
            .collect(Collectors.joining(" "));
    }

    public static String decode(String s, String encoding)
            throws UnsupportedEncodingException {
        if (ObjectUtils.isEmpty(encoding)) {
            encoding = "UTF-8";
        }
        return new String(s.getBytes(StandardCharsets.ISO_8859_1), encoding);
    }

}
