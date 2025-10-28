package com.apollo.hospital.services;

import  com.apollo.hospital.dto.InsuranceDTO;
import  com.apollo.hospital.dto.PatientDTO;
import  com.apollo.hospital.entity.Insurance;
import  com.apollo.hospital.entity.Patient;
import  com.apollo.hospital.entity.types.BloodGroupType;
import  com.apollo.hospital.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final ObjectMapper mapper;

    public PatientService(PatientRepository patientRepository, ObjectMapper mapper) {
        this.patientRepository = patientRepository;
        this.mapper = mapper;
    }

    public List<PatientDTO> getAllPatients() {
        List<Patient> all = patientRepository.findAll();
        return all.stream()
                .map(patient -> PatientDTO.builder()
                        .id(patient.getId())
                        .name(patient.getName())
                        .email(patient.getEmail())
                        .dob(patient.getDob())
                        .gender(patient.getGender())
                        .bloodGroup(patient.getBloodGroup())
                        .createdAt(patient.getCreatedAt())
                        .insuranceId(patient.getInsuranceId())
                        .build())
                .toList();
    }

    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) {
            return null;
        }
        Insurance insurance = patient.getInsurance();
        InsuranceDTO insuranceDTO = null;
        if (insurance != null) {
            insuranceDTO = InsuranceDTO.builder()
                    .id(insurance.getId())
                    .providerName(insurance.getProviderName())
                    .policyNumber(insurance.getPolicyNumber())
                    .coverageAmount(insurance.getCoverageAmount())
                    .validTill(insurance.getValidTill())
                    .createdAt(insurance.getCreatedAt())
                    .build();
        }

        return PatientDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .dob(patient.getDob())
                .gender(patient.getGender())
                .bloodGroup(patient.getBloodGroup())
                .createdAt(patient.getCreatedAt())
                .insurance(insuranceDTO)
                .build();
    }

    public PatientDTO createPatient(PatientDTO patient) {
        Patient newPatient = mapper.convertValue(patient, Patient.class);
        Patient save = patientRepository.save(newPatient);
        return mapper.convertValue(save, PatientDTO.class);
    }

    public PatientDTO updatePatient(Long id, Patient patientDetails) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient != null) {
            patient.setName(patientDetails.getName());
            patient.setEmail(patientDetails.getEmail());
            patient.setDob(patientDetails.getDob());
            patient.setGender(patientDetails.getGender());
            patient.setBloodGroup(patientDetails.getBloodGroup());
            Patient save = patientRepository.save(patient);
            return PatientDTO.builder()
                    .id(save.getId())
                    .name(save.getName())
                    .email(save.getEmail())
                    .dob(save.getDob())
                    .gender(patient.getGender())
                    .createdAt(patient.getCreatedAt())
                    .bloodGroup(patient.getBloodGroup())
                    .build();
        }
        return null;
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<Patient> findByDobBetween(LocalDate startDate, LocalDate endDate) {
        return patientRepository.findByDobBetween(startDate, endDate);
    }

    public List<Patient> findByNameLike(String name) {
        return patientRepository.findByNameLike(name);
    }

    public Patient getByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public List<PatientDTO> getByBloodGroup(BloodGroupType bloodGroup) {
        List<Patient> byBloodGroup = patientRepository.findByBloodGroup(bloodGroup);
        return byBloodGroup.stream()
                .map(patient -> PatientDTO.builder()
                        .id(patient.getId())
                        .name(patient.getName())
                        .email(patient.getEmail())
                        .dob(patient.getDob())
                        .bloodGroup(patient.getBloodGroup())
                        .gender(patient.getGender())
                        .createdAt(patient.getCreatedAt())
                        .insuranceId(patient.getInsuranceId())
                        .build())
                .toList();
    }
}
