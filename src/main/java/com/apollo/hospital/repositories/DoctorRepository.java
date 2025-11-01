package com.apollo.hospital.repositories;

import com.apollo.hospital.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // 1️⃣ Find doctor by email (single result, no pagination)
    @Query("SELECT d FROM Doctor d WHERE d.email = :email")
    Optional<Doctor> findByEmail(@Param("email") String email);

    // 2️⃣ Find doctors by specialization (paginated)
    @Query("SELECT d FROM Doctor d WHERE d.specialization = :specialization")
    Page<Doctor> findBySpecialization(@Param("specialization") String specialization, Pageable pageable);

    // 3️⃣ Find all doctors in a specific department (paginated)
    @Query("SELECT d FROM Doctor d JOIN d.departments dept WHERE dept.id = :departmentId")
    Page<Doctor> findDoctorsByDepartmentId(@Param("departmentId") Long departmentId, Pageable pageable);

    // 4️⃣ Find all doctors with appointments on a specific date (paginated)
    @Query("SELECT DISTINCT d FROM Doctor d JOIN d.appointments a WHERE a.appointmentDate = :date")
    Page<Doctor> findDoctorsWithAppointmentsOn(@Param("date") LocalDate date, Pageable pageable);

    // 5️⃣ Count of appointments for a doctor (single result, no pagination)
    @Query("SELECT COUNT(a) FROM Doctor d JOIN d.appointments a WHERE d.id = :doctorId")
    Long countAppointmentsByDoctor(@Param("doctorId") Long doctorId);

    // 6️⃣ Fetch doctor with departments eagerly (single result)
    @Query("SELECT d FROM Doctor d LEFT JOIN FETCH d.departments WHERE d.id = :id")
    Optional<Doctor> findDoctorWithDepartments(@Param("id") Long id);

    // 7️⃣ Fetch doctor with appointments eagerly (single result)
    @Query("SELECT d FROM Doctor d LEFT JOIN FETCH d.appointments WHERE d.id = :id")
    Optional<Doctor> findDoctorWithAppointments(@Param("id") Long id);

    // 8️⃣ Search doctors by name (partial match, paginated)
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Doctor> searchDoctorsByName(@Param("name") String name, Pageable pageable);

}
