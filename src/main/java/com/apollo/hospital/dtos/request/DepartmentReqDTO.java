package com.apollo.hospital.dtos.request;

import com.apollo.hospital.entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DepartmentReqDTO {
    private String name;
    private LocalDateTime createdAt;
    private Doctor headDoctor;
}
