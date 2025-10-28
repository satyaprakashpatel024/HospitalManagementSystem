package com.apollo.hospital.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsuranceDTO {
    private Long id;
    private String providerName;
    private String policyNumber;
    private Double coverageAmount;
    private LocalDate validTill;
    private LocalDateTime createdAt;
}
