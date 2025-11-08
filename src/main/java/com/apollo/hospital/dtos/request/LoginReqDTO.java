package com.apollo.hospital.dtos.request;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginReqDTO {
    private String email;
    private String password;
}
