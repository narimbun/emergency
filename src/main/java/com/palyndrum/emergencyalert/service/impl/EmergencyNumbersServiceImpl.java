package com.palyndrum.emergencyalert.service.impl;

import com.palyndrum.emergencyalert.api.payload.request.RecipientRq;
import com.palyndrum.emergencyalert.common.api.exception.ResourceConflictException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceForbiddenException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceInvalidException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceNotFoundException;
import com.palyndrum.emergencyalert.common.constant.RegexConstant;
import com.palyndrum.emergencyalert.common.model.CurrentUser;
import com.palyndrum.emergencyalert.common.util.CommonUtil;
import com.palyndrum.emergencyalert.entity.EmergencyNumbers;
import com.palyndrum.emergencyalert.entity.User;
import com.palyndrum.emergencyalert.repository.EmergencyNumbersRepository;
import com.palyndrum.emergencyalert.service.EmergencyNumbersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class EmergencyNumbersServiceImpl implements EmergencyNumbersService {


    private CurrentUser currentUser;
    private EmergencyNumbersRepository emergencyNumbersRepository;

    public EmergencyNumbersServiceImpl(CurrentUser currentUser, EmergencyNumbersRepository emergencyNumbersRepository) {
        this.emergencyNumbersRepository = emergencyNumbersRepository;
        this.currentUser = currentUser;
    }

    @Override
    public Map<String, Object> addNumber(RecipientRq bodyRq) throws ResourceInvalidException, ResourceConflictException, ResourceForbiddenException {

        if (!currentUser.isVerified())
            throw ResourceForbiddenException.create("Nomor Anda belum ter-verfikasi. Silahkan lakukan verifikasi.");


        if (!CommonUtil.patternMatches(bodyRq.getPhone(), RegexConstant.PHONE_PATTERN))
            throw ResourceInvalidException.create("Nomor handphone tidak valid. Pastikan Anda memasukan nomor yang benar.");

        if (emergencyNumbersRepository.findByUserIdAndPhone(currentUser.getId(), bodyRq.getPhone()).isPresent())
            throw ResourceConflictException.create(String.format("Nomor handphone %s sudah terdaftar sebagai penerima pesan darurat.", bodyRq.getPhone()));

        EmergencyNumbers emergencyNumbers = EmergencyNumbers.builder().user(new User(currentUser.getId()))
                .name(bodyRq.getName())
                .phone(bodyRq.getPhone())
                .isActive(true).build();

        emergencyNumbers.setCreatedDate(new Date());

        emergencyNumbers = emergencyNumbersRepository.save(emergencyNumbers);

        return emergencyNumbers.toMap();
    }

    @Override
    public Map<String, Object> editNumber(String id, RecipientRq bodyRq) throws ResourceNotFoundException, ResourceInvalidException, ResourceConflictException, ResourceForbiddenException {

        if (!currentUser.isVerified())
            throw ResourceForbiddenException.create("Nomor Anda belum ter-verfikasi. Silahkan lakukan verifikasi.");

        if (!CommonUtil.patternMatches(bodyRq.getPhone(), RegexConstant.PHONE_PATTERN))
            throw ResourceInvalidException.create("Nomor handphone tidak valid. Pastikan Anda memasukan nomor yang benar.");

        EmergencyNumbers em = emergencyNumbersRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.create(String.format("Number with id '%s' doesn't exist.", id)));

        if (!em.getUser().getId().equals(currentUser.getId()))
            throw ResourceNotFoundException.create(String.format("Number with id '%s' doesn't exist.", id));

        Optional<EmergencyNumbers> em2 = emergencyNumbersRepository.findByUserIdAndPhone(currentUser.getId(), bodyRq.getPhone());

        if (em2.isPresent()) {
            if (!em.getPhone().equals(bodyRq.getPhone()))
                throw ResourceConflictException.create(String.format("Nomor handphone %s sudah terdaftar sebagai penerima pesan darurat.", bodyRq.getPhone()));
        }

        em.setName(bodyRq.getName());
        em.setPhone(bodyRq.getPhone());
        em.setUpdatedDate(new Date());

        emergencyNumbersRepository.save(em);

        return em.toMap();
    }

    @Override
    public List<Map<String, Object>> numberList() throws ResourceForbiddenException {

        if (!currentUser.isVerified())
            throw ResourceForbiddenException.create("Nomor Anda belum ter-verfikasi. Silahkan lakukan verifikasi.");

        List<EmergencyNumbers> emergencyNumbersList = emergencyNumbersRepository.findByUserId(currentUser.getId());

        return emergencyNumbersList.stream()
                .map(EmergencyNumbers::toMap).toList();
    }

    @Override
    public void deleteNumber(String id) throws ResourceNotFoundException, ResourceForbiddenException {

        if (!currentUser.isVerified())
            throw ResourceForbiddenException.create("Nomor Anda belum ter-verfikasi. Silahkan lakukan verifikasi.");

        EmergencyNumbers em = emergencyNumbersRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.create(String.format("Number with id '%s' doesn't exist.", id)));

        if (!em.getUser().getId().equals(currentUser.getId()))
            throw ResourceNotFoundException.create(String.format("Number with id '%s' doesn't exist.", id));

        emergencyNumbersRepository.deleteById(id);
    }
}
