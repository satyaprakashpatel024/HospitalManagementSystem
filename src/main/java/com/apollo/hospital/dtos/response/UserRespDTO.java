package com.apollo.hospital.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class UserRespDTO {
    private Long id;
    private String email;
    private String role;
    private Boolean isActive;
}
