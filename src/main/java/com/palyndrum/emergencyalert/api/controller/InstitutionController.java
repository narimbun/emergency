package com.palyndrum.emergencyalert.api.controller;

import com.palyndrum.emergencyalert.common.api.controller.BaseController;
import com.palyndrum.emergencyalert.service.InstitutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/institution")
public class InstitutionController extends BaseController {

    private InstitutionService institutionService;

    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> findNearest(@RequestParam("lat") String latitude,
                                                                 @RequestParam("long") String longitude,
                                                                 @RequestParam("type") String type,
                                                                 @RequestParam("size") int size) {
        return ResponseEntity.ok(institutionService.findNearest(latitude, longitude, type, size));
    }
}
