package com.apollo.hospital.entity;

import com.apollo.hospital.entity.types.BloodGroupType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_patient",uniqueConstraints = {
        @UniqueConstraint(name = "uk_patient_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_patent_id", columnNames = "id")
})
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false, length = 40)
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Column(length = 10)
    private String gender;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private BloodGroupType bloodGroup;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id", referencedColumnName = "id")
    private Insurance insurance;

    @JsonIgnore
    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", createdAt=" + createdAt +
                ", bloodGroup=" + bloodGroup +
                '}';
    }

    public Long getInsuranceId() {
        return insurance != null ? insurance.getId() : null;
    }
}
