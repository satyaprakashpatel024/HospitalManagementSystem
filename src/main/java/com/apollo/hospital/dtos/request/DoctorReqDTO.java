package com.apollo.hospital.dtos.request;

import com.apollo.hospital.entities.Department;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorReqDTO {
    private String name;
    private String email;
    private String specialization;
    private Department department;
}
