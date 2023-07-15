package com.palyndrum.emergencyalert.common.util;

import lombok.experimental.UtilityClass;

import java.util.LinkedHashMap;
import java.util.Map;

@UtilityClass
public class PayloadBuilder {

    public static Map<String, Object> list(int page, int size, int totalPages, long totalElements, Object list) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("page", page);
        payload.put("size", size);
        payload.put("totalPages", totalPages);
        payload.put("totalItems", totalElements);
        payload.put("list", list);
        return payload;
    }
}
