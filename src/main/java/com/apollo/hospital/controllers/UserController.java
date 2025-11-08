package com.apollo.hospital.controllers;

import com.apollo.hospital.dtos.request.LoginReqDTO;
import com.apollo.hospital.dtos.request.UserReqDTO;
import com.apollo.hospital.dtos.response.UserRespDTO;
import com.apollo.hospital.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/apollo/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServices userServices;

    @PostMapping
    public ResponseEntity<UserRespDTO> getUserByEmail(@RequestBody LoginReqDTO dto) {
        UserRespDTO user = userServices.findUserByEmail(dto.getEmail());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserRespDTO> createUser(@RequestBody UserReqDTO reqDTO) {
        UserRespDTO user = userServices.createUser(reqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
