package com.apollo.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_appointment",uniqueConstraints = {
        @UniqueConstraint(name = "uk_appointment_id", columnNames = "id")})
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false, length = 100)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Doctor doctor;

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", appointmentDate=" + appointmentDate +
                ", reason=" + reason +
                '}';
    }
}
