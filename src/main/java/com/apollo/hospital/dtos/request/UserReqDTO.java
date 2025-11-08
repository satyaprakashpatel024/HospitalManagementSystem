package com.apollo.hospital.dtos.request;

import com.apollo.hospital.entities.types.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserReqDTO {
    private String password;
    private String email;
    private Role role;
}
