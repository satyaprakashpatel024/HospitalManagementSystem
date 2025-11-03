package com.apollo.hospital.controllers;

import com.apollo.hospital.dtos.request.AppointmentReqDTO;
import com.apollo.hospital.dtos.response.AppointmentRespDTO;
import com.apollo.hospital.exceptions.AppointmentNotFoundException;
import com.apollo.hospital.exceptions.ResourceNotFoundException;
import com.apollo.hospital.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/apollo/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping("/by-patient/{id}")
    public ResponseEntity<Page<AppointmentRespDTO>> getAppointmentsByPatientId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @PathVariable("id") Long patientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatientId(patientId, page, size, sortBy));
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentRespDTO>> getAllAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok(appointmentService.getAllAppointments(page, size, sortBy));
    }

    @PutMapping("/reschedule/{id}")
    public ResponseEntity<AppointmentRespDTO> rescheduleAppointment(
            @PathVariable("id") Long appointmentId,
            @RequestBody AppointmentReqDTO updatedAppointmentDate) throws ResourceNotFoundException, AppointmentNotFoundException {
        AppointmentRespDTO appointment = appointmentService.rescheduleAppointment(appointmentId, updatedAppointmentDate);
        return ResponseEntity.accepted().body(appointment);
    }

    @GetMapping("/by-doctor/{id}")
    public ResponseEntity<Page<AppointmentRespDTO>> getAppointmentsByDoctorId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @PathVariable("id") Long doctorId) {
        Page<AppointmentRespDTO> appointments = appointmentService.getAppointmentsByDoctorId(doctorId, page, size, sortBy);
        return ResponseEntity.ok(appointments);
    }

    @PostMapping
    public ResponseEntity<AppointmentRespDTO> bookAppointment(
            @RequestParam Long patientId,
            @RequestParam Long doctorId,
            @RequestBody AppointmentReqDTO appointmentReq) throws ResourceNotFoundException {
        AppointmentRespDTO appointment = appointmentService.bookAppointment(appointmentReq, patientId, doctorId);
        return ResponseEntity.ok(appointment);
    }

    @PutMapping("/reassign-doctor")
    public ResponseEntity<AppointmentRespDTO> reassignDoctor(
            @RequestParam Long appointmentId,
            @RequestParam Long newDoctorId) throws AppointmentNotFoundException, ResourceNotFoundException {
        AppointmentRespDTO updatedAppointment = appointmentService.reassignDoctor(appointmentId, newDoctorId);
        return ResponseEntity.ok(updatedAppointment);
    }

}
