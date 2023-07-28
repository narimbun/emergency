package com.palyndrum.emergencyalert.service.impl;

import com.palyndrum.emergencyalert.api.payload.request.EmergencyRq;
import com.palyndrum.emergencyalert.common.api.exception.ResourceForbiddenException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceNotFoundException;
import com.palyndrum.emergencyalert.common.constant.CodeConfigConstant;
import com.palyndrum.emergencyalert.common.model.CurrentUser;
import com.palyndrum.emergencyalert.entity.ApplicationConfig;
import com.palyndrum.emergencyalert.entity.EmergencyNumbers;
import com.palyndrum.emergencyalert.entity.User;
import com.palyndrum.emergencyalert.repository.ApplicationConfigRepository;
import com.palyndrum.emergencyalert.repository.EmergencyNumbersRepository;
import com.palyndrum.emergencyalert.repository.UserRepository;
import com.palyndrum.emergencyalert.service.SendMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SendMessageServiceImpl implements SendMessageService {

    private CurrentUser currentUser;
    private UserRepository userRepository;
    private ApplicationConfigRepository applicationConfigRepository;
    private EmergencyNumbersRepository emergencyNumbersRepository;
    private RestTemplate restTemplate;

    @Value("${wa.gateway.endpoint}")
    private String waEndpoint;

    @Value("${googlemap.link}")
    private String googleMapLink;

    public SendMessageServiceImpl(CurrentUser currentUser, UserRepository userRepository, ApplicationConfigRepository applicationConfigRepository,
                                  EmergencyNumbersRepository emergencyNumbersRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.applicationConfigRepository = applicationConfigRepository;
        this.currentUser = currentUser;
        this.emergencyNumbersRepository = emergencyNumbersRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendMessage(EmergencyRq bodyRq) throws ResourceNotFoundException, ResourceForbiddenException {

        Optional<User> user = userRepository.findById(currentUser.getId());

        if (user.isEmpty())
            throw ResourceNotFoundException.create("User tidak ditemukan.");

        if (!user.get().isVerified())
            throw ResourceForbiddenException.create("Nomor Anda belum ter-verfikasi. Silahkan lakukan verifikasi.");

        Optional<ApplicationConfig> applicationConfig = applicationConfigRepository.findById(CodeConfigConstant.EMERGENCY_MESSAGE);

        if (applicationConfig.isEmpty())
            throw ResourceNotFoundException.create("Default template pesan tidak ditemukan.");

        List<EmergencyNumbers> listNumber = emergencyNumbersRepository.findByUserId(user.get().getId());


        String linkMap = googleMapLink.replace("{latitude}", bodyRq.getLatitude()).replace("{longitude}", bodyRq.getLongitude());

        if (!listNumber.isEmpty()) {
            listNumber.forEach(i -> {
                Thread newThread = new Thread(() -> {
                    String templateMessage = applicationConfig.get().getValue();
                    String message = String.format(templateMessage, i.getName(), user.get().getName(), user.get().getPhone(), linkMap);
                    String endpoint = String.format(waEndpoint, i.getPhone().replaceFirst("0", "62"), message);

                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(endpoint, String.class);

                    log.info(responseEntity.toString());
                });
                newThread.start();
            });
        }
    }
}
