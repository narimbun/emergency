package com.palyndrum.emergencyalert.common.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.palyndrum.emergencyalert.common.api.payload.response.CommonRs;
import com.palyndrum.emergencyalert.common.constant.APIMessageConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationJWTEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final CommonRs body = new CommonRs(APIMessageConstant.FAILED);
        body.addError(authException.getMessage());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);

    }
}
