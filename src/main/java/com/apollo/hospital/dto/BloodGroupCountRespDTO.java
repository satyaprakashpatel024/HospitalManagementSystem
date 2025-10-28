package com.apollo.hospital.dto;

import com.apollo.hospital.entity.types.BloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BloodGroupCountRespDTO {
    private BloodGroupType bloodGroup;
    private Long count;
}
