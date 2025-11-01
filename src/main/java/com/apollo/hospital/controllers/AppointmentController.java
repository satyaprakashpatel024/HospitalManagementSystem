package com.apollo.hospital.controllers;

import com.apollo.hospital.dtos.request.AppointmentReqDTO;
import com.apollo.hospital.dtos.response.AppointmentRespDTO;
import com.apollo.hospital.exceptions.ResourceNotFoundException;
import com.apollo.hospital.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/apollo/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping("/{id}")
    public ResponseEntity<List<AppointmentRespDTO>> getAppointmentsByPatientId(@PathVariable("id") Long patientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatientId(patientId));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentRespDTO>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @PostMapping
    public ResponseEntity<AppointmentRespDTO> bookAppointment(@RequestParam Long patientId,
                                                              @RequestParam Long doctorId,
                                                              @RequestBody AppointmentReqDTO appointmentReq) throws ResourceNotFoundException {

        AppointmentRespDTO appointment = appointmentService.bookAppointment(appointmentReq, patientId, doctorId);
        return ResponseEntity.ok(appointment);
    }

}
