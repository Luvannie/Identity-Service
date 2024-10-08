package com.luvannie.identity_service.controller;

import com.luvannie.identity_service.dto.request.AuthenticationRequest;
import com.luvannie.identity_service.dto.request.IntrospectRequest;
import com.luvannie.identity_service.dto.response.ApiResponse;
import com.luvannie.identity_service.dto.response.AuthenticationResponse;
import com.luvannie.identity_service.dto.response.IntrospectResponse;
import com.luvannie.identity_service.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController

@RequestMapping("/auth")
@RequiredArgsConstructor
// @Autowired not best practice, use constructor injection
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws KeyLengthException {
        var res = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .result(res)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws JOSEException, ParseException {
        var res = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .code(200)
                .result(res)
                .build();
    }
}
