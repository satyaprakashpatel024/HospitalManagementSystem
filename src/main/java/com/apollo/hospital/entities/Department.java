package com.apollo.hospital.entities;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Schema(hidden = true)
@Hidden
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tbl_department", uniqueConstraints = {
        @UniqueConstraint(name = "uk_department_name", columnNames = "name"),
        @UniqueConstraint(name = "uk_department_id", columnNames = "id")
})
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // Head doctor (optional), One-to-One
    @OneToOne
    @JoinColumn(name = "head_doctor_id")
    private Doctor headDoctor;

    // Doctors in this department, One-to-Many
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private Set<Doctor> doctors = new HashSet<>();

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", headDoctor=" + (headDoctor != null ? headDoctor.getId() : null) +
                '}';
    }
}
