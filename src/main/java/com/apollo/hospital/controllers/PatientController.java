package   com.apollo.hospital.controllers;

import com.apollo.hospital.dtos.request.PatientReqDTO;
import com.apollo.hospital.dtos.response.PatientDetailsDTO;
import com.apollo.hospital.dtos.response.PatientSummaryDTO;
import com.apollo.hospital.entities.types.BloodGroupType;
import com.apollo.hospital.exceptions.ResourceNotFoundException;
import com.apollo.hospital.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
    public ResponseEntity<PatientSummaryDTO> createPatient(@RequestBody PatientReqDTO patient) {
        PatientSummaryDTO createdPatient = patientService.createPatient(patient);
        return ResponseEntity.ok(createdPatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDetailsDTO> updatePatient(
            @PathVariable("id") Long id, @RequestBody PatientReqDTO patientReqDTO) {
        try {
            PatientDetailsDTO updatedPatient = patientService.updatePatient(id, patientReqDTO);
            return ResponseEntity.ok(updatedPatient);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<Page<PatientSummaryDTO>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<PatientSummaryDTO> patients = patientService.getAllPatients(page, size, sortBy);
        return ResponseEntity.ok(patients);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-dob")
    public ResponseEntity<Page<PatientSummaryDTO>> getPatientsByDobBetween(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dob") String sortBy) {
        Page<PatientSummaryDTO> patients = patientService.findByDobBetween(startDate, endDate, page, size, sortBy);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/by-blood-type")
    public ResponseEntity<Page<PatientSummaryDTO>> getPatientsByBloodType(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam("bloodType") String bloodType) {
        Page<PatientSummaryDTO> patients = patientService.getByBloodGroup(BloodGroupType.valueOf(bloodType), page, size, sortBy);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/by-name")
    public ResponseEntity<Page<PatientSummaryDTO>> getPatientsByNameLike(
            @RequestParam("name") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<PatientSummaryDTO> patients = patientService.findByNameLike(name, page, size, sortBy);
        return ResponseEntity.ok(patients);
    }
}
