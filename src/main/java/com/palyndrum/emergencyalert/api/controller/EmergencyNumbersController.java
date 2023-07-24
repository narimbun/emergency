package com.palyndrum.emergencyalert.api.controller;

import com.palyndrum.emergencyalert.api.payload.request.RecipientRq;
import com.palyndrum.emergencyalert.common.api.controller.BaseController;
import com.palyndrum.emergencyalert.common.api.exception.ResourceConflictException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceInvalidException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceNotFoundException;
import com.palyndrum.emergencyalert.common.api.payload.response.CommonRs;
import com.palyndrum.emergencyalert.service.EmergencyNumbersService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/emergency-numbers")
public class EmergencyNumbersController extends BaseController {

    private EmergencyNumbersService emergencyNumbersService;

    public EmergencyNumbersController(EmergencyNumbersService emergencyNumbersService) {
        this.emergencyNumbersService = emergencyNumbersService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addRecipient(@Valid @RequestBody RecipientRq bodyRq) throws ResourceInvalidException, ResourceConflictException {

        return ResponseEntity.ok(emergencyNumbersService.addNumber(bodyRq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> editRecipient(@PathVariable String id, @Valid @RequestBody RecipientRq bodyRq) throws ResourceNotFoundException, ResourceInvalidException, ResourceConflictException {

        return ResponseEntity.ok(emergencyNumbersService.editNumber(id, bodyRq));
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> recipientList(@RequestHeader(value = "Authorization") String accessToken) {

        return ResponseEntity.ok(emergencyNumbersService.numberList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonRs> deleteRecipient(@PathVariable String id) throws ResourceNotFoundException {

        emergencyNumbersService.deleteNumber(id);

        return ResponseEntity.ok(new CommonRs("success"));
    }
}
