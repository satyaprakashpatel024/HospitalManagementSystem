package com.apollo.hospital.services;

import com.apollo.hospital.dtos.request.UserReqDTO;
import com.apollo.hospital.dtos.response.UserRespDTO;
import com.apollo.hospital.entities.Users;
import com.apollo.hospital.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class UserServices {
    private final UsersRepository usersRepository;

    public UserRespDTO findUserByEmail(String email) {
        Users users = usersRepository.findByEmailAndIsActive(email, true).orElse(null);
        if (users == null) {
            return null;
        }
        return mapToDTO(users);
    }

    public UserRespDTO createUser(UserReqDTO userReqDTO) {
        Users user = mapToEntity(userReqDTO);
        Users savedUser = usersRepository.save(user);
        return mapToDTO(savedUser);
    }

    private UserRespDTO mapToDTO(Users save) {
        return UserRespDTO.builder()
                .id(save.getId())
                .email(save.getEmail())
                .role(String.valueOf(save.getRole()))
                .isActive(save.getIsActive())
                .build();
    }

    private Users mapToEntity(UserReqDTO dto) {
        return Users.builder()
                .email(dto.getEmail())
                .role(dto.getRole())
                .password(dto.getPassword())
                .isActive(true)
                .lastLogin(OffsetDateTime.now())
                .createdAt(OffsetDateTime.now())
                .build();
    }
}
