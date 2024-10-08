package com.luvannie.identity_service.repository;

import com.luvannie.identity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {


    //    User findById(String id);
    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
