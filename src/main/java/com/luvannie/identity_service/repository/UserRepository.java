package com.luvannie.identity_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luvannie.identity_service.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

    //    User findById(String id);
    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
