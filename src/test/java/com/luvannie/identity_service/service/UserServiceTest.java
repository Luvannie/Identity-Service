package com.luvannie.identity_service.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.luvannie.identity_service.dto.request.UserCreationRequest;
import com.luvannie.identity_service.dto.response.UserResponse;
import com.luvannie.identity_service.entity.User;
import com.luvannie.identity_service.exception.AppException;
import com.luvannie.identity_service.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(1990, 1, 1);

        request = UserCreationRequest.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .password("12345678")
                .dateOfBirth(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("cf0600f538b3")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(dob)
                .build();

        user = User.builder()
                .id("cf0600f538b3")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var response = userService.createUser(request);
        // THEN

        Assertions.assertThat(response.getId()).isEqualTo("cf0600f538b3"); // id is generated
        Assertions.assertThat(response.getUsername()).isEqualTo("john"); // username is set
    }

    @Test
    void createUser_userExisted_fail() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }

    //    @Test
    //    @WithMockUser(username = "john")
    //    void getMyInfo_valid_success() {
    //        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
    //
    //        var response = userService.getMyInfo();
    //
    //        Assertions.assertThat(response.getUsername()).isEqualTo("john");
    //        Assertions.assertThat(response.getId()).isEqualTo("cf0600f538b3");
    //    }
    //
    //    @Test
    //    @WithMockUser(username = "john")
    //    void getMyInfo_userNotFound_error() {
    //        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));
    //
    //        // WHEN
    //        var exception = assertThrows(AppException.class, () -> userService.getMyInfo());
    //
    //        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
    //    }
}
