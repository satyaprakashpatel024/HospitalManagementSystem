package com.apollo.hospital.services;

import com.apollo.hospital.dtos.request.AppointmentReqDTO;
import com.apollo.hospital.dtos.response.AppointmentRespDTO;
import com.apollo.hospital.entities.Appointment;
import com.apollo.hospital.entities.Doctor;
import com.apollo.hospital.exceptions.AppointmentNotFoundException;
import com.apollo.hospital.exceptions.ResourceNotFoundException;
import com.apollo.hospital.repositories.AppointmentRepository;
import com.apollo.hospital.repositories.DoctorRepository;
import com.apollo.hospital.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    @Transactional
    public AppointmentRespDTO reassignDoctor(Long appointmentId, Long newDoctorId) throws ResourceNotFoundException, AppointmentNotFoundException {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + appointmentId));
        Doctor newDoctor = doctorRepository.findById(newDoctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + newDoctorId));
        appointment.setDoctor(newDoctor);
        newDoctor.getAppointments().add(appointment);
        return convertToAppointmentRespDTO(appointment);
    }

    public Page<AppointmentRespDTO> getAppointmentsByPatientId(Long patientId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Appointment> appointments = appointmentRepository.findByPatientId(pageable, patientId);
        return appointments
                .map(this::convertToAppointmentRespDTO);
    }

    public Page<AppointmentRespDTO> getAppointmentsByDoctorId(Long doctorId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Appointment> appointments = appointmentRepository.findByDoctorId(pageable, doctorId);
        return appointments
                .map(this::convertToAppointmentRespDTO);
    }

    @Transactional
    public AppointmentRespDTO rescheduleAppointment(Long appointmentId, AppointmentReqDTO updatedAppointmentDetails) throws AppointmentNotFoundException {
        Appointment existingAppointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with ID " + appointmentId + " not found."));
        existingAppointment.setAppointmentDate(updatedAppointmentDetails.getAppointmentDate());
        if (updatedAppointmentDetails.getReason() != null) {
            existingAppointment.setReason(updatedAppointmentDetails.getReason());
        }
        Appointment save = appointmentRepository.save(existingAppointment);
        return convertToAppointmentRespDTO(save);
    }


    public Page<AppointmentRespDTO> getAllAppointments(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Appointment> all = appointmentRepository.findAll(pageable);
        return all.map(this::convertToAppointmentRespDTO);
    }


    private AppointmentRespDTO convertToAppointmentRespDTO(Appointment appointment) {
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

    private Appointment convertToAppointmentEntity(AppointmentReqDTO appointment) {
        return Appointment.builder()
                .appointmentDate(appointment.getAppointmentDate())
                .reason(appointment.getReason())
                .build();
    }


    public AppointmentRespDTO getAppointmentById(Long appointmentId) throws AppointmentNotFoundException {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + appointmentId));
        return convertToAppointmentRespDTO(appointment);
    }
}
