package com.luvannie.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luvannie.identity_service.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByName(String name);
}
