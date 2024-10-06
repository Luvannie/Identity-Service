package com.luvannie.identity_service.repository;

import com.luvannie.identity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
//    User findById(String id);
Boolean existsByUsername(String username);
}
