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
import com.palyndrum.emergencyalert.service.InstitutionService;
import com.palyndrum.emergencyalert.service.SendMessageService;
import com.palyndrum.emergencyalert.service.WhatsAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class SendMessageServiceImpl implements SendMessageService {

    private CurrentUser currentUser;
    private UserRepository userRepository;
    private ApplicationConfigRepository applicationConfigRepository;
    private EmergencyNumbersRepository emergencyNumbersRepository;
    private RestTemplate restTemplate;
    private InstitutionService institutionService;
    private WhatsAppService whatsAppService;

    @Value("${googlemap.link}")
    private String googleMapLink;

    public SendMessageServiceImpl(CurrentUser currentUser, UserRepository userRepository, ApplicationConfigRepository applicationConfigRepository,
                                  EmergencyNumbersRepository emergencyNumbersRepository, RestTemplate restTemplate, InstitutionService institutionService, WhatsAppService whatsAppService) {
        this.userRepository = userRepository;
        this.applicationConfigRepository = applicationConfigRepository;
        this.currentUser = currentUser;
        this.emergencyNumbersRepository = emergencyNumbersRepository;
        this.restTemplate = restTemplate;
        this.institutionService = institutionService;
        this.whatsAppService = whatsAppService;
    }

    @Override
    public void sendMessage(EmergencyRq bodyRq) throws ResourceNotFoundException, ResourceForbiddenException {

        Optional<User> user = userRepository.findById(currentUser.getId());

        if (user.isEmpty())
            throw ResourceNotFoundException.create("User tidak ditemukan.");

        if (!user.get().isVerified())
            throw ResourceForbiddenException.create("Nomor Anda belum ter-verfikasi. Silahkan lakukan verifikasi.");

        Optional<ApplicationConfig> templateMessage = applicationConfigRepository.findById(CodeConfigConstant.EMERGENCY_MESSAGE);

        if (templateMessage.isEmpty())
            throw ResourceNotFoundException.create("Default template pesan tidak ditemukan.");

        List<EmergencyNumbers> listNumber = emergencyNumbersRepository.findByUserId(user.get().getId());

        String linkMap = googleMapLink.replace("{latitude}", bodyRq.getLatitude()).replace("{longitude}", bodyRq.getLongitude());

        List<Map<String, Object>> police = institutionService.findNearest(bodyRq.getLatitude(), bodyRq.getLongitude(), "P", 2);
        List<Map<String, Object>> rs = institutionService.findNearest(bodyRq.getLatitude(), bodyRq.getLongitude(), "RS", 2);
        List<Map<String, Object>> pk = institutionService.findNearest(bodyRq.getLatitude(), bodyRq.getLongitude(), "PK", 2);

        StringBuilder policeList = new StringBuilder();
        policeList.append("\n\nKantor polisi terdekat :\n");

        police.forEach(i -> policeList.append(i.get("name")).append(" | ").append(i.get("phone")).append("\n"));

        StringBuilder rsList = new StringBuilder();
        rsList.append("\nRumah sakit terdekat :\n");

        rs.forEach(i -> rsList.append(i.get("name")).append(" | ").append(i.get("phone")).append("\n"));

        StringBuilder pkList = new StringBuilder();
        pkList.append("\nPemadam kebakaran terdekat :\n");

        pk.forEach(i -> pkList.append(i.get("name")).append(" | ").append(i.get("phone")).append("\n"));

        if (!listNumber.isEmpty()) {
            listNumber.forEach(i -> {
                Thread newThread = new Thread(() -> {
                    String template = templateMessage.get().getValue();

                    StringBuilder message = new StringBuilder();

                    /*--------------------------------------------------------------------------------------------------------*/
                    /* Build message */
                    message.append(String.format(template, i.getName(), user.get().getName(), user.get().getPhone(), linkMap))
                            .append(policeList.toString())
                            .append(rsList.toString())
                            .append(pkList.toString());
                    /*--------------------------------------------------------------------------------------------------------*/

                    try {
                        whatsAppService.sendText(message.toString(), i.getPhone());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                newThread.start();
            });
        }
    }
}
