package com.palyndrum.emergencyalert.common.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class CommonUtil {

    public static boolean patternMatches(String str, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(str)
                .matches();
    }
}
