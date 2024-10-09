package com.luvannie.identity_service.controller;

import com.luvannie.identity_service.dto.request.UserCreationRequest;
import com.luvannie.identity_service.dto.request.UserUpdateRequest;
import com.luvannie.identity_service.dto.response.ApiResponse;
import com.luvannie.identity_service.dto.response.UserResponse;
import com.luvannie.identity_service.entity.User;
import com.luvannie.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
// @Autowired not best practice, use constructor injection
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class UserController {

    UserService userService;

    @PostMapping()
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest user) {
        ApiResponse<User> response = new ApiResponse<>();
        response.setCode(200);
       response.setResult(userService.createUser(user));
        return response;
    }

    @GetMapping()
    ApiResponse<List<User>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("User: {}", authentication.getName());
        authentication.getAuthorities().forEach(a -> log.info("Role: {}", a.getAuthority()));
        ApiResponse<List<User>> response = new ApiResponse<>();
        response.setCode(200);
        response.setResult(userService.getUsers());
        return response;
    }

    @GetMapping("/{id}")
    UserResponse getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PutMapping("{id}")
    UserResponse updateUser(@PathVariable String id, @RequestBody @Valid UserUpdateRequest user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("{id}")
    ApiResponse<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        ApiResponse<Void> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("User has been deleted");
        return response;
    }

}
