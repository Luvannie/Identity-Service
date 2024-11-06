package com.luvannie.identity_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.luvannie.identity_service.dto.request.RoleRequest;
import com.luvannie.identity_service.dto.response.ApiResponse;
import com.luvannie.identity_service.dto.response.RoleResponse;
import com.luvannie.identity_service.service.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class RoleController {
    // @Autowired not best practice, use constructor injection
    //
    RoleService roleService;

    @PostMapping()
    ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .code(200)
                .result(roleService.create(request))
                .build();
    }

    @GetMapping()
    ApiResponse<List<RoleResponse>> getRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .code(200)
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> deleteRole(@PathVariable String role) {
        roleService.delete(role);
        return ApiResponse.<Void>builder().code(200).build();
    }
}
