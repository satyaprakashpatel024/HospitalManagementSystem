package com.apollo.hospital.services;

import  com.apollo.hospital.entity.Insurance;
import  com.apollo.hospital.entity.Patient;
import  com.apollo.hospital.repository.InsuranceRepository;
import  com.apollo.hospital.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;



    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance, Long patientId) {
        Patient patient = patientRepository.findById(patientId).
                orElseThrow(() -> new RuntimeException("Patient not found"));
        patient.setInsurance(insurance);
        patientRepository.save(patient);
        return patient;
    }

    @Transactional
    public Patient disassociateInsuranceFromPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        patient.setInsurance(null);
        patientRepository.save(patient);
        return patient;
    }
}
