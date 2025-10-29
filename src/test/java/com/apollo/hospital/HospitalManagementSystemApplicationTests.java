package com.apollo.hospital;

import com.apollo.hospital.dtos.PatientSummaryDTO;
import com.apollo.hospital.services.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HospitalManagementSystemApplicationTests {

    @Autowired
    private PatientService patientService;

    @Test
    public void getAll(){
        List<PatientSummaryDTO> allPatients = patientService.getAllPatients();
        for(PatientSummaryDTO patient : allPatients){
            System.out.println("Patient Name: " + patient.getName() + ", Insurance ID: " + patient.getInsuranceId());
        }
    }
}
