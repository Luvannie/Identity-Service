package com.luvannie.identity_service.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luvannie.identity_service.constant.PredefinedRole;
import com.luvannie.identity_service.dto.request.UserCreationRequest;
import com.luvannie.identity_service.dto.request.UserUpdateRequest;
import com.luvannie.identity_service.dto.response.UserResponse;
import com.luvannie.identity_service.entity.Role;
import com.luvannie.identity_service.entity.User;
import com.luvannie.identity_service.exception.AppException;
import com.luvannie.identity_service.exception.ErrorCode;
import com.luvannie.identity_service.mapper.UserMapper;
import com.luvannie.identity_service.repository.RoleRepository;
import com.luvannie.identity_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        user.setRoles(roles);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponse(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.update(user, request);
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public UserResponse getMyInfo() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
}
