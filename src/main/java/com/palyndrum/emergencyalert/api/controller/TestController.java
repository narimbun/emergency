package com.palyndrum.emergencyalert.api.controller;

import com.palyndrum.emergencyalert.api.payload.request.TestRq;
import com.palyndrum.emergencyalert.entity.Test;
import com.palyndrum.emergencyalert.repository.TestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    private TestRepository testRepository;

    public TestController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<Test> addNewData(@RequestBody TestRq bodyRq) {

        Test test = new Test();

        test.setTex1(bodyRq.getTex1());
        test.setTex2(bodyRq.getTex2());
        test.setTex3(bodyRq.getTex3());

        test = testRepository.save(test);


        return ResponseEntity.ok(test);

    }

    @GetMapping("/find")
    public ResponseEntity<List<Test>> getAllData() {


        List<Test> list = testRepository.findAll();


        return ResponseEntity.ok(list);

    }
}
