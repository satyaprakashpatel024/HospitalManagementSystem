package   com.apollo.hospital.controller;

import   com.apollo.hospital.dto.PatientDTO;
import   com.apollo.hospital.entity.Patient;
import   com.apollo.hospital.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable("id") Long id) {
        PatientDTO patient = patientService.getPatientById(id);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patient) {
        PatientDTO createdPatient = patientService.createPatient(patient);
        return ResponseEntity.ok(createdPatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable("id") Long id, @RequestBody Patient patientDetails) {
        PatientDTO updatedPatient = patientService.updatePatient(id, patientDetails);
        if (updatedPatient != null) {
            return ResponseEntity.ok(updatedPatient);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        List<PatientDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }
}
