package com.apollo.hospital.services;

import com.apollo.hospital.dtos.request.DoctorReqDTO;
import com.apollo.hospital.dtos.response.DoctorRespDTO;
import com.apollo.hospital.entities.Doctor;
import com.apollo.hospital.exceptions.ResourceNotFoundException;
import com.apollo.hospital.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // conversion methods
    private DoctorRespDTO toResponseDto(Doctor doctor) {
        return DoctorRespDTO.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .email(doctor.getEmail())
                .specialization(doctor.getSpecialization())
                .build();
    }

    private Doctor fromRequestDto(DoctorReqDTO req) {
        Doctor doctor = new Doctor();
        doctor.setName(req.getName());
        doctor.setEmail(req.getEmail());
        doctor.setSpecialization(req.getSpecialization());
        return doctor;
    }

    private void updateEntityFromRequestDto(Doctor doctor, DoctorReqDTO req) {
        doctor.setName(req.getName());
        doctor.setEmail(req.getEmail());
        doctor.setSpecialization(req.getSpecialization());
    }

    // service methods
    public DoctorRespDTO createDoctor(DoctorReqDTO reqDto) {
        Doctor entity = fromRequestDto(reqDto);
        Doctor saved = doctorRepository.save(entity);
        return toResponseDto(saved);
    }

    public DoctorRespDTO getDoctorById(Long id) throws ResourceNotFoundException {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        return toResponseDto(doctor);
    }

    public Page<DoctorRespDTO> getDoctors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Doctor> pageEntity = doctorRepository.findAll(pageable);
        return pageEntity.map(this::toResponseDto);
    }

    public DoctorRespDTO updateDoctor(Long id, DoctorReqDTO reqDto) throws ResourceNotFoundException {
        Doctor entity = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        updateEntityFromRequestDto(entity, reqDto);
        Doctor updated = doctorRepository.save(entity);
        return toResponseDto(updated);
    }

    public void deleteDoctor(Long id) throws ResourceNotFoundException {
        Doctor entity = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        doctorRepository.delete(entity);
    }
}

