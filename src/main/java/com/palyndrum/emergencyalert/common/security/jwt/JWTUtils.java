package com.palyndrum.emergencyalert.common.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.palyndrum.emergencyalert.common.security.model.UserDetailsImpl;
import com.palyndrum.emergencyalert.entity.User;
import com.palyndrum.emergencyalert.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JWTUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-in-ms}")
    private int jwtExpirationMs;

    @Value("${app.jwt.issuer}")
    private String issuer;

    private UserService userService;

    public JWTUtils(UserService userService) {
        this.userService = userService;
    }

    public Map<String, String> generateAccessToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        Map<String, String> mapResult = new HashMap<>();

        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);

        String accessToken = JWT.create()
                .withIssuer(issuer)
                .withSubject(userPrincipal.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date((new Date()).getTime() + jwtExpirationMs))
                .sign(algorithm);

        mapResult.put("accessToken", accessToken);
        mapResult.put("expiration", String.valueOf(jwtExpirationMs));

        return mapResult;
    }

    User getUserFromJwtToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            return userService.findById(jwt.getSubject());

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    Map<String, String> validateJwtToken(String authToken) {

        Map<String, String> map = new HashMap<>();
        try {
            Algorithm algorithm = Algorithm.HMAC512(jwtSecret);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();

            verifier.verify(authToken);

            map.put("code", "0");
            map.put("message", "success");
        } catch (JWTVerificationException e) {
            map.put("code", "1");
            map.put("message", "invalid access token");
        }
        return map;
    }
}
