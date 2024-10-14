package com.luvannie.identity_service.entity;

import com.luvannie.identity_service.validator.DobConstraint;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String username;
    String password;
    String firstName;
    String lastName;
    @DobConstraint(min = 18, message = "INVALID_DATE_OF_BIRTH")
    LocalDate dateOfBirth;
    @ManyToMany
    Set<Role> roles;


}
