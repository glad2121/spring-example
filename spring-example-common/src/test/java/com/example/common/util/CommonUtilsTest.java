package com.example.common.util;

import static org.assertj.core.api.Assertions.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommonUtilsTest {

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testToHexString() {
        String s = "Aa1ｶﾞあア亜〜～①彅𠮟";
        String result = CommonUtils.toHexString(s);
        assertThat(result)
            .isEqualTo("41 61 31 FF76 FF9E 3042 30A2 4E9C 301C FF5E 2460 5F45 D842 DF9F");
    }

    @Test
    void testDecode() throws Exception {
        String s = "Aa1ｶﾞあア亜～①彅";
        String encoding = "Windows-31J";
        String latin1 = new String(s.getBytes(encoding), StandardCharsets.ISO_8859_1);
        System.out.println(CommonUtils.toHexString(latin1));
        assertThat(CommonUtils.toHexString(latin1))
            .isEqualTo("41 61 31 B6 DE 82 A0 83 41 88 9F 81 60 87 40 FA 67");

        String decoded = CommonUtils.decode(latin1, encoding);
        assertThat(CommonUtils.toHexString(decoded))
            .isEqualTo("41 61 31 FF76 FF9E 3042 30A2 4E9C FF5E 2460 5F45");
        assertThat(decoded).isEqualTo(s);
    }

}
