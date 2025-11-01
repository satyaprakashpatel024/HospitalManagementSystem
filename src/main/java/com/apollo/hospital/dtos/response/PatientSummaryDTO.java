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
public class PatientSummaryDTO {
    private Long id;
    private String name;
    private String email;
    private LocalDate dob;
    private Gender gender;
    private BloodGroupType bloodGroup;
    private LocalDateTime createdAt;
    private Long insuranceId;
}

