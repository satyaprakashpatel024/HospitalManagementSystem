package com.apollo.hospital.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class InsuranceReqDTO {
    private String providerName;
    private String policyNumber;
    private Double coverageAmount;
    private LocalDate validTill;
}
