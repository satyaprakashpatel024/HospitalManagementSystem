package com.apollo.hospital.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_insurance",uniqueConstraints = {
        @UniqueConstraint(name = "uk_policy_number", columnNames = "policyNumber"),
        @UniqueConstraint(name = "uk_insurance_id", columnNames = "id")
})
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String providerName;

    @Column(nullable = false, length = 50)
    private String policyNumber;

    @Column(nullable = false)
    private Double coverageAmount;

    @Column(nullable = false)
    private LocalDate validTill;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToOne(mappedBy = "insurance")
    private Patient patient;

    @Override
    public String toString() {
        return "Insurance { " +
                "id=" + id +
                ", providerName='" + providerName + '\'' +
                ", policyNumber='" + policyNumber + '\'' +
                ", coverageAmount=" + coverageAmount +
                ", validTill=" + validTill +
                ", createdAt=" + createdAt +
                '}';
    }
}
