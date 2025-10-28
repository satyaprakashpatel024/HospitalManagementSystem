package com.apollo.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tbl_department",uniqueConstraints = {
        @UniqueConstraint(name = "uk_department_name", columnNames = "name"),
        @UniqueConstraint(name = "uk_department_id", columnNames = "id")})
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;
    private LocalDateTime createdAt;
    @OneToOne
    private Doctor headDoctor;
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name = "tbl_department_doctor",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
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
