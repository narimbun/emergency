package com.palyndrum.emergencyalert.service.impl;

import com.palyndrum.emergencyalert.common.api.exception.ResourceNotFoundException;
import com.palyndrum.emergencyalert.common.constant.CodeConfigConstant;
import com.palyndrum.emergencyalert.repository.ApplicationConfigRepository;
import com.palyndrum.emergencyalert.service.WhatsAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class WhatsAppServiceImpl implements WhatsAppService {

    private ApplicationConfigRepository applicationConfigRepository;
    private RestTemplate restTemplate;


    public WhatsAppServiceImpl(ApplicationConfigRepository applicationConfigRepository, RestTemplate restTemplate) {
        this.applicationConfigRepository = applicationConfigRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendText(String text, String destination) throws ResourceNotFoundException {

        String waEndpoint = applicationConfigRepository.findById(CodeConfigConstant.RAPIWHA_ENDPOINT)
                .orElseThrow(() -> ResourceNotFoundException.create(String.format("Config '%s' doesn't exist.", CodeConfigConstant.RAPIWHA_ENDPOINT))).getValue();

        String apiKey = applicationConfigRepository.findById(CodeConfigConstant.RAPIWHA_APIKEY)
                .orElseThrow(() -> ResourceNotFoundException.create(String.format("Config '%s' doesn't exist.", CodeConfigConstant.RAPIWHA_APIKEY))).getValue();

        String endpoint = String.format(waEndpoint, destination.replaceFirst("0", "62"), text);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("apikey", apiKey);
        map.add("number", destination.replaceFirst("0", "62"));
        map.add("text", text);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        /*log.info(endpoint);
        log.info(text);*/

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(endpoint, httpEntity, String.class);
        log.info(responseEntity.toString());


    }
}
