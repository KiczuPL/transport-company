package com.example.transportcompany.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.model.dao.User;
import com.example.transportcompany.security.JwtConfig;
import com.example.transportcompany.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


//TODO: REFACTOR!
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtConfig jwtConfig;
    private final UserService userService;

    public User getUserAssignedToJwt(HttpServletRequest request) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String refreshToken = authorizationHeader.substring(7);
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecretKey().getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        String username = decodedJWT.getSubject();
        User user = userService.getUser(username);
        return user;
    }

    public Company getCompanyOfUserJwtAssignedToJwt(HttpServletRequest request) throws IOException {
        return getUserAssignedToJwt(request).getCompany();
    }
}
