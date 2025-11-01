package com.apollo.hospital.dtos.response;

import com.apollo.hospital.entities.types.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRespDTO {
    private Long id;
    private LocalDate appointmentDate;
    private String reason;
    private String patient;
    private String doctor;
    private String specialization;
    private Gender gender;
}

