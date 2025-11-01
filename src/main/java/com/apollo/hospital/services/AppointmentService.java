package com.apollo.hospital.services;

import com.apollo.hospital.dtos.request.AppointmentReqDTO;
import com.apollo.hospital.dtos.response.AppointmentRespDTO;
import com.apollo.hospital.entities.Appointment;
import com.apollo.hospital.entities.Doctor;
import com.apollo.hospital.exceptions.ResourceNotFoundException;
import com.apollo.hospital.repositories.AppointmentRepository;
import com.apollo.hospital.repositories.DoctorRepository;
import com.apollo.hospital.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public AppointmentRespDTO bookAppointment(AppointmentReqDTO appointment, Long patientId, Long doctorId) throws ResourceNotFoundException {
        var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + doctorId));
        Appointment appointment1 = convertToAppointmentEntity(appointment);
        appointment1.setPatient(patient);
        appointment1.setDoctor(doctor);
        patient.getAppointments().add(appointment1);
        doctor.getAppointments().add(appointment1);
        Appointment save = appointmentRepository.save(appointment1);
        return convertToAppointmentRespDTO(save);
    }

    private Appointment convertToAppointmentEntity(AppointmentReqDTO appointment) {
        return Appointment.builder()
                .appointmentDate(appointment.getAppointmentDate())
                .reason(appointment.getReason())
                .build();
    }

    @Transactional
    public Appointment reassignDoctor(Long appointmentId, Long newDoctorId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));
        Doctor newDoctor = doctorRepository.findById(newDoctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + newDoctorId));
        appointment.setDoctor(newDoctor);
        newDoctor.getAppointments().add(appointment);
        return appointment;
    }

    public List<AppointmentRespDTO> getAppointmentsByPatientId(Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        return appointments.stream()
                .map(this::convertToAppointmentRespDTO)
                .toList();
    }


    public List<AppointmentRespDTO> getAllAppointments() {
        List<Appointment> all = appointmentRepository.findAll();
        return all.stream()
                .map(this::convertToAppointmentRespDTO)
                .toList();
    }


    public AppointmentRespDTO convertToAppointmentRespDTO(Appointment appointment) {
        return AppointmentRespDTO.builder()
                .id(appointment.getId())
                .appointmentDate(appointment.getAppointmentDate())
                .reason(appointment.getReason())
                .doctor(appointment.getDoctor().getName())
                .patient(appointment.getPatient().getName())
                .gender(appointment.getPatient().getGender())
                .specialization(appointment.getDoctor().getSpecialization())
                .build();
    }
}
