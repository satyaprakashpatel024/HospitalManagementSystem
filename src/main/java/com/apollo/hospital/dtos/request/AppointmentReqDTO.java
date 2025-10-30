package com.apollo.hospital.dtos.request;

import com.apollo.hospital.entities.Doctor;
import com.apollo.hospital.entities.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AppointmentReqDTO {
    private LocalDate appointmentDate;
    private String reason;
    private Patient patient;
    private Doctor doctor;
}
