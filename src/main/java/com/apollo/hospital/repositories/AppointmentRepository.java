package com.apollo.hospital.repositories;

import com.apollo.hospital.entities.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT a FROM Appointment a " +
            "JOIN FETCH a.patient p " +
            "JOIN FETCH a.doctor d " +
            "WHERE a.patient.id = :patientId")
    Page<Appointment> findByPatientId(Pageable pageable, @Param("patientId") Long patientId);

    @Modifying
    @Transactional
    @Query("UPDATE Appointment a SET a.appointmentDate = :newDate WHERE a.id = :appointmentId")
    int rescheduleAppointmentDate(@Param("appointmentId") Long appointmentId, @Param("newDate") LocalDate newDate);

    @Query("SELECT a FROM Appointment a JOIN FETCH a.doctor d JOIN FETCH a.patient p WHERE d.id = :doctorId")
    Page<Appointment> findByDoctorId(Pageable pageable, Long doctorId);
}