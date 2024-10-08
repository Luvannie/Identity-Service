package com.luvannie.identity_service.mapper;

import com.luvannie.identity_service.dto.request.UserCreationRequest;
import com.luvannie.identity_service.dto.request.UserUpdateRequest;
import com.luvannie.identity_service.dto.response.UserResponse;
import com.luvannie.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    void update(@MappingTarget User user, UserUpdateRequest request);
    UserResponse toUserResponse(User user);
}
