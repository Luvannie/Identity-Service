package com.luvannie.identity_service.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data // tao ra cac phuong thuc getter, setter, equals, hashcode, toString
@Builder // tao ra mot builder class de tao ra mot doi tuong
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE) // xac dinh muc do truy cap cua cac truong mac dinh la private
public class UserCreationRequest {
    @NotNull
    @Size(min = 4, message = "Username must be at least 4 characters")
    String username;

    @Size(min = 4, message = "INVALID_PASSWORD") // chieu dai tooi thieu cua password la 4 ki tu
    String password;

    String firstName;
    String lastName;
    LocalDate dateOfBirth;
}
