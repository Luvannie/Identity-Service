package com.luvannie.identity_service.controller;

import com.luvannie.identity_service.dto.request.UserCreationRequest;
import com.luvannie.identity_service.dto.request.UserUpdateRequest;
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
    User createUser(@RequestBody @Valid UserCreationRequest user) {
        return userService.createUser(user);
    }

    @GetMapping()
    List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PutMapping("{id}")
    User updateUser(@PathVariable String id, @RequestBody UserUpdateRequest request){
        return userService.updateUser(id, request);

    }

    @DeleteMapping("{id}")
    String deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return "User has been deleted";
    }

}
