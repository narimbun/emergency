package com.palyndrum.emergencyalert.service.impl;

import com.palyndrum.emergencyalert.api.payload.request.ProfileRq;
import com.palyndrum.emergencyalert.api.payload.request.VerificationRq;
import com.palyndrum.emergencyalert.common.api.exception.ResourceConflictException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceForbiddenException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceInvalidException;
import com.palyndrum.emergencyalert.common.api.exception.ResourceNotFoundException;
import com.palyndrum.emergencyalert.common.constant.CodeConfigConstant;
import com.palyndrum.emergencyalert.common.model.CurrentUser;
import com.palyndrum.emergencyalert.entity.User;
import com.palyndrum.emergencyalert.repository.ApplicationConfigRepository;
import com.palyndrum.emergencyalert.repository.UserRepository;
import com.palyndrum.emergencyalert.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private CurrentUser currentUser;
    private ApplicationConfigRepository applicationConfigRepository;
    private MailSender mailSender;
    private RestTemplate restTemplate;

    @Value("${mail.send.to}")
    private String mailSendTo;


    public UserServiceImpl(UserRepository userRepository, CurrentUser currentUser, ApplicationConfigRepository applicationConfigRepository, MailSender mailSender, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.currentUser = currentUser;
        this.applicationConfigRepository = applicationConfigRepository;
        this.mailSender = mailSender;
        this.restTemplate = restTemplate;
    }


    @Override
    public User findById(String id) throws ResourceNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.create(String.format("User with '%s' doesn't exist.", id)));

    }

    @Override
    public User profile() throws ResourceNotFoundException {
        return findById(currentUser.getId());
    }

    @Override
    public User editProfile(ProfileRq bodyRq) throws ResourceNotFoundException {

        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> ResourceNotFoundException.create(String.format("User with '%s' doesn't exist.", currentUser.getId())));

        user.setName(bodyRq.getName());

        user = userRepository.save(user);

        return user;
    }

    @Override
    public void verification(VerificationRq bodyRq) throws ResourceNotFoundException, ResourceInvalidException, ResourceConflictException {

        Optional<User> user = userRepository.findById(currentUser.getId());

        if (user.isEmpty())
            throw ResourceNotFoundException.create(String.format("User with '%s' doesn't exist.", currentUser.getId()));

        if (user.get().isVerified())
            throw ResourceConflictException.create("Account Anda sudah terverifikasi.");

        String otp = user.get().getRegistrationOtp();
        Date otpSendDate = user.get().getLastOtpSendDate();
        Date currentDate = new Date();

        if (StringUtils.isEmpty(otp) || otpSendDate == null)
            throw ResourceNotFoundException.create("Kode verifikasi tidak ditemukan. Kirim ulang kode verifikasi.");

        int maxOTP = Integer.parseInt(applicationConfigRepository.findById(CodeConfigConstant.MAX_OTP_EXPIRED)
                .orElseThrow(() -> ResourceNotFoundException.create(String.format("Config '%s' doesn't exist.", CodeConfigConstant.MAX_OTP_EXPIRED))).getValue());

        long differenceInMinutes = ((currentDate.getTime() - otpSendDate.getTime()) / (60 * 1000));

        if (differenceInMinutes > maxOTP)
            throw ResourceInvalidException.create("Kode verifikasi sudah tidak dapat digunakan. Silahkan kirim ulang kode verifikasi.");

        if (!bodyRq.getVerificationCode().equals(otp))
            throw ResourceInvalidException.create("Kode verifikasi yang Anda masukan salah. Periksa kembali kode verfikasi Anda.");
        else
            user.get().setVerified(true);

        userRepository.save(user.get());

    }

    @Override
    public String sendVerification() throws ResourceNotFoundException, ResourceConflictException, ResourceForbiddenException {

        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> ResourceNotFoundException.create(String.format("User with '%s' doesn't exist.", currentUser.getId())));

        if (user.isVerified())
            throw ResourceConflictException.create("Account Anda sudah terverifikasi.");

        Date otpSendDate = user.getLastOtpSendDate();
        Date currentDate = new Date();

        if (otpSendDate != null) {
            long differenceInMinutes = ((currentDate.getTime() - otpSendDate.getTime()) / (60 * 1000));
            int maxOTP = Integer.parseInt(applicationConfigRepository.findById(CodeConfigConstant.MAX_OTP_EXPIRED)
                    .orElseThrow(() -> ResourceNotFoundException.create(String.format("Config '%s' doesn't exist.", CodeConfigConstant.MAX_OTP_EXPIRED))).getValue());
            if (differenceInMinutes <= maxOTP)
                throw ResourceForbiddenException.create(String.format("Kode verifikasi sudah dikirim ke nomor handphone Anda. Tunggu %s menit untuk mengirimkan ulang.", String.valueOf(maxOTP)));
        }

        String templateMessage = applicationConfigRepository.findById(CodeConfigConstant.VERIFICATION_MESSAGE)
                .orElseThrow(() -> ResourceNotFoundException.create(String.format("Config '%s' doesn't exist.", CodeConfigConstant.VERIFICATION_MESSAGE))).getValue();

        String otp = RandomStringUtils.randomNumeric(6);
        user.setRegistrationOtp(otp);
        user.setLastOtpSendDate(new Date());
        userRepository.save(user);

        /*SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@pesandarurat.com");
        message.setTo(mailSendTo);
        message.setSubject("Kode Verifikasi");
        message.setText(String.format("Hai %s\nKode verifikasi Anda : %s", user.getName(), otp));*/

        String waEndpoint = applicationConfigRepository.findById(CodeConfigConstant.RAPIWHA_ENDPOINT)
                .orElseThrow(() -> ResourceNotFoundException.create(String.format("Config '%s' doesn't exist.", CodeConfigConstant.RAPIWHA_ENDPOINT))).getValue();


        String endpoint = String.format(waEndpoint, user.getPhone().replaceFirst("0", "62"), String.format(templateMessage, user.getName(), otp));

        Thread newThread = new Thread(() -> {
            log.info("start send to wa {}", user.getName());
            ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);
            log.info(response.toString());
            log.info("finish send to wa {}", user.getName());


            /*      log.info("start send email {}", user.getName());
             *//*mailSender.send(message);*//*
            log.info("finish send email {}", user.getName());*/
        });
        newThread.start();

        return String.format("Kode verifikasi dikirim ke nomor %s", user.getPhone());

    }
}
