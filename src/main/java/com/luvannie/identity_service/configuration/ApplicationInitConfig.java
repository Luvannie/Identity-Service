package com.luvannie.identity_service.configuration;

import java.time.LocalDate;
import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.luvannie.identity_service.entity.Role;
import com.luvannie.identity_service.entity.User;
import com.luvannie.identity_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner init(UserRepository userRepository) {
        return args -> {
            System.out.println("Application started");
            var roles = new HashSet<Role>();
            roles.add(Role.builder().name("ADMIN").description("Admin role").build());
            if (userRepository.findByUsername("admin").isEmpty()) {
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("nhatanh25"))
                        .firstName("Anh")
                        .lastName("Nguyen")
                        .dateOfBirth(LocalDate.parse("1999-01-01"))
                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("Admin user created");
            }
            ;
        };
    }
}
