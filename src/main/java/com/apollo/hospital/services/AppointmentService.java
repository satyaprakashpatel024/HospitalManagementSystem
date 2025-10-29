package com.apollo.hospital.services;

import  com.apollo.hospital.entities.Appointment;
import  com.apollo.hospital.entities.Doctor;
import  com.apollo.hospital.repositories.AppointmentRepository;
import  com.apollo.hospital.repositories.DoctorRepository;
import  com.apollo.hospital.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public Appointment bookAppointment(Appointment appointment, Long patientId, Long doctorId) {
        var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        patient.getAppointments().add(appointment);
        doctor.getAppointments().add(appointment);
        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment reassignDoctor(Long appointmentId, Long newDoctorId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        Doctor newDoctor = doctorRepository.findById(newDoctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        appointment.setReason("Brain Surgery Consultation");
        appointment.setDoctor(newDoctor);
        newDoctor.getAppointments().add(appointment);
        return appointment;
    }
}
