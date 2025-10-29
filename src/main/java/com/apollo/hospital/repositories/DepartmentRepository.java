package com.apollo.hospital.repositories;

import com.apollo.hospital.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}