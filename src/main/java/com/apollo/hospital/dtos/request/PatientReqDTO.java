package com.apollo.hospital.dtos.request;

import com.apollo.hospital.entities.types.BloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PatientReqDTO {
    private String name;
    private String email;
    private LocalDate dob;
    private String gender;
    private BloodGroupType bloodGroup;
}
