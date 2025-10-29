package com.apollo.hospital.controllers;

import com.apollo.hospital.dtos.InsuranceDTO;
import com.apollo.hospital.exceptions.ResourceNotFoundException;
import com.apollo.hospital.services.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/apollo/insurances")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    @GetMapping
    public ResponseEntity<Object> getInsuranceById(Long insuranceId) {
        InsuranceDTO insuranceDTO = null;
        try {
            insuranceDTO = insuranceService.getInsuranceById(insuranceId);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(insuranceDTO);
    }
}
