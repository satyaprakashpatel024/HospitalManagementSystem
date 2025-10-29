package com.apollo.hospital.services;

import  com.apollo.hospital.dtos.InsuranceDTO;
import com.apollo.hospital.dtos.PatientDetailsDTO;
import com.apollo.hospital.dtos.PatientSummaryDTO;
import  com.apollo.hospital.entities.Insurance;
import  com.apollo.hospital.entities.Patient;
import  com.apollo.hospital.entities.types.BloodGroupType;
import com.apollo.hospital.exceptions.ResourceNotFoundException;
import  com.apollo.hospital.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper mapper;

    public List<PatientSummaryDTO> getAllPatients() {
        List<Patient> all = patientRepository.findAll();
        return all.stream()
                .map(this::convertToPatientSummaryDTO)
                .toList();
    }

    public PatientDetailsDTO getPatientById(Long id) throws ResourceNotFoundException {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) {
            throw  new ResourceNotFoundException("Patient not found with id: " + id);
        }
        return this.convertToPatientDetailsDTO(patient);
    }

    public PatientSummaryDTO createPatient(PatientSummaryDTO patient) {
        Patient newPatient = this.convertToPatientEntity(patient);
        Patient save = patientRepository.save(newPatient);
        return this.convertToPatientSummaryDTO(save);
    }

    public PatientDetailsDTO updatePatient(Long id, PatientDetailsDTO patientDetails) throws ResourceNotFoundException {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient != null) {
            mapper.map(patientDetails, patient);
            Patient savedPatient = patientRepository.save(patient);
            return mapper.map(savedPatient, PatientDetailsDTO.class);
        }
        throw  new ResourceNotFoundException("Patient not found with id: " + id);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<PatientDetailsDTO> findByDobBetween(LocalDate startDate, LocalDate endDate) {
        List<Patient> byDobBetween = patientRepository.findByDobBetween(startDate, endDate);
        return byDobBetween.stream()
                .map((element) -> mapper.map(element, PatientDetailsDTO.class))
                .toList();
    }

    public List<Patient> findByNameLike(String name) {
        return patientRepository.findByNameLike(name);
    }

    public Patient getByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public List<PatientDetailsDTO> getByBloodGroup(BloodGroupType bloodGroup) {
        List<Patient> byBloodGroup = patientRepository.findByBloodGroup(bloodGroup);
        return byBloodGroup.stream()
                .map(patient -> mapper.map(patient, PatientDetailsDTO.class))
                .toList();
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
                .insurance(convertToInsuranceDTO(patient.getInsurance()))
                .build();
    }

    private Patient convertToPatientEntity(PatientSummaryDTO patientDTO) {
        return Patient.builder()
                .name(patientDTO.getName())
                .email(patientDTO.getEmail())
                .dob(patientDTO.getDob())
                .gender(patientDTO.getGender())
                .bloodGroup(patientDTO.getBloodGroup())
                .build();
    }

    private InsuranceDTO convertToInsuranceDTO(Insurance insurance) {
        if(insurance==null) return null;
        return InsuranceDTO.builder()
                .id(insurance.getId())
                .providerName(insurance.getProviderName())
                .policyNumber(insurance.getPolicyNumber())
                .coverageAmount(insurance.getCoverageAmount())
                .validTill(insurance.getValidTill())
                .createdAt(insurance.getCreatedAt())
                .build();
    }

    private PatientSummaryDTO convertToPatientSummaryDTO(Patient patient) {
        return  PatientSummaryDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .dob(patient.getDob())
                .gender(patient.getGender())
                .bloodGroup(patient.getBloodGroup())
                .createdAt(patient.getCreatedAt())
                .insuranceId(patient.getInsuranceId())
                .build();
    }
}
