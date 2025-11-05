package com.apollo.hospital.entities;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_appointment",uniqueConstraints = {
        @UniqueConstraint(name = "uk_appointment_id", columnNames = "id")})
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@BatchSize(size = 10)
@Hidden
@Schema(hidden = true)
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
        return "Appointment {" +
                "id=" + id +
                ", appointmentDate=" + appointmentDate +
                ", reason=" + reason +
                '}';
    }
}
