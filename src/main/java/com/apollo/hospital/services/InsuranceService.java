package com.apollo.hospital.services;

import com.apollo.hospital.dtos.InsuranceDTO;
import  com.apollo.hospital.entities.Insurance;
import  com.apollo.hospital.entities.Patient;
import com.apollo.hospital.exceptions.ResourceNotFoundException;
import  com.apollo.hospital.repositories.InsuranceRepository;
import  com.apollo.hospital.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance, Long patientId) throws ResourceNotFoundException {
        Patient patient = patientRepository.findById(patientId).
                orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        patient.setInsurance(insurance);
        patientRepository.save(patient);
        return patient;
    }

    @Transactional
    public Patient disassociateInsuranceFromPatient(Long patientId) throws ResourceNotFoundException {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        patient.setInsurance(null);
        patientRepository.save(patient);
        return patient;
    }

    public Insurance createInsurance(InsuranceDTO insurance) {
        Insurance map = modelMapper.map(insurance, Insurance.class);
        return insuranceRepository.save(map);
    }

    public InsuranceDTO getInsuranceById(Long insuranceId) throws ResourceNotFoundException {
        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance not found with id: " + insuranceId));
        return modelMapper.map(insurance, InsuranceDTO.class);
    }
}
