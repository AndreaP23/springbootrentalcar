package com.si2001.webapp.mapper;

import com.si2001.webapp.entities.User;
import org.openapitools.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user); // Converte User in UserDTO

    User toEntity(UserDTO userDTO); // Converte UserDTO in User
}
