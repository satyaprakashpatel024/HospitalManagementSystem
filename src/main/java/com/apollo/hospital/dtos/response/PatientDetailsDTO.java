package com.apollo.hospital.dtos.response;

import com.apollo.hospital.entities.types.BloodGroupType;
import com.apollo.hospital.entities.types.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class PatientDetailsDTO {
    private Long id;
    private String name;
    private String email;
    private LocalDate dob;
    private Gender gender;
    private LocalDateTime createdAt;
    private BloodGroupType bloodGroup;
    private InsuranceRespDTO insurance;
}
