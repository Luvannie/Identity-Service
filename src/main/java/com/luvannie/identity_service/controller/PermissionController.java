package com.luvannie.identity_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.luvannie.identity_service.dto.request.PermissionRequest;
import com.luvannie.identity_service.dto.response.ApiResponse;
import com.luvannie.identity_service.dto.response.PermissionResponse;
import com.luvannie.identity_service.service.PermissionService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
// @Autowired not best practice, use constructor injection
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping()
    ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .code(200)
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping()
    ApiResponse<List<PermissionResponse>> getPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(200)
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> deletePermission(@PathVariable String permission) {
        permissionService.delete(permission);
        return ApiResponse.<Void>builder().code(200).build();
    }
}
