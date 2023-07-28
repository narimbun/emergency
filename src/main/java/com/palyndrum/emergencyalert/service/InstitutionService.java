package com.palyndrum.emergencyalert.service;

import java.util.List;
import java.util.Map;

public interface InstitutionService {

    List<Map<String,Object>> findNearest(String latitude, String longitude, String type, int size);
}
