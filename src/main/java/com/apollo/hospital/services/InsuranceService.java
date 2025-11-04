package com.apollo.hospital.services;

import com.apollo.hospital.dtos.request.InsuranceReqDTO;
import com.apollo.hospital.dtos.response.InsuranceRespDTO;
import com.apollo.hospital.dtos.response.PatientDetailsDTO;
import com.apollo.hospital.entities.Insurance;
import com.apollo.hospital.entities.Patient;
import com.apollo.hospital.exceptions.PatientNotFoundException;
import com.apollo.hospital.exceptions.ResourceNotFoundException;
import com.apollo.hospital.repositories.InsuranceRepository;
import com.apollo.hospital.repositories.PatientRepository;
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
    public PatientDetailsDTO assignInsuranceToPatient(InsuranceReqDTO insurance, Long patientId) throws PatientNotFoundException {
        Patient patient = patientRepository.findById(patientId).
                orElseThrow(() -> new PatientNotFoundException("Patient not found With this id : " + patientId));
        Insurance existingInsurance = insuranceRepository.findByPolicyNumber(insurance.getPolicyNumber());
        Insurance map = modelMapper.map(insurance, Insurance.class);
        patient.setInsurance(map);
        patientRepository.save(patient);
        return convertToPatientDetailsDTO(patient);
    }

    @Transactional
    public Patient disassociateInsuranceFromPatient(Long patientId) throws ResourceNotFoundException {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found With this id : " + patientId));
        patient.setInsurance(null);
        patientRepository.save(patient);
        return patient;
    }

    public InsuranceRespDTO getInsuranceById(Long insuranceId) throws ResourceNotFoundException {
        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance not found with id: " + insuranceId));
        return modelMapper.map(insurance, InsuranceRespDTO.class);
    }

    private PatientDetailsDTO convertToPatientDetailsDTO(Patient patient) {
        return PatientDetailsDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .gender(patient.getGender())
                .bloodGroup(patient.getBloodGroup())
                .dob(patient.getDob())
                .createdAt(patient.getCreatedAt())
                .insurance(convertToInsuranceRespDTO(patient.getInsurance()))
                .build();
    }

    private InsuranceRespDTO convertToInsuranceRespDTO(Insurance insurance) {
        if (insurance == null) return null;
        return InsuranceRespDTO.builder()
                .id(insurance.getId())
                .providerName(insurance.getProviderName())
                .policyNumber(insurance.getPolicyNumber())
                .coverageAmount(insurance.getCoverageAmount())
                .validTill(insurance.getValidTill())
                .createdAt(insurance.getCreatedAt())
                .build();
    }

}
