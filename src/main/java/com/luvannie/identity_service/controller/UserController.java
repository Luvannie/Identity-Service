package com.luvannie.identity_service.controller;

import com.luvannie.identity_service.dto.request.UserCreationRequest;
import com.luvannie.identity_service.dto.request.UserUpdateRequest;
import com.luvannie.identity_service.dto.response.ApiResponse;
import com.luvannie.identity_service.dto.response.UserResponse;
import com.luvannie.identity_service.entity.User;
import com.luvannie.identity_service.exception.AppException;
import com.luvannie.identity_service.exception.ErrorCode;
import com.luvannie.identity_service.mapper.UserMapper;
import com.luvannie.identity_service.repository.RoleRepository;
import com.luvannie.identity_service.repository.UserRepository;
import com.luvannie.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest user) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setCode(200);
       response.setResult(userService.createUser(user));
        return response;
    }

//    @PreAuthorize("hasRole('ADMIN')") //spring se tao rao proxy de kiem tra quyen
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // Dung  hasAuthority thi spring se tim kiem chinh xac authority la : ROLE_ADMIN
    @GetMapping()
    ApiResponse<List<User>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("User: {}", authentication.getName());
        authentication.getAuthorities().forEach(a -> log.info("Role: {}", a.getAuthority()));
        log.info("in method get all users");
        ApiResponse<List<User>> response = new ApiResponse<>();
        response.setCode(200);
        response.setResult(userService.getUsers());
        return response;
    }

    @PostAuthorize("returnObject.username == authentication.name") //spring se tao rao proxy de kiem tra quyen nhung ma la sau khi vao ham
    @GetMapping("/{id}")
    UserResponse getUserById(@PathVariable String id) {
        log.info("in method get user by id");
        return userService.getUserById(id);
    }

    @GetMapping("myInfo")
    ApiResponse<UserResponse> getMyIfo(){
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .result(userService.getMyInfo())
                .build();
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
