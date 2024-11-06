package com.luvannie.identity_service.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.luvannie.identity_service.dto.request.RoleRequest;
import com.luvannie.identity_service.dto.response.PermissionResponse;
import com.luvannie.identity_service.dto.response.RoleResponse;
import com.luvannie.identity_service.entity.Permission;
import com.luvannie.identity_service.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    //    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "permissions", expression = "java(mapPermissions(role.getPermissions()))")
    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "permissions", expression = "java(mapStringPermissions(request.getPermissions()))")
    Role toRole(RoleRequest request);

    default Set<PermissionResponse> mapPermissions(Set<Permission> permissions) {
        return permissions.stream()
                .map(permission -> new PermissionResponse(permission.getName(), permission.getDescription()))
                .collect(Collectors.toSet());
    }

    default Set<Permission> mapStringPermissions(Set<String> permissions) {
        return permissions.stream()
                .map(permissionName -> new Permission(permissionName, ""))
                .collect(Collectors.toSet());
    }
}
