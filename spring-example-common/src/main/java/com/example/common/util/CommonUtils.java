package com.example.common.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.util.ObjectUtils;
import org.springframework.web.util.HtmlUtils;

public class CommonUtils {

    public static String toHexString(String input) {
        return input.chars()
            .mapToObj(c -> String.format("%02X", c))
            .collect(Collectors.joining(" "));
    }

    public static String decode(String input, String encoding)
            throws UnsupportedEncodingException {
        if (ObjectUtils.isEmpty(encoding)) {
            encoding = "UTF-8";
        }
        return new String(input.getBytes(StandardCharsets.ISO_8859_1), encoding);
    }

    public static String htmlUnescape(String input) {
        return HtmlUtils.htmlUnescape(unescapeNumericCharRefs(input));
    }

    static final Pattern NUMERIC_CHAR_REF_PATTERN = Pattern.compile("&#(\\d+);");

    static String unescapeNumericCharRefs(String input) {
        return NUMERIC_CHAR_REF_PATTERN.matcher(input)
                .replaceAll(mr -> codePointsToString(Integer.parseInt(mr.group(1))));
    }

    static String codePointsToString(int... codePoints) {
        return new String(codePoints, 0, codePoints.length);
    }

}
