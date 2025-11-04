package com.apollo.hospital.controllers;

import com.apollo.hospital.dtos.request.DoctorReqDTO;
import com.apollo.hospital.dtos.response.DoctorRespDTO;
import com.apollo.hospital.exceptions.DoctorNotFoundException;
import com.apollo.hospital.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/apollo/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // Create a new doctor
    @PostMapping
    public ResponseEntity<DoctorRespDTO> createDoctor(@RequestBody DoctorReqDTO reqDto) {
        DoctorRespDTO respDto = doctorService.createDoctor(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respDto);
    }

    // Get a doctor by ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorRespDTO> getDoctorById(@PathVariable Long id) throws DoctorNotFoundException {
        DoctorRespDTO respDto = doctorService.getDoctorById(id);
        return ResponseEntity.ok(respDto);
    }

    // Get paginated list of doctors
    @GetMapping
    public ResponseEntity<Page<DoctorRespDTO>> getDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<DoctorRespDTO> pageDto = doctorService.getDoctors(page, size, sortBy);
        return ResponseEntity.ok(pageDto);
    }

    // Update an existing doctor
    @PutMapping("/{id}")
    public ResponseEntity<DoctorRespDTO> updateDoctor(
            @PathVariable Long id,
            @RequestBody DoctorReqDTO reqDto) throws DoctorNotFoundException {

        DoctorRespDTO respDto = doctorService.updateDoctor(id, reqDto);
        return ResponseEntity.ok(respDto);
    }

    // Delete a doctor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) throws DoctorNotFoundException {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();  // HTTP 204 No Content
    }

}
