package com.palyndrum.emergencyalert.api.controller;

import com.palyndrum.emergencyalert.common.api.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/send-message")
public class SendEmergencyMessageController extends BaseController {
}
