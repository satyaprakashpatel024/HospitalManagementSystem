package com.apollo.hospital.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_doctor",uniqueConstraints = {
        @UniqueConstraint(name = "uk_doctor_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_doctor_id", columnNames = "id")})
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false, length = 40)
    private String email;

    @Column(nullable = false, length = 50)
    private String specialization;

    @ManyToMany(mappedBy = "doctors",fetch = FetchType.LAZY)
    private List<Department> departments;

    @ManyToMany(mappedBy = "doctor", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
