package com.luvannie.identity_service.controller;

import com.luvannie.identity_service.dto.request.UserCreationRequest;
import com.luvannie.identity_service.dto.request.UserUpdateRequest;
import com.luvannie.identity_service.dto.response.ApiResponse;
import com.luvannie.identity_service.entity.User;
import com.luvannie.identity_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest user) {
        ApiResponse<User> response = new ApiResponse<>();
        response.setCode(200);
       response.setResult(userService.createUser(user));
        return response;
    }

    @GetMapping()
    ApiResponse<List<User>> getUsers() {
        ApiResponse<List<User>> response = new ApiResponse<>();
        response.setCode(200);
        response.setResult(userService.getUsers());
        return response;
    }

    @GetMapping("/{id}")
    ApiResponse<User> getUserById(@PathVariable String id) {
        ApiResponse<User> response = new ApiResponse<>();
        response.setCode(200);
        response.setResult(userService.getUserById(id));
        return response;
    }

    @PutMapping("{id}")
    ApiResponse<User> updateUser(@PathVariable String id, @RequestBody @Valid UserUpdateRequest request) {
        ApiResponse<User> response = new ApiResponse<>();
        response.setCode(200);
        response.setResult(userService.updateUser(id, request));
        return response;
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
