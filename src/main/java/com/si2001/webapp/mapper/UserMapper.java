package com.si2001.webapp.mapper;

import com.si2001.webapp.dto.UserDTO;
import com.si2001.webapp.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserDTO.builder()
                .userId(user.getUserId())
                .nome(user.getNome())
                .cognome(user.getCognome())
                .email(user.getEmail())
                .password(user.getPassword())
                .telefono(user.getTelefono())
                .dataNascita(user.getDataNascita())
                .ruolo(user.getRuolo())
                .build();
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        return User.builder()
                .userId(dto.getUserId())
                .nome(dto.getNome())
                .cognome(dto.getCognome())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .telefono(dto.getTelefono())
                .dataNascita(dto.getDataNascita())
                .ruolo(dto.getRuolo())
                .build();
    }
}
