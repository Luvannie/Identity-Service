package com.luvannie.identity_service.service;

import com.luvannie.identity_service.dto.request.AuthenticationRequest;
import com.luvannie.identity_service.dto.request.IntrospectRequest;
import com.luvannie.identity_service.dto.response.AuthenticationResponse;
import com.luvannie.identity_service.dto.response.IntrospectResponse;
import com.luvannie.identity_service.entity.User;
import com.luvannie.identity_service.exception.AppException;
import com.luvannie.identity_service.exception.ErrorCode;
import com.luvannie.identity_service.repository.UserRepository;
import com.nimbusds.jose.*;

import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthenticationService {
    UserRepository userRepository;


    @Value("${jwt.secretKey}")
            @NonFinal
    String SIGNER_KEY;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier =  new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date exp = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && exp.after(new Date()))
        .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws KeyLengthException {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated =  passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(user.getUsername());
        return AuthenticationResponse.builder()
                .isAuthenticated(true)
                .token(token)
                .build();
    }

    private String generateToken(String username) throws KeyLengthException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("luvanie")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .claim("custom", "custom-value")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error signing token", e);
        }
    }
}
