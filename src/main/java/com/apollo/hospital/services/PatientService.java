package com.apollo.hospital.services;

import com.apollo.hospital.dtos.InsuranceDTO;
import com.apollo.hospital.dtos.PatientDetailsDTO;
import com.apollo.hospital.dtos.PatientSummaryDTO;
import com.apollo.hospital.dtos.request.PatientReqDTO;
import com.apollo.hospital.entities.Insurance;
import com.apollo.hospital.entities.Patient;
import com.apollo.hospital.entities.types.BloodGroupType;
import com.apollo.hospital.exceptions.ResourceNotFoundException;
import com.apollo.hospital.repositories.PatientRepository;
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

    public PatientSummaryDTO createPatient(PatientReqDTO patient) {
        Patient newPatient = this.convertToPatientEntity(patient);
        Patient save = patientRepository.save(newPatient);
        return this.convertToPatientSummaryDTO(save);
    }

    public PatientDetailsDTO updatePatient(Long id, PatientReqDTO patientReqDTO) throws ResourceNotFoundException {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient != null) {
            mapper.map(patientReqDTO, patient);
            Patient savedPatient = patientRepository.save(patient);
            return this.convertToPatientDetailsDTO(savedPatient);
        }
        throw  new ResourceNotFoundException("Patient not found with id: " + id);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<PatientSummaryDTO> findByDobBetween(LocalDate startDate, LocalDate endDate) {
        List<Patient> byDobBetween = patientRepository.findByDobBetween(startDate, endDate);
        return byDobBetween.stream()
                .map(this::convertToPatientSummaryDTO)
                .toList();
    }

    public List<PatientSummaryDTO> findByNameLike(String name) {
        List<Patient> byNameLike = patientRepository.findByNameLike(name);
        return byNameLike.stream()
                .map(this::convertToPatientSummaryDTO)
                .toList();
    }

    public PatientSummaryDTO getByEmail(String email) {
        Patient byEmail = patientRepository.findByEmail(email);
        return this.convertToPatientSummaryDTO(byEmail);
    }

    public List<PatientSummaryDTO> getByBloodGroup(BloodGroupType bloodGroup) {
        List<Patient> byBloodGroup = patientRepository.findByBloodGroup(bloodGroup);
        return byBloodGroup.stream()
                .map(this::convertToPatientSummaryDTO)
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

    private Patient convertToPatientEntity(PatientReqDTO patientDTO) {
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
