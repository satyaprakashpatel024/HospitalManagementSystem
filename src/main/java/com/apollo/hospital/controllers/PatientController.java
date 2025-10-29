package   com.apollo.hospital.controllers;

import com.apollo.hospital.dtos.PatientDetailsDTO;
import com.apollo.hospital.dtos.PatientSummaryDTO;
import com.apollo.hospital.exceptions.ResourceNotFoundException;
import   com.apollo.hospital.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/apollo/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<PatientDetailsDTO> getPatientById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        PatientDetailsDTO patient = patientService.getPatientById(id);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PatientSummaryDTO> createPatient(@RequestBody PatientSummaryDTO patient) {
        PatientSummaryDTO createdPatient = patientService.createPatient(patient);
        return ResponseEntity.ok(createdPatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDetailsDTO> updatePatient(@PathVariable("id") Long id, @RequestBody PatientDetailsDTO patientDetails) {
        PatientDetailsDTO updatedPatient = null;
        try {
            updatedPatient = patientService.updatePatient(id, patientDetails);
            return ResponseEntity.ok(updatedPatient);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<List<PatientSummaryDTO>> getAllPatients() {
        List<PatientSummaryDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }
}
