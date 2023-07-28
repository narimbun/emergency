package com.palyndrum.emergencyalert.service.impl;

import com.palyndrum.emergencyalert.entity.Institution;
import com.palyndrum.emergencyalert.repository.InstitutionRepository;
import com.palyndrum.emergencyalert.service.InstitutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class InstitutionServiceImpl implements InstitutionService {

    private InstitutionRepository institutionRepository;


    public InstitutionServiceImpl(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }


    @Override
    public List<Map<String, Object>> findNearest(String latitude, String longitude, String type, int size) {
        List<Institution> list = institutionRepository.findNearest(Double.parseDouble(latitude), Double.parseDouble(longitude), type, size);
        return list.stream()
                .map(Institution::forListResponse).toList();
    }
}
