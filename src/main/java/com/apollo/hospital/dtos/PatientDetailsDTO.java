package com.apollo.hospital.dtos;

import com.apollo.hospital.entities.types.BloodGroupType;
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
    private String gender;
    private LocalDateTime createdAt;
    private BloodGroupType bloodGroup;
    private InsuranceDTO insurance;
}
