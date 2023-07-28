package com.palyndrum.emergencyalert.common.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palyndrum.emergencyalert.common.api.payload.response.CommonRs;
import com.palyndrum.emergencyalert.common.constant.APIMessageConstant;
import com.palyndrum.emergencyalert.common.model.CurrentUser;
import com.palyndrum.emergencyalert.common.security.model.UserDetailsImpl;
import com.palyndrum.emergencyalert.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class AuthenticationJWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private CurrentUser currentUser;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return super.shouldNotFilter(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = parseJwt(request);

        if (!StringUtils.isEmpty(jwt)) {
            Map<String, String> map = jwtUtils.validateJwtToken(jwt);
            if ("0".equals(map.get("code"))) {

                User user = jwtUtils.getUserFromJwtToken(jwt);

                UserDetailsImpl userDetails = new UserDetailsImpl(user.getId(), user.getEmail(), user.getPhone(), user.getPassword(), user.getUsername(), user.getName(), user.isActive(), user.getLastLoginTime());

                currentUser.setId(user.getId());
                currentUser.setUsername(user.getUsername());
                currentUser.setEmail(user.getEmail());
                currentUser.setPhone(user.getPhone());
                currentUser.setName(user.getName());
                currentUser.setActive(user.isActive());
                currentUser.setLastLoginTime(user.getLastLoginTime());
                currentUser.setVerified(user.isVerified());
                currentUser.setLastOtpSendDate(user.getLastOtpSendDate());
                currentUser.setRegistrationOtp(user.getRegistrationOtp());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                final CommonRs body = new CommonRs(APIMessageConstant.FAILED);
                body.addError(map.get("message"));

                final ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(response.getOutputStream(), body);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (!StringUtils.isEmpty(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return "";
    }
}
