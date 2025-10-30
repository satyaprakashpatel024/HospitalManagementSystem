package com.apollo.hospital.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DoctorReqDTO {
    private String name;
    private String email;
    private String specialization;
}
