package com.apollo.hospital.repositories;

import  com.apollo.hospital.dtos.BloodGroupCountRespDTO;
import  com.apollo.hospital.entities.Patient;
import  com.apollo.hospital.entities.types.BloodGroupType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // this method uses numbering as parameter binding
    @Query("SELECT p FROM Patient p WHERE p.bloodGroup = ?1")
    List<Patient> findByBloodGroup(@Param("bloodGroup") BloodGroupType bloodGroup);
    List<Patient> findByDobBetween(LocalDate startDate, LocalDate endDate);
    List<Patient> findByNameLike(String name);
    Patient findByEmail(String email);
    // this method uses naming as parameter binding
    @Query("SELECT p FROM Patient p WHERE p.dob > :date")
    List<Patient> findWhoBornAfterDate(@Param("date") LocalDate date);
    @Query("SELECT new com.apollo.hospital.dtos.BloodGroupCountRespDTO(p.bloodGroup, COUNT(p)) FROM Patient p GROUP BY p.bloodGroup")
    List<BloodGroupCountRespDTO> findPatientCountByBloodGroup();
    // native query
    @Query(value = "select * from tbl_patient",nativeQuery = true)
    Page<Patient> findAllPatients(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Patient p SET p.name = :name WHERE p.id = :id")
    int updatePatientNameById(@Param("name") String name, @Param("id") Long id);
}
