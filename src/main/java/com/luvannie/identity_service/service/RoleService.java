package com.luvannie.identity_service.service;

import com.luvannie.identity_service.dto.request.RoleRequest;
import com.luvannie.identity_service.dto.response.RoleResponse;
import com.luvannie.identity_service.entity.Permission;
import com.luvannie.identity_service.entity.Role;
import com.luvannie.identity_service.mapper.RoleMapper;
import com.luvannie.identity_service.repository.PermissionRepository;
import com.luvannie.identity_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest request) {
        Role role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();
        log.info("roles: {}", roles.stream().map(roleMapper::toRoleResponse).toList());
        log.info("roles: {}", roles);
        return roles.stream().map(roleMapper::toRoleResponse).toList();

    }

    public void delete(String name) {
        roleRepository.deleteById(name);
    }
}
