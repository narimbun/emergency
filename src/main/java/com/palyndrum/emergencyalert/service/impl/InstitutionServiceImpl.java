package com.palyndrum.emergencyalert.service.impl;

import com.palyndrum.emergencyalert.repository.InstitutionRepository;
import com.palyndrum.emergencyalert.service.InstitutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class InstitutionServiceImpl implements InstitutionService {

    private InstitutionRepository institutionRepository;


    public InstitutionServiceImpl(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }


    @Override
    public List<Map<String, Object>> findNearest(String latitude, String longitude, String type, int size) {
        List<Object[]> list = institutionRepository.findNearest(Double.parseDouble(latitude), Double.parseDouble(longitude), type, size);

        List<Map<String, Object>> newList = new ArrayList<>();

        list.forEach(i -> {
            HashMap<String, Object> map = new LinkedHashMap<>();

            map.put("id", i[0]);
            map.put("name", i[1]);
            map.put("address", i[2]);
            map.put("phone", i[3]);
            map.put("distance", String.format("%.2f", i[4]));

            newList.add(map);
        });

        return newList;
    }
}
