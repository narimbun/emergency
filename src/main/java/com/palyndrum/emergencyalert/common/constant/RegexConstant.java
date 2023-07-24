package com.palyndrum.emergencyalert.common.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RegexConstant {

    public static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public static final String PHONE_PATTERN = "^08[1-9][0-9]{6,9}$";
}
