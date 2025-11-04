package com.apollo.hospital.controllers;

import com.apollo.hospital.dtos.request.InsuranceReqDTO;
import com.apollo.hospital.dtos.response.InsuranceRespDTO;
import com.apollo.hospital.exceptions.PatientNotFoundException;
import com.apollo.hospital.exceptions.ResourceNotFoundException;
import com.apollo.hospital.services.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/apollo/insurances")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    @GetMapping("{id}")
    public ResponseEntity<InsuranceRespDTO> getInsuranceById(@PathVariable("id") Long insuranceId) throws ResourceNotFoundException {
            InsuranceRespDTO insuranceRespDTO = insuranceService.getInsuranceById(insuranceId);
            return ResponseEntity.ok(insuranceRespDTO);

    }

    @PostMapping
    public ResponseEntity<Object> assignInsuranceToPatient(@RequestParam Long patientId, @RequestBody InsuranceReqDTO insurance) throws ResourceNotFoundException, PatientNotFoundException {
            insuranceService.assignInsuranceToPatient(insurance, patientId);
            return ResponseEntity.ok("Insurance assigned to patient successfully.");
    }

}
