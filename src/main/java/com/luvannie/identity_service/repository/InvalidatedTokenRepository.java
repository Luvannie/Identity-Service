package com.luvannie.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luvannie.identity_service.entity.InvalidatedToken;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {}
