package com.apollo.hospital.services;

import com.apollo.hospital.dtos.request.PatientReqDTO;
import com.apollo.hospital.dtos.response.InsuranceRespDTO;
import com.apollo.hospital.dtos.response.PatientDetailsDTO;
import com.apollo.hospital.dtos.response.PatientSummaryDTO;
import com.apollo.hospital.entities.Insurance;
import com.apollo.hospital.entities.Patient;
import com.apollo.hospital.entities.types.BloodGroupType;
import com.apollo.hospital.entities.types.Gender;
import com.apollo.hospital.exceptions.ResourceNotFoundException;
import com.apollo.hospital.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper mapper;

    public Page<PatientSummaryDTO> getAllPatients(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Patient> all = patientRepository.findAll(pageable);
        return all.map(this::convertToPatientSummaryDTO);
    }

    public PatientDetailsDTO getPatientById(Long id) throws ResourceNotFoundException {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        return this.convertToPatientDetailsDTO(patient);
    }

    public PatientSummaryDTO createPatient(PatientReqDTO patient) {
        Patient newPatient = this.convertToPatientEntity(patient);
        Patient save = patientRepository.save(newPatient);
        return this.convertToPatientSummaryDTO(save);
    }

    public PatientDetailsDTO updatePatient(Long id, PatientReqDTO patientReqDTO) throws ResourceNotFoundException {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
            mapper.map(patientReqDTO, patient);
            Patient savedPatient = patientRepository.save(patient);
            return this.convertToPatientDetailsDTO(savedPatient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public Page<PatientSummaryDTO> findByDobBetween(LocalDate startDate, LocalDate endDate, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Patient> byDobBetween = patientRepository.findByDobBetween(pageable, startDate, endDate);
        return byDobBetween.map(this::convertToPatientSummaryDTO);
    }

    public Page<PatientSummaryDTO> findByNameLike(String name, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Patient> byNameLike = patientRepository.findByNameLike(pageable, name);
        return byNameLike.map(this::convertToPatientSummaryDTO);
    }

    public PatientSummaryDTO getByEmail(String email) throws ResourceNotFoundException {
        Patient byEmail = patientRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Patient not found with email : " + email));
        return this.convertToPatientSummaryDTO(byEmail);
    }

    public Page<PatientSummaryDTO> getByBloodGroup(BloodGroupType bloodGroup, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Patient> byBloodGroup = patientRepository.findByBloodGroup(pageable, bloodGroup);
        return byBloodGroup.map(this::convertToPatientSummaryDTO);
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
                .gender(Gender.valueOf(patientDTO.getGender()))
                .bloodGroup(patientDTO.getBloodGroup())
                .build();
    }

    private InsuranceRespDTO convertToInsuranceDTO(Insurance insurance) {
        if(insurance==null) return null;
        return InsuranceRespDTO.builder()
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
