package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.MedicationCreateRequest;
import com.kenzie.appserver.controller.model.MedicationResponse;
import com.kenzie.appserver.controller.model.MedicationUpdateRequest;
import com.kenzie.appserver.service.MedicationService;
import com.kenzie.appserver.service.model.Medication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/medication")
public class MedicationController {
    private MedicationService medicationService;

    public MedicationController(MedicationService medicationService){
        this.medicationService = medicationService;
    }
    @PostMapping
    public ResponseEntity<MedicationResponse> createMedication(@RequestBody MedicationCreateRequest createRequest){
        Medication medication = new Medication(createRequest.getName(), randomUUID().toString(),
                createRequest.getTimeOfDay(), createRequest.getDosage(), createRequest.getAlertTime(), createRequest.getAlertDays());
        medicationService.addNewMedication(medication);

        MedicationResponse response = createMedicationResponse(medication);

        return ResponseEntity.created(URI.create("/medication/" + response.getId())).body(response);
    }

    @GetMapping("/{medication}")
    public ResponseEntity<MedicationResponse> getMedication(@PathVariable("medication") String medicationName) {
        Medication medication = medicationService.findById(medicationName);
        if (medication == null) {
            return ResponseEntity.noContent().build();
        }
        MedicationResponse response = this.createMedicationResponse(medication);

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<MedicationResponse> updateMedication(@RequestBody MedicationUpdateRequest updateRequest) {
        Medication medication = new Medication(updateRequest.getName(),
                updateRequest.getId(),
                updateRequest.getTimeOfDay(),
                updateRequest.getDosage(),
                updateRequest.getAlertTime(),
                updateRequest.getAlertDays());
        medicationService.updateMedication(medication);

        MedicationResponse response = createMedicationResponse(medication);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MedicationResponse>> getMedicationList() {
        List<Medication> medications = medicationService.getAllMedications();
        if (medications == null || medications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<MedicationResponse> response = new ArrayList<>();
        for (Medication medication : medications) {
            response.add(this.createMedicationResponse(medication));
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{medication}")
    public ResponseEntity deleteMedication(@PathVariable("medication") String medication) {
        medicationService.deleteMedication(medication);
        return ResponseEntity.noContent().build();
    }

    private MedicationResponse createMedicationResponse(Medication medication){
        MedicationResponse response = new MedicationResponse();
        response.setName(medication.getName());
        response.setId(medication.getId());
        response.setTimeOfDay(medication.getTimeOfDay());
        response.setDosage(medication.getDosage());
        response.setAlertTime(medication.getAlertTime());
        response.setAlertDays(medication.getAlertDays());
        return response;
    }
}
