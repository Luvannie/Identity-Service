package com.luvannie.identity_service.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data //tao ra cac phuong thuc getter, setter, equals, hashcode, toString
@Builder //tao ra mot builder class de tao ra mot doi tuong
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE) //xac dinh muc do truy cap cua cac truong mac dinh la private
public class UserCreationRequest {
    String username;
    @Size(min = 4, message = "INVALID_PASSWORD") //chieu dai tooi thieu cua password la 4 ki tu
    String password;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;


}
